--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/5623.java	2018-10-25 14:41:38.638623105 +0200
@@ -1,16 +1,16 @@
 
 package com.badlogic.metagun;
 
 import android.os.Bundle;
 
 import com.badlogic.gdx.backends.android.AndroidApplication;
 import com.mojang.metagun.Metagun;
 
 public class MetagunAndroid extends AndroidApplication {
 	/** Called when the activity is first created. */
 	@Override
 	public void onCreate (Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
-		initialize(new Metagun(), false);
 	}
 }
