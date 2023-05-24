There has been a change in the class RedisApplicationTestIT, in the section of code starting on line nr 46.
  
The change is in the class ```RedisApplicationTestIT```.
  
The following changes have been made:  
Inserted field starting with ```private StringRedisTemplate redisTemplate;``` with the tag ```@Autowired``` on line 46.  
Inserted method ```void resetStorage()``` with the tag ```@BeforeEach``` on line 49.  
