There has been a change in the class HashCalculatorFragment, in the section of code starting on line nr 291.
  
The change is in the class ```HashCalculatorFragment```.
  
The following changes have been made:  
Changed invocation from ```cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)``` to ```cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)``` on line 291.  
