--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/13568.java	2018-10-25 14:41:39.375752461 +0200
@@ -1,173 +1,173 @@
 /*
  * Licensed to Elastic Search and Shay Banon under one
  * or more contributor license agreements.  See the NOTICE file
  * distributed with this work for additional information
  * regarding copyright ownership. Elastic Search licenses this
  * file to you under the Apache License, Version 2.0 (the
  * "License"); you may not use this file except in compliance
  * with the License.  You may obtain a copy of the License at
  *
  *    http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing,
  * software distributed under the License is distributed on an
  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  * KIND, either express or implied.  See the License for the
  * specific language governing permissions and limitations
  * under the License.
  */
 
 package org.elasticsearch.index.query;
 
 import org.apache.lucene.index.IndexReader;
 import org.apache.lucene.search.DeletionAwareConstantScoreQuery;
 import org.apache.lucene.search.DocIdSet;
 import org.apache.lucene.search.Filter;
 import org.apache.lucene.search.FilteredQuery;
 import org.apache.lucene.search.Query;
 import org.elasticsearch.common.Strings;
 import org.elasticsearch.common.inject.Inject;
 import org.elasticsearch.common.xcontent.XContentParser;
 import org.elasticsearch.index.mapper.MapperService;
 import org.elasticsearch.index.mapper.object.ObjectMapper;
 import org.elasticsearch.index.search.nested.BlockJoinQuery;
 import org.elasticsearch.index.search.nested.NonNestedDocsFilter;
 import org.elasticsearch.search.internal.SearchContext;
 
 import java.io.IOException;
 
 public class NestedQueryParser implements QueryParser {
 
     public static final String NAME = "nested";
 
     @Inject public NestedQueryParser() {
     }
 
     @Override public String[] names() {
         return new String[]{NAME, Strings.toCamelCase(NAME)};
     }
 
     @Override public Query parse(QueryParseContext parseContext) throws IOException, QueryParsingException {
         XContentParser parser = parseContext.parser();
 
         Query query = null;
         Filter filter = null;
         float boost = 1.0f;
         String scope = null;
         String path = null;
         BlockJoinQuery.ScoreMode scoreMode = BlockJoinQuery.ScoreMode.Avg;
 
         // we need a late binding filter so we can inject a parent nested filter inner nested queries
         LateBindingParentFilter currentParentFilterContext = parentFilterContext.get();
 
         LateBindingParentFilter usAsParentFilter = new LateBindingParentFilter();
         parentFilterContext.set(usAsParentFilter);
 
         String currentFieldName = null;
         XContentParser.Token token;
         while ((token = parser.nextToken()) != XContentParser.Token.END_OBJECT) {
             if (token == XContentParser.Token.FIELD_NAME) {
                 currentFieldName = parser.currentName();
             } else if (token == XContentParser.Token.START_OBJECT) {
                 if ("query".equals(currentFieldName)) {
                     query = parseContext.parseInnerQuery();
                 } else if ("filter".equals(currentFieldName)) {
                     filter = parseContext.parseInnerFilter();
                 }
             } else if (token.isValue()) {
                 if ("path".equals(currentFieldName)) {
                     path = parser.text();
                 } else if ("boost".equals(currentFieldName)) {
                     boost = parser.floatValue();
                 } else if ("_scope".equals(currentFieldName)) {
                     scope = parser.text();
-                } else if ("score_mode".equals(currentFieldName) || "scoreMode".equals(scoreMode)) {
                     String sScoreMode = parser.text();
                     if ("avg".equals(sScoreMode)) {
                         scoreMode = BlockJoinQuery.ScoreMode.Avg;
                     } else if ("max".equals(sScoreMode)) {
                         scoreMode = BlockJoinQuery.ScoreMode.Max;
                     } else if ("total".equals(sScoreMode)) {
                         scoreMode = BlockJoinQuery.ScoreMode.Total;
                     } else if ("none".equals(sScoreMode)) {
                         scoreMode = BlockJoinQuery.ScoreMode.None;
                     } else {
                         throw new QueryParsingException(parseContext.index(), "illegal score_mode for nested query [" + sScoreMode + "]");
                     }
                 }
             }
         }
         if (query == null && filter == null) {
             throw new QueryParsingException(parseContext.index(), "[nested] requires either 'query' or 'filter' field");
         }
         if (path == null) {
             throw new QueryParsingException(parseContext.index(), "[nested] requires 'path' field");
         }
 
         if (filter != null) {
             query = new DeletionAwareConstantScoreQuery(filter);
         }
 
         MapperService.SmartNameObjectMapper mapper = parseContext.smartObjectMapper(path);
         if (mapper == null) {
             throw new QueryParsingException(parseContext.index(), "[nested] failed to find nested object under path [" + path + "]");
         }
         ObjectMapper objectMapper = mapper.mapper();
         if (objectMapper == null) {
             throw new QueryParsingException(parseContext.index(), "[nested] failed to find nested object under path [" + path + "]");
         }
         if (!objectMapper.nested().isNested()) {
             throw new QueryParsingException(parseContext.index(), "[nested] nested object under path [" + path + "] is not of nested type");
         }
 
         Filter childFilter = parseContext.cacheFilter(objectMapper.nestedTypeFilter(), null);
         usAsParentFilter.filter = childFilter;
         // wrap the child query to only work on the nested path type
         query = new FilteredQuery(query, childFilter);
 
         Filter parentFilter = currentParentFilterContext;
         if (parentFilter == null) {
             parentFilter = NonNestedDocsFilter.INSTANCE;
             if (mapper.hasDocMapper()) {
                 // filter based on the type...
                 parentFilter = mapper.docMapper().typeFilter();
             }
             parentFilter = parseContext.cacheFilter(parentFilter, null);
         }
 
         // restore the thread local one...
         parentFilterContext.set(currentParentFilterContext);
 
         BlockJoinQuery joinQuery = new BlockJoinQuery(query, parentFilter, scoreMode);
         joinQuery.setBoost(boost);
 
         if (scope != null) {
             SearchContext.current().addNestedQuery(scope, joinQuery);
         }
 
         return joinQuery;
     }
 
     static ThreadLocal<LateBindingParentFilter> parentFilterContext = new ThreadLocal<LateBindingParentFilter>();
 
     static class LateBindingParentFilter extends Filter {
 
         Filter filter;
 
         @Override public int hashCode() {
             return filter.hashCode();
         }
 
         @Override public boolean equals(Object obj) {
             return filter.equals(obj);
         }
 
         @Override public String toString() {
             return filter.toString();
         }
 
         @Override public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
             return filter.getDocIdSet(reader);
         }
     }
 }
