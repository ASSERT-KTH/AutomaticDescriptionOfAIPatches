There has been a change in the method testGetRateException, in the section of code starting on line nr 61.
  
The change is in the invocation ```thenThrow```, in the method ```testGetRateException```, in the class ```ConsulRateLimiterTest```.
  
The following changes have been made:  
Changed invocation from ```Mockito.when(objectMapper.readValue(ArgumentMatchers.anyString(), ArgumentMatchers.eq(com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.Rate.class))).thenThrow(new java.io.IOException())``` to ```starting with Mockito.when(objectMapper.readValue(ArgumentMatchers.anyString(), ArgumentMatchers.eq(com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.Rate.class))).thenAnswer(invocation -> {``` on line 61.  
Moved constructor call ```new java.io.IOException()``` from line 61 to line 64.  
