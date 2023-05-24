There has been a change in the method resize, in the section of code starting on line nr 187.
  
The change is in the invocation ```update```, in the method ```resize```, in the class ```Scene2dTest```.
  
The following changes have been made:  
Changed invocation from ```stage.getViewport()``` to ```stage.setViewport(width, height, true)``` on line 187.  
Deleted invocation ```stage.getViewport().update(width, height)``` on line 187.  
Moved invocation ```stage.getViewport()``` from line 187 to line 187.  
