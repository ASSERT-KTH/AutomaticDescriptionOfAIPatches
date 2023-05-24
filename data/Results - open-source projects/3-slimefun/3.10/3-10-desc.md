There has been a change in the method onApproachGround, in the section of code starting on line nr 40.
  
The change is in the method ```onApproachGround```, in the class ```BeeWingsListener```.
  
The following changes have been made:  
Deleted if ```statement with condition wings == null``` on line 40.  
Inserted binary operator ```((wings == null) || (!e.isGliding()))``` on line 40.  
Moved binary operator ```wings == null``` from line 40 to line 40.  
Moved unary operator ```(!e.isGliding())``` from line 44 to line 40.  
