There has been a change in the method create, in the section of code starting on line nr 50.
  
The change is in the constructorcall ```com.badlogic.gdx.scenes.scene2d.Stage()```, in an assignment, in the method ```create```, in the class ```SoundTest```.
  
The following changes have been made:  
Deleted invocation ```Gdx.graphics.getWidth()``` on line 50.  
Deleted invocation ```Gdx.graphics.getHeight()``` on line 50.  
Deleted literal ```true``` on line 50.  
