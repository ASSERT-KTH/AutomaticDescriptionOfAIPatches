+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/8123.java	2018-10-25 14:41:07.971011126 +0200
@@ -1,16 +1,16 @@
 package com.github.mobile.android.ui.user;
 
 import com.github.mobile.android.ResourcePager;
 
 import org.eclipse.egit.github.core.event.Event;
 
 /**
  * Pager over events
  */
 public abstract class EventPager extends ResourcePager<Event> {
 
     @Override
+    protected Object getId(Event resource) {
         return resource.getId();
     }
 }
