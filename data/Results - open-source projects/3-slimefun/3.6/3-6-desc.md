There has been a change in the method updateProgressBar, in the section of code starting on line nr 256.
  
The change is in the method ```updateProgressBar```, in the class ```MachineProcessor```.
  
The following changes have been made:  
Inserted if ```statement with condition (remainingTicks <= 0) && (totalTicks <= 0)``` on line 256.  
