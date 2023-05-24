+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/16592.java	2018-10-25 14:41:08.129462016 +0200
@@ -1,30 +1,30 @@
 /*******************************************************************************
  * Copyright 2011 See AUTHORS file.
  * 
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  * 
  *   http://www.apache.org/licenses/LICENSE-2.0
  * 
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  ******************************************************************************/
 
 package com.badlogic.gdx.tests.lwjgl;
 
 import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
 import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
 import com.badlogic.gdx.tests.ShapeRendererTest;
 
 public class LwjglDebugStarter {
 	public static void main (String[] argv) {
 		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
+		config.useGL20 = true;
 		config.vSyncEnabled = true;
 		new LwjglApplication(new ShapeRendererTest(), config);
 	}
 }
