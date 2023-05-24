There has been a change in the method findTemplateBuilder, in the section of code starting on line nr 217.
  
The change is in retriving a value, in the invocation ```mappingForName```, in the invocation ```parse```, in a return statement, in the method ```findTemplateBuilder```, in the class ```RootObjectMapper```.
  
The following changes have been made:  
Changed variable read from ```mappingType``` to ```dynamicType``` on line 217.  
