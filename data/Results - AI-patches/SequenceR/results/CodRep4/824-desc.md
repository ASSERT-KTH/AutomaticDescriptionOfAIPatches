There has been a change in the method create, in the section of code starting on line nr 65.
  
The change is in a binary operator, in the invocation ```log```, in the method ```create```, in the class ```PixmapPackerTest```.
  
The following changes have been made:  
Deleted field read ```atlas.getTextures().size``` on line 65.  
Inserted invocation ```atlas.getTextures().size()``` on line 65.  
Moved invocation ```atlas.getTextures()``` from line 65 to line 65.  
