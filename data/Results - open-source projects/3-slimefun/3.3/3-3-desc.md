There has been a change in the condition in an if statement, in the section of code starting on line nr 107.
  
The change is in the invocation ```isDisabled```, in a unary operator, in a binary operator, in an if clause, in a then clause, in an if clause, in a then clause, in an if clause, in a lambda expression, in a return statement, in the method ```onBlockDispense```, in the class ```BlockPlacer```.
  
The following changes have been made:  
Changed invocation from ```item.isDisabled()``` to ```item.isDisabledIn(dispenser.getWorld())``` on line 107.  
