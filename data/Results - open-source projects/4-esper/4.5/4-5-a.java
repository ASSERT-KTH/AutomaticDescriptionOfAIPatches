/*
 * *************************************************************************************
 *  Copyright (C) 2006-2015 EsperTech, Inc. All rights reserved.                       *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.core.context.mgr;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.context.ContextPartitionIdentifier;
import com.espertech.esper.client.context.ContextPartitionIdentifierHash;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.core.context.stmt.*;
import com.espertech.esper.epl.core.EngineImportSingleRowDesc;
import com.espertech.esper.epl.expression.core.ExprEvaluator;
import com.espertech.esper.epl.expression.core.ExprNode;
import com.espertech.esper.epl.expression.core.ExprNodeUtility;
import com.espertech.esper.epl.expression.core.ExprValidationException;
import com.espertech.esper.epl.spec.ContextDetail;
import com.espertech.esper.epl.spec.ContextDetailHash;
import com.espertech.esper.epl.spec.ContextDetailHashItem;
import com.espertech.esper.epl.spec.ContextDetailPartitionItem;
import com.espertech.esper.epl.spec.util.StatementSpecCompiledAnalyzer;
import com.espertech.esper.epl.spec.util.StatementSpecCompiledAnalyzerResult;
import com.espertech.esper.event.EventTypeUtility;
import com.espertech.esper.filter.*;

import java.io.StringWriter;
import java.util.*;

public abstract class ContextControllerHashFactoryBase extends ContextControllerFactoryBase implements ContextControllerFactory {

    private final ContextDetailHash hashedSpec;
    private final List<FilterSpecCompiled> filtersSpecsNestedContexts;
    private Map<String, Object> contextBuiltinProps;

    public ContextControllerHashFactoryBase(ContextControllerFactoryContext factoryContext, ContextDetailHash hashedSpec, List<FilterSpecCompiled> filtersSpecsNestedContexts) {
        super(factoryContext);
        this.hashedSpec = hashedSpec;
        this.filtersSpecsNestedContexts = filtersSpecsNestedContexts;
    }

    public boolean hasFiltersSpecsNestedContexts() {
        return filtersSpecsNestedContexts != null && !filtersSpecsNestedContexts.isEmpty();
    }

    public void validateFactory() throws ExprValidationException {
        validatePopulateContextDesc();
        contextBuiltinProps = ContextPropertyEventType.getHashType();
    }

    public ContextControllerStatementCtxCache validateStatement(ContextControllerStatementBase statement) throws ExprValidationException {
        StatementSpecCompiledAnalyzerResult streamAnalysis = StatementSpecCompiledAnalyzer.analyzeFilters(statement.getStatementSpec());
        ContextControllerPartitionedUtil.validateStatementForContext(factoryContext.getContextName(), statement, streamAnalysis, getItemEventTypes(hashedSpec), factoryContext.getServicesContext().getNamedWindowMgmtService());
        return new ContextControllerStatementCtxCacheFilters(streamAnalysis.getFilters());
    }

    public void populateFilterAddendums(IdentityHashMap<FilterSpecCompiled, FilterValueSetParam[][]> filterAddendum, ContextControllerStatementDesc statement, Object key, int contextId) {
        ContextControllerStatementCtxCacheFilters statementInfo = (ContextControllerStatementCtxCacheFilters) statement.getCaches()[factoryContext.getNestingLevel() - 1];
        int assignedContextPartition = (Integer) key;
        int code = assignedContextPartition % hashedSpec.getGranularity();
        getAddendumFilters(filterAddendum, code, statementInfo.getFilterSpecs(), hashedSpec, statement);
    }

    public void populateContextInternalFilterAddendums(ContextInternalFilterAddendum filterAddendum, Object key) {
        int assignedContextPartition = (Integer) key;
        int code = assignedContextPartition % hashedSpec.getGranularity();
        getAddendumFilters(filterAddendum.getFilterAddendum(), code, filtersSpecsNestedContexts, hashedSpec, null);
    }

    public FilterSpecLookupable getFilterLookupable(EventType eventType) {
        for (ContextDetailHashItem hashItem : hashedSpec.getItems()) {
            if (hashItem.getFilterSpecCompiled().getFilterForEventType() == eventType) {
                return hashItem.getLookupable();
            }
        }
        return null;
    }

    public boolean isSingleInstanceContext() {
        return false;
    }

    public StatementAIResourceRegistryFactory getStatementAIResourceRegistryFactory() {
        if (hashedSpec.getGranularity() <= 65536) {
            return new StatementAIResourceRegistryFactory() {
                public StatementAIResourceRegistry make() {
                    return new StatementAIResourceRegistry(new AIRegistryAggregationMultiPerm(), new AIRegistryExprMultiPerm());
                }
            };
        }
        else {
            return new StatementAIResourceRegistryFactory() {
                public StatementAIResourceRegistry make() {
                    return new StatementAIResourceRegistry(new AIRegistryAggregationMap(), new AIRegistryExprMap());
                }
            };
        }
    }

    public List<ContextDetailPartitionItem> getContextDetailPartitionItems() {
        return Collections.emptyList();
    }

    public ContextDetail getContextDetail() {
        return hashedSpec;
    }

    public ContextDetailHash getHashedSpec() {
        return hashedSpec;
    }

    public Map<String, Object> getContextBuiltinProps() {
        return contextBuiltinProps;
    }

    public ContextPartitionIdentifier keyPayloadToIdentifier(Object payload) {
        return new ContextPartitionIdentifierHash((Integer) payload);
    }

    private Collection<EventType> getItemEventTypes(ContextDetailHash hashedSpec) {
        List<EventType> itemEventTypes = new ArrayList<EventType>();
        for (ContextDetailHashItem item : hashedSpec.getItems()) {
            itemEventTypes.add(item.getFilterSpecCompiled().getFilterForEventType());
        }
        return itemEventTypes;
    }

    private void validatePopulateContextDesc() throws ExprValidationException {

        if (hashedSpec.getItems().isEmpty()) {
            throw new ExprValidationException("Empty list of hash items");
        }

        for (ContextDetailHashItem item : hashedSpec.getItems()) {
            if (item.getFunction().getParameters().isEmpty()) {
                throw new ExprValidationException("For context '" + factoryContext.getContextName() + "' expected one or more parameters to the hash function, but found no parameter list");
            }

            // determine type of hash to use
            String hashFuncName = item.getFunction().getName();
            HashFunctionEnum hashFunction = HashFunctionEnum.determine(factoryContext.getContextName(), hashFuncName);
            Pair<Class, EngineImportSingleRowDesc> hashSingleRowFunction = null;
            if (hashFunction == null) {
                try {
                    hashSingleRowFunction = factoryContext.getAgentInstanceContextCreate().getStatementContext().getMethodResolutionService().resolveSingleRow(hashFuncName);
                }
                catch (Exception e) {
                    // expected
                }

                if (hashSingleRowFunction == null) {
                    throw new ExprValidationException("For context '" + factoryContext.getContextName() + "' expected a hash function that is any of {" + HashFunctionEnum.getStringList() +
                        "} or a plug-in single-row function or script but received '" + hashFuncName + "'");
                }
            }

            // get first parameter
            ExprNode paramExpr = item.getFunction().getParameters().get(0);
            ExprEvaluator eval = paramExpr.getExprEvaluator();
            Class paramType = eval.getType();
            EventPropertyGetter getter;

            if (hashFunction == HashFunctionEnum.CONSISTENT_HASH_CRC32) {
                if (item.getFunction().getParameters().size() > 1 || paramType != String.class) {
                    getter = new ContextControllerHashedGetterCRC32Serialized(factoryContext.getAgentInstanceContextCreate().getStatementContext().getStatementName(), item.getFunction().getParameters(), hashedSpec.getGranularity());
                }
                else {
                    getter = new ContextControllerHashedGetterCRC32Single(eval, hashedSpec.getGranularity());
                }
            }
            else if (hashFunction == HashFunctionEnum.HASH_CODE) {
                if (item.getFunction().getParameters().size() > 1) {
                    getter = new ContextControllerHashedGetterHashMultiple(item.getFunction().getParameters(), hashedSpec.getGranularity());
                }
                else {
                    getter = new ContextControllerHashedGetterHashSingle(eval, hashedSpec.getGranularity());
                }
            }
            else if (hashSingleRowFunction != null) {
                getter = new ContextControllerHashedGetterSingleRow(factoryContext.getAgentInstanceContextCreate().getStatementContext().getStatementName(), hashFuncName, hashSingleRowFunction, item.getFunction().getParameters(), hashedSpec.getGranularity(),
                        factoryContext.getAgentInstanceContextCreate().getStatementContext().getMethodResolutionService(),
                        item.getFilterSpecCompiled().getFilterForEventType(),
                        factoryContext.getAgentInstanceContextCreate().getStatementContext().getEventAdapterService(),
                        factoryContext.getAgentInstanceContextCreate().getStatementId(),
                        factoryContext.getServicesContext().getTableService());
            }
            else {
                throw new IllegalArgumentException("Unrecognized hash code function '" + hashFuncName + "'");
            }

            // create and register expression
            String expression = item.getFunction().getName() + "(" + ExprNodeUtility.toExpressionStringMinPrecedenceSafe(paramExpr) + ")";
            FilterSpecLookupable lookupable = new FilterSpecLookupable(expression, getter, Integer.class, true);
            item.setLookupable(lookupable);
            factoryContext.getServicesContext().getFilterNonPropertyRegisteryService().registerNonPropertyExpression(factoryContext.getAgentInstanceContextCreate().getStatementName(), item.getFilterSpecCompiled().getFilterForEventType(), lookupable);
        }
    }

    // Compare filters in statement with filters in segmented context, addendum filter compilation
    public static void getAddendumFilters(IdentityHashMap<FilterSpecCompiled, FilterValueSetParam[][]> addendums, int agentInstanceId, List<FilterSpecCompiled> filtersSpecs, ContextDetailHash hashSpec, ContextControllerStatementDesc statementDesc) {

        // determine whether create-named-window
        boolean isCreateWindow = statementDesc != null && statementDesc.getStatement().getStatementSpec().getCreateWindowDesc() != null;
        if (!isCreateWindow) {
            for (FilterSpecCompiled filtersSpec : filtersSpecs) {

                ContextDetailHashItem foundPartition = findHashItemSpec(hashSpec, filtersSpec);
                if (foundPartition == null) {
                    continue;
                }

                FilterValueSetParam filter = new FilterValueSetParamImpl(foundPartition.getLookupable(), FilterOperator.EQUAL, agentInstanceId);

                FilterValueSetParam[][] addendum = new FilterValueSetParam[1][];
                addendum[0] = new FilterValueSetParam[] {filter};

                FilterValueSetParam[][] partitionFilters = foundPartition.getParametersCompiled();
                if (partitionFilters != null) {
                    addendum = ContextControllerAddendumUtil.addAddendum(partitionFilters, filter);
                }

                FilterValueSetParam[][] existing = addendums.get(filtersSpec);
                if (existing != null) {
                    addendum = ContextControllerAddendumUtil.multiplyAddendum(existing, addendum);
                }
                addendums.put(filtersSpec, addendum);
            }
        }
        // handle segmented context for create-window
        else {
            String declaredAsName = statementDesc.getStatement().getStatementSpec().getCreateWindowDesc().getAsEventTypeName();
            if (declaredAsName != null) {
                for (FilterSpecCompiled filterSpec : filtersSpecs) {

                    ContextDetailHashItem foundPartition = null;
                    for (ContextDetailHashItem partitionItem : hashSpec.getItems()) {
                        if (partitionItem.getFilterSpecCompiled().getFilterForEventType().getName().equals(declaredAsName)) {
                            foundPartition = partitionItem;
                            break;
                        }
                    }

                    if (foundPartition == null) {
                        continue;
                    }

                    FilterValueSetParam filter = new FilterValueSetParamImpl(foundPartition.getLookupable(), FilterOperator.EQUAL, agentInstanceId);
                    FilterValueSetParam[][] addendum = new FilterValueSetParam[1][];
                    addendum[0] = new FilterValueSetParam[] {filter};

                    FilterValueSetParam[][] existing = addendums.get(filterSpec);
                    if (existing != null) {
                        addendum = ContextControllerAddendumUtil.multiplyAddendum(existing, addendum);
                    }
                    addendums.put(filterSpec, addendum);
                }
            }
        }
    }

    public static ContextDetailHashItem findHashItemSpec(ContextDetailHash hashSpec, FilterSpecCompiled filterSpec) {
        ContextDetailHashItem foundPartition = null;
        for (ContextDetailHashItem partitionItem : hashSpec.getItems()) {
            boolean typeOrSubtype = EventTypeUtility.isTypeOrSubTypeOf(filterSpec.getFilterForEventType(), partitionItem.getFilterSpecCompiled().getFilterForEventType());
            if (typeOrSubtype) {
                foundPartition = partitionItem;
            }
        }

        return foundPartition;
    }

    public static enum HashFunctionEnum {
        CONSISTENT_HASH_CRC32,
        HASH_CODE;
        private static String stringList;

        public static HashFunctionEnum determine(String contextName, String name) throws ExprValidationException {
            String nameTrim = name.toLowerCase().trim();
            for (HashFunctionEnum val : HashFunctionEnum.values()) {
                if (val.name().toLowerCase().trim().equals(nameTrim)) {
                    return val;
                }
            }

            return null;
        }

        public static String getStringList() {
            StringWriter message = new StringWriter();
            String delimiter = "";
            for (HashFunctionEnum val : HashFunctionEnum.values()) {
                message.append(delimiter);
                message.append(val.name().toLowerCase().trim());
                delimiter = ", ";
            }
            return message.toString();
        }
    }
}
