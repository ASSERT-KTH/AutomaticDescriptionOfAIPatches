--- /Users/zimin/eclipse-workspace/statementDiff/data/CodRep_versions/Dataset4_versions/pre_versions/14604.java	2018-10-25 14:41:39.472603759 +0200
@@ -1,638 +1,638 @@
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
 package com.badlogic.gdx.tests.gwt;
 
 import com.badlogic.gdx.Gdx;
 import com.badlogic.gdx.Input;
 import com.badlogic.gdx.InputAdapter;
 import com.badlogic.gdx.InputMultiplexer;
 import com.badlogic.gdx.InputProcessor;
 import com.badlogic.gdx.graphics.Color;
 import com.badlogic.gdx.graphics.GL20;
 import com.badlogic.gdx.graphics.g2d.BitmapFont;
 import com.badlogic.gdx.scenes.scene2d.ActorEvent;
 import com.badlogic.gdx.scenes.scene2d.Stage;
 import com.badlogic.gdx.scenes.scene2d.ui.Label;
 import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
 import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
 import com.badlogic.gdx.scenes.scene2d.ui.Skin;
 import com.badlogic.gdx.scenes.scene2d.ui.Table;
 import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
 import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
 import com.badlogic.gdx.tests.AccelerometerTest;
 import com.badlogic.gdx.tests.ActionSequenceTest;
 import com.badlogic.gdx.tests.ActionTest;
 import com.badlogic.gdx.tests.AlphaTest;
 import com.badlogic.gdx.tests.AnimationTest;
 import com.badlogic.gdx.tests.AssetManagerTest;
 import com.badlogic.gdx.tests.AtlasIssueTest;
 import com.badlogic.gdx.tests.BitmapFontAlignmentTest;
 import com.badlogic.gdx.tests.BitmapFontFlipTest;
 import com.badlogic.gdx.tests.BitmapFontTest;
 import com.badlogic.gdx.tests.BlitTest;
 import com.badlogic.gdx.tests.Box2DCharacterControllerTest;
 import com.badlogic.gdx.tests.Box2DTest;
 import com.badlogic.gdx.tests.Box2DTestCollection;
 import com.badlogic.gdx.tests.ComplexActionTest;
 import com.badlogic.gdx.tests.CustomShaderSpriteBatchTest;
 import com.badlogic.gdx.tests.DecalTest;
 import com.badlogic.gdx.tests.EdgeDetectionTest;
 import com.badlogic.gdx.tests.FilterPerformanceTest;
 import com.badlogic.gdx.tests.FrameBufferTest;
 import com.badlogic.gdx.tests.GestureDetectorTest;
 import com.badlogic.gdx.tests.GroupCullingTest;
 import com.badlogic.gdx.tests.GroupFadeTest;
 import com.badlogic.gdx.tests.ImageScaleTest;
 import com.badlogic.gdx.tests.ImageTest;
 import com.badlogic.gdx.tests.IndexBufferObjectShaderTest;
 import com.badlogic.gdx.tests.IntegerBitmapFontTest;
 import com.badlogic.gdx.tests.InverseKinematicsTest;
 import com.badlogic.gdx.tests.IsoCamTest;
 import com.badlogic.gdx.tests.IsometricTileTest;
 import com.badlogic.gdx.tests.KinematicBodyTest;
 import com.badlogic.gdx.tests.LabelTest;
 import com.badlogic.gdx.tests.LifeCycleTest;
 import com.badlogic.gdx.tests.MeshShaderTest;
 import com.badlogic.gdx.tests.MipMapTest;
 import com.badlogic.gdx.tests.MultitouchTest;
 import com.badlogic.gdx.tests.MusicTest;
 import com.badlogic.gdx.tests.ParallaxTest;
 import com.badlogic.gdx.tests.ParticleEmitterTest;
 import com.badlogic.gdx.tests.PixelsPerInchTest;
 import com.badlogic.gdx.tests.ProjectiveTextureTest;
 import com.badlogic.gdx.tests.RotationTest;
 import com.badlogic.gdx.tests.ShadowMappingTest;
 import com.badlogic.gdx.tests.ShapeRendererTest;
 import com.badlogic.gdx.tests.SimpleAnimationTest;
 import com.badlogic.gdx.tests.SimpleDecalTest;
 import com.badlogic.gdx.tests.SimpleStageCullingTest;
 import com.badlogic.gdx.tests.SortedSpriteTest;
 import com.badlogic.gdx.tests.SoundTest;
 import com.badlogic.gdx.tests.SpriteBatchShaderTest;
 import com.badlogic.gdx.tests.SpriteCacheOffsetTest;
 import com.badlogic.gdx.tests.SpriteCacheTest;
 import com.badlogic.gdx.tests.StageTest;
 import com.badlogic.gdx.tests.TableTest;
 import com.badlogic.gdx.tests.TextButtonTest;
 import com.badlogic.gdx.tests.TextButtonTestGL2;
 import com.badlogic.gdx.tests.TextureAtlasTest;
 import com.badlogic.gdx.tests.UITest;
 import com.badlogic.gdx.tests.VertexBufferObjectShaderTest;
 import com.badlogic.gdx.tests.YDownTest;
 import com.badlogic.gdx.tests.utils.GdxTest;
 
 public class GwtTestWrapper extends GdxTest {
 	Stage ui;
 	Table container;
 	Skin skin;
 	BitmapFont font;
 	GdxTest test;
 	boolean dispose;
 
 	@Override
 	public void create () {
 		ui = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
-		skin = new Skin(Gdx.files.internal("data/uiskin.json"), Gdx.files.internal("data/uiskin.png"));
 		font = new BitmapFont(Gdx.files.internal("data/arial-15.fnt"), false);
 		container = new Table();
 		ui.addActor(container);
 		container.debug();
 		Table table = new Table();
 		ScrollPane scroll = new ScrollPane(table);
 		container.add(scroll).expand().fill();
 		table.pad(10).defaults().expandX().space(4);
 		for (final Instancer instancer : tests) {
 			table.row();
 			TextButton button = new TextButton(instancer.instance().getClass().getName(), skin);
 			button.addListener(new ClickListener() {
 				@Override
 				public void clicked (ActorEvent event, float x, float y) {
 					((InputWrapper)Gdx.input).multiplexer.removeProcessor(ui);
 					test = instancer.instance();
 					test.create();
 					test.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
 				}
 			});
 			table.add(button).expandX().fillX();
 		}
 		container.row();
 		container
 			.add(new Label("Click on a test to start it, press ESC to close it.", new LabelStyle(font, Color.WHITE)))
 			.pad(5, 5, 5, 5);
 
 		Gdx.input = new InputWrapper(Gdx.input) {
 			@Override
 			public boolean keyUp (int keycode) {
 				if (keycode == Keys.ESCAPE) {
 					dispose = true;
 				}
 				return false;
 			}
 		};
 		((InputWrapper)Gdx.input).multiplexer.addProcessor(ui);
 	}
 
 	public void render () {
 		if (test == null) {
 			Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
 			Gdx.gl.glClearColor(0, 0, 0, 0);
 			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
 			ui.act(Gdx.graphics.getDeltaTime());
 			ui.draw();
 		} else {
 			if (dispose) {
 				test.pause();
 				test.dispose();
 				test = null;
 				Gdx.graphics.setVSync(true);
 				InputWrapper wrapper = ((InputWrapper)Gdx.input);
 				wrapper.multiplexer.addProcessor(ui);
 				wrapper.multiplexer.removeProcessor(wrapper.lastProcessor);
 				wrapper.lastProcessor = null;
 				dispose = false;
 			} else {
 				test.render();
 			}
 		}
 	}
 
 	public void resize (int width, int height) {
 		ui.setViewport(width, height, false);
 		container.setSize(width, height);
 	}
 
 	class InputWrapper extends InputAdapter implements Input {
 		Input input;
 		InputProcessor lastProcessor;
 		InputMultiplexer multiplexer;
 
 		public InputWrapper (Input input) {
 			this.input = input;
 			this.multiplexer = new InputMultiplexer();
 			this.multiplexer.addProcessor(this);
 			input.setInputProcessor(multiplexer);
 		}
 
 		@Override
 		public float getAccelerometerX () {
 			return input.getAccelerometerX();
 		}
 
 		@Override
 		public float getAccelerometerY () {
 			return input.getAccelerometerY();
 		}
 
 		@Override
 		public float getAccelerometerZ () {
 			return input.getAccelerometerZ();
 		}
 
 		@Override
 		public int getX () {
 			return input.getX();
 		}
 
 		@Override
 		public int getX (int pointer) {
 			return input.getX(pointer);
 		}
 
 		@Override
 		public int getDeltaX () {
 			return input.getDeltaX();
 		}
 
 		@Override
 		public int getDeltaX (int pointer) {
 			return input.getDeltaX(pointer);
 		}
 
 		@Override
 		public int getY () {
 			return input.getY();
 		}
 
 		@Override
 		public int getY (int pointer) {
 			return input.getY(pointer);
 		}
 
 		@Override
 		public int getDeltaY () {
 			return input.getDeltaY();
 		}
 
 		@Override
 		public int getDeltaY (int pointer) {
 			return input.getDeltaY(pointer);
 		}
 
 		@Override
 		public boolean isTouched () {
 			return input.isTouched();
 		}
 
 		@Override
 		public boolean justTouched () {
 			return input.justTouched();
 		}
 
 		@Override
 		public boolean isTouched (int pointer) {
 			return input.isTouched(pointer);
 		}
 
 		@Override
 		public boolean isButtonPressed (int button) {
 			return input.isButtonPressed(button);
 		}
 
 		@Override
 		public boolean isKeyPressed (int key) {
 			return input.isKeyPressed(key);
 		}
 
 		@Override
 		public void getTextInput (TextInputListener listener, String title, String text) {
 			input.getTextInput(listener, title, text);
 		}
 
 		@Override
 		public void getPlaceholderTextInput (TextInputListener listener, String title, String placeholder) {
 			input.getPlaceholderTextInput(listener, title, placeholder);
 		}
 
 		@Override
 		public void setOnscreenKeyboardVisible (boolean visible) {
 			input.setOnscreenKeyboardVisible(visible);
 		}
 
 		@Override
 		public void vibrate (int milliseconds) {
 			input.vibrate(milliseconds);
 		}
 
 		@Override
 		public void vibrate (long[] pattern, int repeat) {
 			input.vibrate(pattern, repeat);
 		}
 
 		@Override
 		public void cancelVibrate () {
 			input.cancelVibrate();
 		}
 
 		@Override
 		public float getAzimuth () {
 			return input.getAzimuth();
 		}
 
 		@Override
 		public float getPitch () {
 			return input.getPitch();
 		}
 
 		@Override
 		public float getRoll () {
 			return input.getRoll();
 		}
 
 		@Override
 		public void getRotationMatrix (float[] matrix) {
 			input.getRotationMatrix(matrix);
 		}
 
 		@Override
 		public long getCurrentEventTime () {
 			return input.getCurrentEventTime();
 		}
 
 		@Override
 		public void setCatchBackKey (boolean catchBack) {
 			input.setCatchBackKey(catchBack);
 		}
 
 		@Override
 		public void setCatchMenuKey (boolean catchMenu) {
 			input.setCatchMenuKey(catchMenu);
 		}
 
 		@Override
 		public void setInputProcessor (InputProcessor processor) {
 			multiplexer.removeProcessor(lastProcessor);
 			multiplexer.addProcessor(processor);
 			lastProcessor = processor;
 		}
 
 		@Override
 		public InputProcessor getInputProcessor () {
 			return input.getInputProcessor();
 		}
 
 		@Override
 		public boolean isPeripheralAvailable (Peripheral peripheral) {
 			return input.isPeripheralAvailable(peripheral);
 		}
 
 		@Override
 		public int getRotation () {
 			return input.getRotation();
 		}
 
 		@Override
 		public Orientation getNativeOrientation () {
 			return input.getNativeOrientation();
 		}
 
 		@Override
 		public void setCursorCatched (boolean catched) {
 			input.setCursorCatched(catched);
 		}
 
 		@Override
 		public boolean isCursorCatched () {
 			return input.isCursorCatched();
 		}
 
 		@Override
 		public void setCursorPosition (int x, int y) {
 			setCursorPosition(x, y);
 		}
 	}
 
 	interface Instancer {
 		public GdxTest instance ();
 	}
 
 	Instancer[] tests = {new Instancer() {
 		public GdxTest instance () {
 			return new AccelerometerTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new ActionTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new ActionSequenceTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new AlphaTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new AnimationTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new AssetManagerTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new AtlasIssueTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new BitmapFontAlignmentTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new BitmapFontFlipTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new BitmapFontTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new BlitTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new Box2DCharacterControllerTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new Box2DTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new Box2DTestCollection();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new ComplexActionTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new CustomShaderSpriteBatchTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new DecalTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new EdgeDetectionTest();
 		}
 	}, new Instancer() {
 		public GdxTest instance () {
 			return new FilterPerformanceTest();
 		}
 	},
 // new Instancer() {public GdxTest instance(){return new FlickScrollPaneTest();}}, // FIXME this messes up stuff, why?
 		new Instancer() {
 			public GdxTest instance () {
 				return new FrameBufferTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new GestureDetectorTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new GroupCullingTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new GroupFadeTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new ImageScaleTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new ImageTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new IndexBufferObjectShaderTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new IntegerBitmapFontTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new InverseKinematicsTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new IsoCamTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new IsometricTileTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new KinematicBodyTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new LifeCycleTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new LabelTest();
 			}
 		},
 // new Instancer() {public GdxTest instance(){return new MatrixJNITest();}}, // No purpose
 		new Instancer() {
 			public GdxTest instance () {
 				return new MeshShaderTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new MipMapTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new MultitouchTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new MusicTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new ParallaxTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new ParticleEmitterTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new PixelsPerInchTest();
 			}
 		},
 // new Instancer() {public GdxTest instance(){return new PixmapBlendingTest();}}, // FIXME no idea why this doesn't work
 		new Instancer() {
 			public GdxTest instance () {
 				return new ProjectiveTextureTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new RotationTest();
 			}
 		},
 // new Instancer() {public GdxTest instance(){return new ScrollPaneTest();}}, // FIXME this messes up stuff, why?
 // new Instancer() {public GdxTest instance(){return new ShaderMultitextureTest();}}, // FIXME fucks up stuff
 		new Instancer() {
 			public GdxTest instance () {
 				return new ShadowMappingTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new ShapeRendererTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new SimpleAnimationTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new SimpleDecalTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new SimpleStageCullingTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new SortedSpriteTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new SpriteBatchShaderTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new SpriteCacheOffsetTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new SpriteCacheTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new SoundTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new StageTest();
 			}
 		},
 // new Instancer() {public GdxTest instance(){return new StagePerformanceTest();}}, // FIXME borks out
 		new Instancer() {
 			public GdxTest instance () {
 				return new TableTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new TextButtonTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new TextButtonTestGL2();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new TextureAtlasTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new UITest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new VertexBufferObjectShaderTest();
 			}
 		}, new Instancer() {
 			public GdxTest instance () {
 				return new YDownTest();
 			}
 		},};
 
 	@Override
 	public boolean needsGL20 () {
 		return true;
 	}
 }