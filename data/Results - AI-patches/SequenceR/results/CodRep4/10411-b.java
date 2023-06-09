--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/10411.java	2018-10-25 14:41:40.146902206 +0200
@@ -1,21 +1,21 @@
 
 package com.badlogic.gdx.scenes.scene2d.actions;
 
 import com.badlogic.gdx.scenes.scene2d.Event;
 
 /** An EventAction that is complete once it receives X number of events.
  * @author JavadocMD
  * @author Nathan Sweet */
 public class CountdownEventAction<T extends Event> extends EventAction<T> {
 	int count, current;
 
 	public CountdownEventAction (Class<? extends T> eventClass, int count) {
 		super(eventClass);
 		this.count = count;
 	}
 
 	public boolean handle (T event) {
 		current++;
-		return current > count;
 	}
 }
