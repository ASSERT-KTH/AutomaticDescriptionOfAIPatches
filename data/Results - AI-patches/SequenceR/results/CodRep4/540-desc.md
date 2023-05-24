There has been a change in the method verifyCache, in the section of code starting on line nr 87.
  
The change is in a literal, in the constructorcall ```org.elasticsearch.index.cache.filter.DeletionAwareConstantScoreQuery()```, in the invocation ```count```, in the invocation ```assertThat```, in the method ```verifyCache```, in the class ```FilterCacheTests```.
  
The following changes have been made:  
Deleted literal ```true``` on line 87.  
