+++ /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/post_versions/2175.java	2018-10-25 14:41:14.569935642 +0200
@@ -1,34 +1,34 @@
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
 
 package com.badlogic.gdx.tests;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.tests.utils.GdxTest;
 import com.badlogic.gdx.utils.Timer;
 import com.badlogic.gdx.utils.Timer.Task;
 
 public class TimerTest extends GdxTest {
 	@Override
 	public void create () {
 		new Timer().scheduleTask(new Task() {
 			@Override
 			public void run () {
 				Gdx.app.log("TimerTest", "ping");
 			}
+		}, 0, 1);
 	}
 }
