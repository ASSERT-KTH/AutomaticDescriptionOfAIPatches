There has been a change in the condition in an if statement, in the section of code starting on line nr 97.
  
The change is in an if clause, in a for each statement, in the method ```connectedToNetworkTypeForLollipop```, in the class ```MerlinsBeard```.
  
The following changes have been made:  
Inserted binary operator ```(networkInfo != null) && (networkInfo.getType() == networkType)``` on line 97.  
Moved binary operator ```networkInfo.getType() == networkType``` from line 97 to line 97.  
