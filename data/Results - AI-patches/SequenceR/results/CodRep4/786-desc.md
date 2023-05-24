There has been a change in the method build, in the section of code starting on line nr 88.
  
The change is in a binary operator, in the invocation ```setOmitNorms```, in the method ```build```, in the class ```Builder```, in the class ```LongFieldMapper```.
  
The following changes have been made:  
Changed binary operator from ```fieldType.omitNorms() || (boost != 1.0F)``` to ```fieldType.omitNorms() && (boost != 1.0F)``` on line 88.  
