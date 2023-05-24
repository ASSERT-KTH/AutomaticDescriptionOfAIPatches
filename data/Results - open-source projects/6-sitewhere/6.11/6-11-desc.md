There has been a change in the body of an if statement, in the section of code starting on line nr 74.
  
The change is in a then clause, in an if clause, in the method ```process```, in the class ```BatchCommandInvocationHandler```.
  
The following changes have been made:  
Changed constructor call from ```new com.sitewhere.spi.SiteWhereException("Invalid command token referenced by batch command invocation.")``` to ```new com.sitewhere.spi.SiteWhereException(java.lang.String.format("Invalid command token (%s) for device type '%s' referenced by batch command invocation.", deviceCommandToken, type.getName()))``` on line 74.  
Changed literal from ```"Invalid command token referenced by batch command invocation."``` to ```"Invalid command token (%s) for device type '%s' referenced by batch command invocation."``` on line 74.  
Inserted local variable ```getDeviceTypeId())``` on line 75.  
Moved literal ```"Invalid command token referenced by batch command invocation."``` from line 74 to line 77.  
