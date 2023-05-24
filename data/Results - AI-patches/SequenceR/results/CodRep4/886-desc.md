There has been a change in the method load, in the section of code starting on line nr 167.
  
The change is in a binary operator, in a localvariable, in the method ```load```, in the class ```JniGenSharedLibraryLoader```.
  
The following changes have been made:  
Deleted binary operator ```java.lang.System.getProperty("os.arch").equals("amd64") || java.lang.System.getProperty("os.arch").equals("x86_64")``` on line 167.  
Moved invocation ```java.lang.System.getProperty("os.arch").equals("amd64")``` from line 167 to line 167.  
