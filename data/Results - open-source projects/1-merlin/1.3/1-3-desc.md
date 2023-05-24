There has been a change in the method forwardInitialNetworkStatus, in the section of code starting on line nr 32.
  
The change is in the method ```forwardInitialNetworkStatus```, in the class ```ConnectivityChangesForwarder```.
  
The following changes have been made:  
Inserted if statement with condition ```bindCallbackManager == null``` on line 32.  
