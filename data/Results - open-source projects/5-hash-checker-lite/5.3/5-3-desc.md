There has been a change in the body of an if statement, in the section of code starting on line nr 229.
  
The change is in a then clause, in an if clause, in the method ```waitForDoubleBackPressed```, in the class ```MainActivity```.
  
The following changes have been made:  
Changed invocation from ```startActivity(a)``` to ```finish()``` on line 232.  
Deleted local variable ```android.content.Intent a = new android.content.Intent(android.content.Intent.ACTION_MAIN)``` on line 229.  
Deleted invocation ```a.addCategory(Intent.CATEGORY_HOME)``` on line 230.  
Deleted invocation ```a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)``` on line 231.  
Deleted return ```return``` on line 233.  
