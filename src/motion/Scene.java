
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

public class Scene
{
	protected SceneManager manager = null;
	protected Backend backend = null;
	protected cave.GuiApplicationContext context = null;

	public void setContext(cave.GuiApplicationContext value) {
		this.context = value;
	}

	public Scene setManager(SceneManager value) {
		manager = value;
		return(this);
	}

	public SceneManager getManager() {
		return(manager);
	}

	public Scene setBackend(Backend value) {
		backend = value;
		return(this);
	}

	public Backend getBackend() {
		return(backend);
	}

	public void initialize() {
	}

	public void start() {
	}

	public void onKeyEvent(cave.KeyEvent event) {
	}

	public void onPointerEvent(cave.PointerEvent event) {
	}

	public void tick(cape.TimeValue gameTime, double delta) {
	}

	public void stop() {
	}

	public void cleanup() {
	}

	public cave.Image createImageFromResource(java.lang.String name) {
		return(backend.createImageFromResource(name));
	}

	public Texture createTextureForImageResource(java.lang.String name) {
		cave.Image img = createImageFromResource(name);
		if(img == null) {
			return(null);
		}
		return(createTextureForImage(img));
	}

	public Texture createTextureForImage(cave.Image image) {
		return(backend.createTextureForImage(image));
	}

	public Texture createTextureForColor(cave.Color color) {
		return(backend.createTextureForColor(color));
	}

	public void deleteTexture(Texture texture) {
		backend.deleteTexture(texture);
	}

	public void deleteAllTextures() {
		backend.deleteAllTextures();
	}

	public void replaceScene(Scene scene) {
		if(manager != null) {
			manager.replaceScene(scene);
		}
	}

	public void pushScene(Scene scene) {
		if(manager != null) {
			manager.pushScene(scene);
		}
	}

	public void popScene() {
		if(manager != null) {
			manager.popScene();
		}
	}
}
