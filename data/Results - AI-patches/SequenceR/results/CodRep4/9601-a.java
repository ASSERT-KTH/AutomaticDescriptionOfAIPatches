+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/9601.java	2018-10-25 14:41:11.050691428 +0200
@@ -1,10 +1,10 @@
 
 package com.dozingcatsoftware.bouncy;
 
 import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
 
 public class BouncyDesktop {
 	public static void main (String[] argv) {
+		new LwjglApplication(new Bouncy(), "Bouncy", 320, 480, true);
 	}
 }
