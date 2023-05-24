There has been a change in the condition in an if statement, in the section of code starting on line nr 49.
  
The change is in an if clause, in a then clause, in an if clause, in the method ```onInventoryCreativeEvent```, in the class ```MiddleClickListener```.
  
The following changes have been made:  
Inserted binary operator ```(b == null) || (!isActualMiddleClick(e, b))``` on line 50.  
Moved unary operator ```!isActualMiddleClick(e, b)``` from line 49 to line 50.  
