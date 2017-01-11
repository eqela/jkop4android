
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

public class SpriteSceneWithEntities extends SpriteScene implements EntityScene
{
	private Entity[] entities = null;
	private java.util.ArrayList<cave.PointerListener> pointerListeners = new java.util.ArrayList<cave.PointerListener>();
	private java.util.ArrayList<cave.KeyListener> keyListeners = new java.util.ArrayList<cave.KeyListener>();
	private int highestIndex = -1;
	private cave.PointerListener capturedPointerListener = null;

	@Override
	public void cleanup() {
		super.cleanup();
		removeAllEntities();
	}

	@Override
	public void tick(cape.TimeValue gameTime, double delta) {
		super.tick(gameTime, delta);
		if(entities != null) {
			int n = 0;
			int m = entities.length;
			for(n = 0 ; n < m ; n++) {
				Entity entity = entities[n];
				if(entity != null) {
					if(entity == null) {
						continue;
					}
					entity.tick(gameTime, delta);
				}
			}
		}
	}

	@Override
	public void onKeyEvent(cave.KeyEvent event) {
		if(keyListeners != null) {
			int n = 0;
			int m = keyListeners.size();
			for(n = 0 ; n < m ; n++) {
				cave.KeyListener keyListener = keyListeners.get(n);
				if(keyListener != null) {
					keyListener.onKeyEvent(event);
					if(event.isConsumed) {
						break;
					}
				}
			}
		}
	}

	@Override
	public void onPointerEvent(cave.PointerEvent event) {
		super.onPointerEvent(event);
		if(capturedPointerListener != null) {
			if(capturedPointerListener.onPointerEvent(event) == false) {
				capturedPointerListener = null;
			}
			event.consume();
			return;
		}
		if(pointerListeners != null) {
			int n = 0;
			int m = pointerListeners.size();
			for(n = 0 ; n < m ; n++) {
				cave.PointerListener pointerListener = pointerListeners.get(n);
				if(pointerListener != null) {
					if(event.isConsumed) {
						break;
					}
					if(pointerListener.onPointerEvent(event)) {
						event.consume();
						capturedPointerListener = pointerListener;
						break;
					}
				}
			}
		}
	}

	public void grow() {
		int nsz = 0;
		if(entities == null) {
			nsz = 1024;
		}
		else {
			nsz = entities.length * 2;
		}
		Entity[] v = new Entity[nsz];
		if(entities != null) {
			int osz = entities.length;
			for(int n = 0 ; n < osz ; n++) {
				v[n] = entities[n];
			}
		}
		entities = v;
	}

	public void addEntity(Entity entity) {
		if(entity == null) {
			return;
		}
		int thisIndex = highestIndex + 1;
		int count = 0;
		if(entities != null) {
			count = entities.length;
		}
		if(thisIndex >= count) {
			grow();
		}
		entity.setScene((EntityScene)this);
		entities[thisIndex] = entity;
		entity.index = thisIndex;
		highestIndex = thisIndex;
		if(entity instanceof cave.PointerListener) {
			pointerListeners.add((cave.PointerListener)entity);
		}
		if(entity instanceof cave.KeyListener) {
			keyListeners.add((cave.KeyListener)entity);
		}
		entity.initialize();
	}

	public void removeEntity(Entity entity) {
		if(entity == null) {
			return;
		}
		int eidx = entity.index;
		if(eidx < 0) {
			return;
		}
		if(entity instanceof cave.PointerListener) {
			cape.Vector.removeValue(pointerListeners, (cave.PointerListener)entity);
		}
		if(entity instanceof cave.KeyListener) {
			cape.Vector.removeValue(keyListeners, (cave.KeyListener)entity);
		}
		entity.cleanup();
		entity.setScene(null);
		entity.index = -1;
		if(highestIndex == eidx) {
			entities[eidx] = null;
		}
		else {
			Entity n = entities[highestIndex];
			entities[eidx] = n;
			if(n != null) {
				n.index = eidx;
			}
			entities[highestIndex] = null;
		}
		highestIndex--;
	}

	public void removeAllEntities() {
		if(entities == null) {
			return;
		}
		int esz = entities.length;
		for(int n = 0 ; n < esz ; n++) {
			Entity e = entities[n];
			if(e == null) {
				continue;
			}
			e.cleanup();
			e.setScene(null);
			e.index = -1;
		}
		entities = null;
	}
}
