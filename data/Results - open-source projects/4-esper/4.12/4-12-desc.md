There has been a change in the class TestUseResultPattern, in the section of code starting on line nr 175.
  
The change is in the class ```TestUseResultPattern```.
  
The following changes have been made:  
Changed invocation from ```EPServiceProviderManager.getProvider("testRFIDZoneEnter", config)``` to ```EPServiceProviderManager.getDefaultProvider(config)``` on line 175.  
