There has been a change in the condition in an if statement, in the section of code starting on line nr 67.
  
The change is in a binary operator, in an if clause, in a for each statement, in a lambda expression, in a return statement, in the method ```getItemHandler```, in the class ```Juice```.
  
The following changes have been made:  
Deleted invocation ```effect.getType().equals(PotionEffectType.SATURATION)``` on line 67.  
Deleted invocation ```effect.getType().equals(PotionEffectType.ABSORPTION)``` on line 67.  
Inserted binary operator ```(effect.getType() == PotionEffectType.SATURATION)``` on line 67.  
Inserted binary operator ```(effect.getType() == PotionEffectType.ABSORPTION)``` on line 67.  
Moved invocation ```effect.getType()``` from line 67 to line 67.  
Moved variable read ```PotionEffectType.SATURATION``` from line 67 to line 67.  
Moved invocation ```effect.getType()``` from line 67 to line 67.  
Moved variable read ```PotionEffectType.ABSORPTION``` from line 67 to line 67.  
