--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/13753.java	2018-10-25 14:41:39.199520655 +0200
@@ -1,21 +1,21 @@
 
 package com.badlogic.gdxinvaders;
 
 import android.content.pm.ActivityInfo;
 import android.os.Bundle;
 
 import com.badlogic.gdx.backends.android.AndroidApplication;
 import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
 
 public class GdxInvadersAndroid extends AndroidApplication {
 	/** Called when the activity is first created. */
 	@Override
 	public void onCreate (Bundle savedInstanceState) {
 		super.onCreate(savedInstanceState);
 		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
 		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
 		config.useWakelock = true;
-		config.useGL20 = true;
 		initialize(new GdxInvaders(), config);
 	}
 }
