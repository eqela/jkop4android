
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

public class SpriteUtil
{
	public static TextureSprite addColorSprite(Scene scene, SpriteLayer layer, cave.Color color, double width) {
		return(addColorSprite(scene, layer, color, width, 0.00));
	}

	public static TextureSprite addColorSprite(Scene scene, SpriteLayer layer, cave.Color color, double width, double height) {
		Texture text = scene.createTextureForColor(color);
		if(text == null) {
			return(null);
		}
		return(layer.addTextureSpriteForSize(text, width, height));
	}

	public static TextureSprite addTextureSpriteForWidth(Scene scene, SpriteLayer layer, Texture texture, double width) {
		return(layer.addTextureSpriteForSize(texture, width, 0.00));
	}

	public static TextureSprite addTextureSpriteForHeight(Scene scene, SpriteLayer layer, Texture texture, double height) {
		return(layer.addTextureSpriteForSize(texture, 0.00, height));
	}

	public static TextureSprite addTextureSpriteForSize(Scene scene, SpriteLayer layer, Texture texture, double width, double height) {
		return(layer.addTextureSpriteForSize(texture, width, height));
	}

	public static TextSprite addTextSprite(Scene scene, SpriteLayer layer, java.lang.String text) {
		return(layer.addTextSprite(motion.TextProperties.forText(text)));
	}

	public static TextSprite addTextSpriteWithAbsoluteSize(Scene scene, SpriteLayer layer, java.lang.String text, double size) {
		TextProperties tp = motion.TextProperties.forText(text);
		tp.setFontSizeAbsolute(size);
		return(layer.addTextSprite(tp));
	}

	public static TextSprite addTextSpriteWithRelativeSize(Scene scene, SpriteLayer layer, java.lang.String text, double size) {
		TextProperties tp = motion.TextProperties.forText(text);
		tp.setFontSizeRelative(size);
		return(layer.addTextSprite(tp));
	}
}
