--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/13175.java	2018-10-25 14:41:38.709191624 +0200
@@ -1,16 +1,16 @@
 
 package com.badlogic.cubocy;
 
 import com.badlogic.gdx.Application;
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
 
 public class CubocDesktop {
 	public static void main (String[] argv) {
-		new LwjglApplication(new Cubocy(), "Cubocy", 480, 320, true);
 
 		// After creating the Application instance we can set the log level to
 		// show the output of calls to Gdx.app.debug
 		Gdx.app.setLogLevel(Application.LOG_DEBUG);
 	}
 }
