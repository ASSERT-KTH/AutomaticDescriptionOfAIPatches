There has been a change in the method parse, in the section of code starting on line nr 95.
  
The change is in the invocation ```cacheFilter```, in the constructorcall ```org.apache.lucene.search.FilteredQuery()```, in an assignment, in the method ```parse```, in the class ```HasChildQueryParser```.
  
The following changes have been made:  
Inserted literal ```null``` on line 95.  
