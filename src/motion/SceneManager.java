
/*
 * This file is part of Jkop for Android
 * Copyright (c) 2016-2017 Job and Esther Technologies, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package motion;

public class SceneManager
{
	public static SceneManager forScene(Scene scene, Backend backend) {
		SceneManager v = new SceneManager();
		v.setBackend(backend);
		v.pushScene(scene);
		return(v);
	}

	private cape.Stack<Scene> sceneStack = null;
	private boolean isInitialized = false;
	private boolean isStarted = false;
	private Backend backend = null;
	private cave.GuiApplicationContext context = null;

	public SceneManager() {
		sceneStack = new cape.Stack<Scene>();
	}

	public void initialize() {
		isInitialized = true;
		Scene scene = sceneStack.peek();
		if(scene != null) {
			scene.initialize();
		}
	}

	public void start() {
		isStarted = true;
		Scene scene = sceneStack.peek();
		if(scene != null) {
			scene.start();
		}
	}

	public void stop() {
		isStarted = false;
		Scene scene = sceneStack.peek();
		if(scene != null) {
			scene.stop();
		}
	}

	public void cleanup() {
		isInitialized = false;
		while(doPopScene()) {
			;
		}
	}

	private void onNewCurrentScene() {
		Scene scene = sceneStack.peek();
		if(scene == null) {
			return;
		}
		if(isInitialized) {
			scene.initialize();
		}
		if(isStarted) {
			scene.start();
		}
	}

	public Scene getCurrentScene() {
		return(sceneStack.peek());
	}

	public void replaceScene(Scene next) {
		doReplaceCurrentScene(next);
	}

	private void doReplaceCurrentScene(Scene next) {
		popScene();
		pushScene(next);
	}

	public void pushScene(Scene scene) {
		Scene currentScene = sceneStack.peek();
		if(currentScene != null) {
			if(isStarted) {
				currentScene.stop();
			}
			if(isInitialized) {
				currentScene.cleanup();
			}
		}
		if(scene != null) {
			scene.setContext(context);
			scene.setBackend(backend);
			scene.setManager(this);
			sceneStack.push((Scene)scene);
			onNewCurrentScene();
		}
	}

	public void popScene() {
		doPopScene();
	}

	private boolean doPopScene() {
		Scene currentScene = sceneStack.pop();
		if(currentScene != null) {
			if(isStarted) {
				currentScene.stop();
			}
			if(isInitialized) {
				currentScene.cleanup();
			}
			currentScene.setManager(null);
			currentScene.setBackend(null);
			onNewCurrentScene();
			return(true);
		}
		return(false);
	}

	public Backend getBackend() {
		return(backend);
	}

	public SceneManager setBackend(Backend v) {
		backend = v;
		return(this);
	}

	public cave.GuiApplicationContext getContext() {
		return(context);
	}

	public SceneManager setContext(cave.GuiApplicationContext v) {
		context = v;
		return(this);
	}
}
