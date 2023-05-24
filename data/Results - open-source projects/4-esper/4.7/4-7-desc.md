There has been a change in the condition in an if statement, in the section of code starting on line nr 380.
  
The change is in an if clause, in a for each statement, in a try statement, in the method ```evaluateFilterForStatement```, in the class ```StatementAgentInstanceUtil```.
  
The following changes have been made:  
Deleted binary operator ```handle == filterHandle``` on line 380.  
Inserted invocation ```handle.equals(filterHandle)``` on line 380.  
Moved variable read ```handle``` from line 380 to line 380.  
Moved variable read ```filterHandle``` from line 380 to line 380.  
