There has been a change in the method deassignService, in the section of code starting on line nr 178.
  
The change is in the method ```deassignService```, in the class ```AIRegistryExprBase```.
  
The following changes have been made:  
Inserted for each ```starting with for (java.util.Map.Entry<com.espertech.esper.epl.expression.table.ExprTableAccessNode, com.espertech.esper.core.context.stmt.AIRegistryTableAccess> entry : tableAccess.entrySet()) {``` on line 178.  
Inserted invocation ```matchRecognizePrevious.deassignService(agentInstanceId)``` on line 181.  
