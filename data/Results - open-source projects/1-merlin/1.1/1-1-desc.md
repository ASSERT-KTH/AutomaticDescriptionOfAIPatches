There has been a change in the method registerables, in the section of code starting on line nr 21.
  
The change is in a return statement, in the method ```registerables```, in the class ```Register```.
  
The following changes have been made:  
Inserted constructor call ```new java.util.ArrayList<>(registerables)``` on line 21.  
Moved field read ```registerables``` from line 21 to line 21.  
