There has been a change in a while loop, in the section of code starting on line nr 94.
  
The change is in a try statement, in a while statement, in the method ```initialize```, in the class ```MongoDbClient```.
  
The following changes have been made:  
Inserted invocation ```builder.serverSelectionTimeout(-1)``` on line 94.  
