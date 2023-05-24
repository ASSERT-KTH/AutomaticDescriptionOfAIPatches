There has been a change in the method calcRemaining, in the section of code starting on line nr 76.
  
The change is in a binary operator, in the invocation ```max```, in a return statement, in the method ```calcRemaining```, in the class ```RedisRateLimiter```.
  
The following changes have been made:  
Inserted conditional ```(current != null ? current : 0L)``` on line 76.  
Moved variable read ```current``` from line 76 to line 76.  
