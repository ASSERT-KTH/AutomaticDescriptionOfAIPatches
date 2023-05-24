There has been a change in the method getDependencies, in the section of code starting on line nr 63.
  
The change is in the constructorcall ```com.badlogic.gdx.utils.Array()```, in a localvariable, in the method ```getDependencies```, in the class ```TextureAtlasLoader```.
  
The following changes have been made:  
Changed constructor call from ```new com.badlogic.gdx.utils.Array()``` to ```new com.badlogic.gdx.utils.Array<com.badlogic.gdx.assets.AssetDescriptor>()``` on line 63.  
