+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/13180.java	2018-10-25 14:41:07.167648293 +0200
@@ -1,15 +1,15 @@
 
 package com.dozingcatsoftware.bouncy;
 
 import android.os.Bundle;
 
 import com.badlogic.gdx.backends.android.AndroidApplication;
 
 public class BouncyAndroid extends AndroidApplication {
 	/** Called when the activity is first created. */
 	@Override
 	public void onCreate (Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
+		initialize(new Bouncy());
 	}
 }
