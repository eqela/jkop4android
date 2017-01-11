
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

public class SpriteScene extends Scene implements SpriteLayer
{
	private SpriteLayer layer = null;
	private cave.Color backgroundColor = null;
	private Texture backgroundTexture = null;
	private TextureSprite backgroundSprite = null;

	public void setBackgroundColor(cave.Color color) {
		backgroundColor = color;
		updateBackgroundColor();
	}

	public Texture getBackgroundTexture() {
		if(backgroundTexture != null) {
			return(backgroundTexture);
		}
		if(backgroundColor != null) {
			return(createTextureForColor(backgroundColor));
		}
		return(null);
	}

	private void updateBackgroundColor() {
		if(backgroundSprite == null) {
			if(layer != null) {
				Texture txt = getBackgroundTexture();
				if(txt != null) {
					backgroundSprite = layer.addTextureSpriteForSize(txt, layer.getReferenceWidth(), layer.getReferenceHeight());
					backgroundSprite.move((double)0, (double)0);
				}
			}
		}
		else {
			Texture txt = getBackgroundTexture();
			if(txt != null) {
				backgroundSprite.setTexture(txt);
			}
		}
	}

	@Override
	public void initialize() {
		super.initialize();
		layer = backend.createSpriteLayer();
		updateBackgroundColor();
	}

	@Override
	public void cleanup() {
		super.cleanup();
		layer.removeAllSprites();
		backend.deleteSpriteLayer(layer);
		layer = null;
		backgroundSprite = null;
	}

	@Override
	public void onPointerEvent(cave.PointerEvent event) {
		if(layer != null) {
			event.x = event.x * layer.getReferenceWidth();
			event.y = event.y * layer.getReferenceHeight();
		}
	}

	public TextureSprite addTextureSpriteForSize(Texture texture, double width, double height) {
		return(layer.addTextureSpriteForSize(texture, width, height));
	}

	public TextSprite addTextSprite(TextProperties text) {
		return(layer.addTextSprite(text));
	}

	public ContainerSprite addContainerSprite(double width, double height) {
		return(layer.addContainerSprite(width, height));
	}

	public void removeSprite(Sprite sprite) {
		layer.removeSprite(sprite);
	}

	public void removeAllSprites() {
		layer.removeAllSprites();
	}

	public void setReferenceWidth(double referenceWidth) {
		layer.setReferenceWidth(referenceWidth);
	}

	public void setReferenceHeight(double referenceHeight) {
		layer.setReferenceHeight(referenceHeight);
	}

	public double getReferenceWidth() {
		return(layer.getReferenceWidth());
	}

	public double getReferenceHeight() {
		return(layer.getReferenceHeight());
	}

	public double getHeightValue(java.lang.String spec) {
		return(layer.getHeightValue(spec));
	}

	public double getWidthValue(java.lang.String spec) {
		return(layer.getWidthValue(spec));
	}

	public SpriteLayer getLayer() {
		return(layer);
	}

	public SpriteScene setLayer(SpriteLayer v) {
		layer = v;
		return(this);
	}
}
