+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/14392.java	2018-10-25 14:41:08.626432006 +0200
@@ -1,10 +1,10 @@
 
 package com.mojang.metagun;
 
 import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
 
 public class MetagunDesktop {
 	public static void main (String[] argv) {
+		new LwjglApplication(new Metagun(), "Metagun", 320, 240, false);
 	}
 }
