+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/10673.java	2018-10-25 14:41:28.807644082 +0200
@@ -1,9 +1,9 @@
 package com.badlogic.cubocy;
 
 import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
 
 public class CubocDesktop {
 	public static void main (String[] argv) {
+		new LwjglApplication(new Cubocy(), "Cubocy", 480, 320, true);
 	}
 }
