There has been a change in the method resize, in the section of code starting on line nr 51.
  
The change is in the invocation ```update```, in the method ```resize```, in the class ```TouchpadTest```.
  
The following changes have been made:  
Changed invocation from ```stage.getViewport()``` to ```stage.setViewport(width, height, true)``` on line 51.  
Deleted invocation ```stage.getViewport().update(width, height)``` on line 51.  
Moved invocation ```stage.getViewport()``` from line 51 to line 51.  
