There has been a change in the method query, in the section of code starting on line nr 63.
  
The change is in a literal, in the constructorcall ```org.apache.lucene.search.DeletionAwareConstantScoreQuery()```, in a return statement, in the method ```query```, in the class ```MissingFieldQueryExtension```.
  
The following changes have been made:  
Deleted literal ```true``` on line 63.  
