There has been a change in an else statement, in the section of code starting on line nr 322.
  
The change is in the constructorcall ```com.badlogic.gdx.backends.lwjgl.LwjglPreferences(java.lang.String,java.lang.String)```, in a localvariable, in an else clause, in an if clause, in the method ```getPreferences```, in the class ```LwjglAWTCanvas```.
  
The following changes have been made:  
Changed constructor call from ```new com.badlogic.gdx.backends.lwjgl.LwjglPreferences(name, ".prefs/")``` to ```new com.badlogic.gdx.backends.lwjgl.LwjglPreferences(name)``` on line 322.  
