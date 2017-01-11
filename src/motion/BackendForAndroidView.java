
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

public class BackendForAndroidView implements Backend
{
	private static class ViewSprite implements Sprite
	{
		protected android.view.View view = null;
		private double widthFactor = 0.00;
		private double heightFactor = 0.00;
		protected double myx = -99999.90;
		protected double myy = -99999.90;
		protected double mywidth = 0.00;
		protected double myheight = 0.00;
		protected double myAngle = 0.00;
		protected double myAlpha = 1.00;

		public android.view.View getView() {
			return(view);
		}

		public android.view.ViewGroup getViewGroup() {
			return((android.view.ViewGroup)((view instanceof android.view.ViewGroup) ? view : null));
		}

		public ViewSprite setView(android.view.View value) {
			view = value;
			return(this);
		}

		public void setBackgroundColor(cave.Color color) {
			if(color != null) {
				view.setBackgroundColor(color.toARGBInt32());
			}
			else {
				view.setBackgroundColor(0);
			}
		}

		public void move(double x, double y) {
			if(x != myx) {
				view.setX((float)(x * widthFactor));
				myx = x;
			}
			if(y != myy) {
				view.setY((float)(y * heightFactor));
				myy = y;
			}
		}

		public void updateSizeAsPixels(int wpx, int hpx) {
			mywidth = (double)(wpx / widthFactor);
			myheight = (double)(hpx / heightFactor);
		}

		public void resize(double w, double h) {
			if((w == mywidth) && (h == myheight)) {
				return;
			}
			int wpx = (int)(w * widthFactor);
			int hpx = (int)(h * heightFactor);
			int x = (int)view.getX();
			int y = (int)view.getY();
			view.layout(x, y, x+wpx, y+hpx);
			mywidth = w;
			myheight = h;
		}

		public void setRotation(double angle) {
			if(myAngle == angle) {
				return;
			}
			double a = (angle * 180.00) / cape.Math.M_PI;
			view.setRotation((float)a);
			myAngle = angle;
		}

		public void setAlpha(double alpha) {
			double f = alpha;
			if(f < 0) {
				f = (double)0;
			}
			if(f > 1) {
				f = (double)1;
			}
			if(f == myAlpha) {
				return;
			}
			view.setAlpha((float)f);
			myAlpha = f;
		}

		public void scale(double scalex, double scaley) {
			view.setScaleX((float)scalex);
			view.setScaleY((float)scaley);
		}

		public double getX() {
			return(myx);
		}

		public double getY() {
			return(myy);
		}

		public double getWidth() {
			return(mywidth);
		}

		public double getHeight() {
			return(myheight);
		}

		public double getRotation() {
			return(myAngle);
		}

		public double getAlpha() {
			return(myAlpha);
		}

		public double getScaleX() {
			return((double)view.getScaleX());
		}

		public double getScaleY() {
			return((double)view.getScaleY());
		}

		public void removeFromContainer() {
			if(view == null) {
				return;
			}
			android.view.ViewParent pp = view.getParent();
			if(pp == null) {
				return;
			}
			android.view.ViewGroup parent = (android.view.ViewGroup)((pp instanceof android.view.ViewGroup) ? pp : null);
			if(parent == null) {
				return;
			}
			parent.removeView(view);
		}

		public double getWidthFactor() {
			return(widthFactor);
		}

		public ViewSprite setWidthFactor(double v) {
			widthFactor = v;
			return(this);
		}

		public double getHeightFactor() {
			return(heightFactor);
		}

		public ViewSprite setHeightFactor(double v) {
			heightFactor = v;
			return(this);
		}
	}

	private static class MyTextureSprite extends ViewSprite implements TextureSprite
	{
		private android.content.Context context = null;
		private MySpriteLayer layer = null;

		public void setTexture(Texture texture) {
			setTextureWithSize(texture, getWidth(), getHeight());
		}

		public void setTextureWithSize(Texture texture, double width, double height) {
			if((texture == null) || ((width <= 0.00) && (height <= 0.00))) {
				return;
			}
			double w = width;
			double h = height;
			ImageTexture imgt = (ImageTexture)((texture instanceof ImageTexture) ? texture : null);
			if(imgt != null) {
				cave.Image image = imgt.getImage();
				if(image != null) {
					if(w <= 0.00) {
						w = (double)((image.getPixelWidth() * h) / image.getPixelHeight());
					}
					if(h <= 0.00) {
						h = (double)((image.getPixelHeight() * w) / image.getPixelWidth());
					}
				}
			}
			if(w <= 0.00) {
				w = h;
			}
			if(h <= 0.00) {
				h = w;
			}
			if((w <= 0.00) || (h <= 0.00)) {
				return;
			}
			if(texture instanceof ColorTexture) {
				android.view.View view = getView();
				if(view == null) {
					view = new android.view.View(context);
					setView(view);
				}
				resize(w, h);
				setBackgroundColor(((ColorTexture)texture).getColor());
				return;
			}
			if(texture instanceof ImageTexture) {
				cave.Image timg = ((ImageTexture)texture).getImage();
				cave.ImageForAndroid img = null;
				if(timg instanceof cave.ImageForAndroid) {
					img = (cave.ImageForAndroid)timg;
				}
				if(img == null) {
					return;
				}
				android.graphics.Bitmap bm = img.getAndroidBitmap();
				if(bm == null) {
					return;
				}
				android.view.View tview = getView();
				android.widget.ImageView view = null;
				if(tview instanceof android.widget.ImageView) {
					view = (android.widget.ImageView)tview;
				}
				if(view == null) {
					view = new android.widget.ImageView(context);
					setView(view);
				}
				view.setScaleType(android.widget.ImageView.ScaleType.FIT_XY);
				resize(w, h);
				view.setImageBitmap(bm);
				return;
			}
			System.out.println("[motion.BackendForAndroidView.MyTextureSprite.setTextureWithSize] (BackendForAndroidView@target_android.sling:256:2): Unknown texture type");
		}

		public android.content.Context getContext() {
			return(context);
		}

		public MyTextureSprite setContext(android.content.Context v) {
			context = v;
			return(this);
		}

		public MySpriteLayer getLayer() {
			return(layer);
		}

		public MyTextureSprite setLayer(MySpriteLayer v) {
			layer = v;
			return(this);
		}
	}

	private static class TextViewSprite extends ViewSprite implements TextSprite
	{
		private cape.AndroidApplicationContext context = null;

		public TextViewSprite(cape.AndroidApplicationContext androidContext) {
			this.context = androidContext;
		}

		public boolean initialize(android.content.Context context, TextProperties text) {
			android.widget.TextView vv = new android.widget.TextView(context);
			setView(vv);
			setText(text);
			return(true);
		}

		public void setText(TextProperties desc) {
			android.view.View tview = getView();
			android.widget.TextView view = null;
			if(tview instanceof android.widget.TextView) {
				view = (android.widget.TextView)tview;
			}
			if(view == null) {
				return;
			}
			if(desc == null) {
				view.setText("");
				return;
			}
			cave.Color textColor = desc.getTextColor();
			if(textColor != null) {
				view.setTextColor(textColor.toARGBInt32());
			}
			java.lang.String fontFamily = desc.getFontFamily();
			java.lang.String fontResource = desc.getFontResource();
			cape.File fontFile = desc.getFontFile();
			int fontStyle = 0;
			fontStyle = android.graphics.Typeface.NORMAL;
			if(desc.getFontIsItalic() && desc.getFontIsBold()) {
				fontStyle = android.graphics.Typeface.BOLD_ITALIC;
			}
			else if(desc.getFontIsItalic()) {
				fontStyle = android.graphics.Typeface.ITALIC;
			}
			if(desc.getFontIsBold()) {
				fontStyle = android.graphics.Typeface.BOLD;
			}
			if(cape.String.isEmpty(fontFamily) == false) {
				view.setTypeface(android.graphics.Typeface.create(fontFamily, fontStyle), fontStyle);
			}
			else if(cape.String.isEmpty(fontResource) == false) {
				android.content.Context ctx = view.getContext();
				if(ctx != null) {
					view.setTypeface(android.graphics.Typeface.createFromAsset(ctx.getAssets(), fontResource), fontStyle);
				}
			}
			else if(fontFile != null) {
				view.setTypeface(android.graphics.Typeface.createFromFile(fontFile.getPath()), fontStyle);
			}
			setBackgroundColor(desc.getBackgroundColor());
			view.setText(desc.getText());
			int msw = 0;
			int msh = 0;
			double hsz = 0.00;
			java.lang.String fsDesc = desc.getFontSizeDescription();
			if(cape.String.isEmpty(fsDesc) == false) {
				int density = 0;
				cave.GuiApplicationContext gc = (cave.GuiApplicationContext)((context instanceof cave.GuiApplicationContext) ? context : null);
				if(gc != null) {
					density = gc.getScreenDensity();
				}
				else {
					density = 96;
				}
				hsz = (double)cave.Length.asPoints(fsDesc, density);
			}
			else {
				double fsRelative = desc.getFontSizeRelative();
				double fsAbsolute = desc.getFontSizeAbsolute();
				if(fsRelative > 0.00) {
					hsz = getHeightFactor() * fsRelative;
				}
				else {
					hsz = fsAbsolute * 18;
				}
			}
			view.setTextSize((float)hsz);
			view.measure(android.view.View.MeasureSpec.UNSPECIFIED, android.view.View.MeasureSpec.UNSPECIFIED);
			msw = view.getMeasuredWidth();
			msh = view.getMeasuredHeight();
			view.layout(0, 0, msw, msh);
			updateSizeAsPixels(msw, msh);
		}
	}

	private static class MyViewGroup extends android.view.ViewGroup
	{
		public MyViewGroup(android.content.Context context) {
			super(context);
		}

		public void onLayout(boolean changed, int left, int top, int right, int bottom) {
			int count = getChildCount();
			for (int i = 0; i < count; i++) {
				android.view.View child = getChildAt(i);
				if(child.getVisibility() != GONE) {
					child.layout((int)child.getX(), (int)child.getY(), (int)child.getWidth(), (int)child.getHeight());
				}
			}
		}

		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
		}
	}

	private static class MySpriteLayer extends ViewSprite implements ContainerSprite, SpriteLayer
	{
		private android.view.View parentView = null;
		private double referenceWidth = 1.00;
		private double referenceHeight = 0.00;
		private double layerWidthFactor = 0.00;
		private double layerHeightFactor = 0.00;
		private cape.AndroidApplicationContext context = null;
		private int ppi = -1;

		public MySpriteLayer(cape.AndroidApplicationContext androidContext, android.content.Context context) {
			this.context = androidContext;
			view = (android.view.View)new MyViewGroup(context);
		}

		public double getLayerWidthFactor() {
			if(((layerWidthFactor == 0.00) && (referenceWidth > 0.00)) && (parentView != null)) {
				double rwidth = (double)parentView.getWidth();
				layerWidthFactor = rwidth / referenceWidth;
			}
			return(layerWidthFactor);
		}

		public double getLayerHeightFactor() {
			if(layerHeightFactor == 0.00) {
				double rh = getReferenceHeight();
				if((rh > 0.00) && (parentView != null)) {
					double rheight = (double)parentView.getHeight();
					layerHeightFactor = rheight / rh;
				}
			}
			return(layerHeightFactor);
		}

		public TextureSprite addTextureSpriteForSize(Texture texture, double width, double height) {
			MyTextureSprite v = new MyTextureSprite();
			v.setWidthFactor(getLayerWidthFactor());
			v.setHeightFactor(getLayerHeightFactor());
			v.setContext(view.getContext());
			v.setLayer(this);
			v.setTextureWithSize(texture, width, height);
			android.view.View vv = v.getView();
			if(vv == null) {
				return(null);
			}
			android.view.ViewGroup vg = (android.view.ViewGroup)((view instanceof android.view.ViewGroup) ? view : null);
			if(vg != null) {
				vg.addView(vv);
			}
			return((TextureSprite)v);
		}

		public TextSprite addTextSprite(TextProperties text) {
			if(view == null) {
				return(null);
			}
			TextViewSprite v = new TextViewSprite(context);
			v.setWidthFactor(getLayerWidthFactor());
			v.setHeightFactor(getLayerHeightFactor());
			if(v.initialize(view.getContext(), text) == false) {
				return(null);
			}
			android.view.View vv = v.getView();
			if(vv == null) {
				return(null);
			}
			android.view.ViewGroup vg = (android.view.ViewGroup)((view instanceof android.view.ViewGroup) ? view : null);
			if(vg != null) {
				vg.addView(vv);
			}
			return((TextSprite)v);
		}

		public ContainerSprite addContainerSprite(double width, double height) {
			if(view == null) {
				return(null);
			}
			MySpriteLayer v = new MySpriteLayer(context, view.getContext());
			v.setParentView(view);
			android.view.View vv = v.getView();
			if(vv == null) {
				return(null);
			}
			android.view.ViewGroup vg = (android.view.ViewGroup)((view instanceof android.view.ViewGroup) ? view : null);
			if(vg != null) {
				vg.addView(vv);
			}
			return((ContainerSprite)v);
		}

		public void removeSprite(Sprite sprite) {
			ViewSprite vs = (ViewSprite)((sprite instanceof ViewSprite) ? sprite : null);
			if(vs == null) {
				return;
			}
			vs.removeFromContainer();
		}

		public void removeAllSprites() {
			android.view.ViewGroup vg = getViewGroup();
			if(vg != null) {
				vg.removeAllViews();
			}
		}

		public void setReferenceWidth(double rw) {
			referenceWidth = rw;
		}

		public void setReferenceHeight(double rh) {
			referenceHeight = rh;
		}

		public double getReferenceWidth() {
			return(referenceWidth);
		}

		public double getReferenceHeight() {
			if(referenceHeight > 0.00) {
				return(referenceHeight);
			}
			return((((double)parentView.getHeight()) * referenceWidth) / ((double)parentView.getWidth()));
		}

		private int getPPI() {
			if(ppi < 0) {
				cave.GuiApplicationContext gc = (cave.GuiApplicationContext)((context instanceof cave.GuiApplicationContext) ? context : null);
				if(gc != null) {
					ppi = gc.getScreenDensity();
				}
				if(ppi < 0) {
					ppi = 96;
				}
			}
			return(ppi);
		}

		public double getHeightValue(java.lang.String spec) {
			return((double)(cave.Length.asPoints(spec, getPPI()) / getLayerHeightFactor()));
		}

		public double getWidthValue(java.lang.String spec) {
			return((double)(cave.Length.asPoints(spec, getPPI()) / getLayerWidthFactor()));
		}

		public android.view.View getParentView() {
			return(parentView);
		}

		public MySpriteLayer setParentView(android.view.View v) {
			parentView = v;
			return(this);
		}
	}

	private static class ColorTexture implements Texture
	{
		private cave.Color color = null;

		public cave.Color getColor() {
			return(color);
		}

		public ColorTexture setColor(cave.Color v) {
			color = v;
			return(this);
		}
	}

	private static class ImageTexture implements Texture
	{
		private cave.Image image = null;

		public cave.Image getImage() {
			return(image);
		}

		public ImageTexture setImage(cave.Image v) {
			image = v;
			return(this);
		}
	}

	private static class MyLayeredContainerView extends android.view.ViewGroup
	{
		private BackendForAndroidView backend = null;
		private cave.PointerEvent pointerEvent = new cave.PointerEvent();
		private cave.KeyEvent keyEvent = new cave.KeyEvent();

		public MyLayeredContainerView(android.content.Context context) {
			super(context);
		}

		public void onLayout(boolean changed, int left, int top, int right, int bottom) {
			int count = getChildCount();
			for (int i = 0; i < count; i++) {
				android.view.View child = getChildAt(i);
				if(child.getVisibility() != GONE) {
					child.layout(0, 0, getWidth(), getHeight());
				}
			}
		}

		public void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
			backend.onSizeChanged(w, h);
		}

		public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
			System.out.println("KEY DOWN: " + cape.String.forInteger(keyCode));
			return(true);
		}

		public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
			System.out.println("KEY UP: " + cape.String.forInteger(keyCode));
			return(true);
		}

		public boolean onTouchEvent(android.view.MotionEvent event) {
			int viewWidth = getWidth();
			int viewHeight = getHeight();
			if(viewWidth < 1 || viewHeight < 1) {
				return(false);
			}
			int androidAction = event.getAction();
			int action;
			int hsz = event.getHistorySize();
			int pcount = event.getPointerCount();
			if(androidAction == android.view.MotionEvent.ACTION_DOWN || androidAction == android.view.MotionEvent.ACTION_POINTER_DOWN) {
				action = cave.PointerEvent.DOWN;
			}
			else if(androidAction == android.view.MotionEvent.ACTION_UP || androidAction == android.view.MotionEvent.ACTION_POINTER_UP) {
				action = cave.PointerEvent.UP;
			}
			else if(androidAction == android.view.MotionEvent.ACTION_MOVE) {
				action = cave.PointerEvent.MOVE;
			}
			else if(androidAction == android.view.MotionEvent.ACTION_CANCEL) {
				action = cave.PointerEvent.CANCEL;
			}
			else {
				return(false);
			}
			pointerEvent.isConsumed = false;
			pointerEvent.action = action;
			for(int h = 0; h<hsz; h++) {
				for (int p = 0; p<pcount; p++) {
					pointerEvent.pointerId = p;
					pointerEvent.x = (double)event.getHistoricalX(p, h) / (double)viewWidth;
					pointerEvent.y = (double)event.getHistoricalY(p, h) / (double)viewHeight;
					backend.onPointerEvent(pointerEvent);
				}
			}
			for(int p = 0; p < pcount; p++) {
				pointerEvent.pointerId = p;
				pointerEvent.x = (double)event.getX(p) / (double)viewWidth;
				pointerEvent.y = (double)event.getY(p) / (double)viewHeight;
				backend.onPointerEvent(pointerEvent);
			}
			return(true);
		}

		public BackendForAndroidView getBackend() {
			return(backend);
		}

		public MyLayeredContainerView setBackend(BackendForAndroidView v) {
			backend = v;
			return(this);
		}
	}

	public static BackendForAndroidView forScene(Scene scene, cape.AndroidApplicationContext context) {
		BackendForAndroidView v = new BackendForAndroidView(context);
		if(scene != null) {
			v.setScene(scene);
			scene.setBackend((Backend)v);
		}
		return(v);
	}

	private android.view.ViewGroup androidView = null;
	private boolean isInitialized = false;
	private boolean isStarted = false;
	private android.animation.TimeAnimator animator = null;
	private cape.TimeValue gameTime = null;
	private SceneManager sceneManager = null;
	private cape.AndroidApplicationContext androidContext = null;

	public BackendForAndroidView(cape.AndroidApplicationContext context) {
		this.androidContext = context;
		sceneManager = new SceneManager();
		sceneManager.setContext((cave.GuiApplicationContext)((context instanceof cave.GuiApplicationContext) ? context : null));
		sceneManager.setBackend((Backend)this);
		MyLayeredContainerView vv = new MyLayeredContainerView(context.getAndroidActivityContext());
		vv.setBackend(this);
		androidView = (android.view.ViewGroup)vv;
	}

	public void setScene(Scene scene) {
		sceneManager.pushScene(scene);
	}

	public void onKeyEvent(cave.KeyEvent event) {
		Scene scene = sceneManager.getCurrentScene();
		if(scene != null) {
			scene.onKeyEvent(event);
		}
	}

	public void onPointerEvent(cave.PointerEvent event) {
		Scene scene = sceneManager.getCurrentScene();
		if(scene != null) {
			scene.onPointerEvent(event);
		}
	}

	public android.view.ViewGroup getAndroidView() {
		return(androidView);
	}

	public void onSizeChanged(int w, int h) {
		if(((isInitialized == false) && (w > 0)) && (h > 0)) {
			isInitialized = true;
			sceneManager.initialize();
			if(isStarted) {
				doStart();
			}
		}
	}

	public SpriteLayer createSpriteLayer() {
		if(androidView == null) {
			return(null);
		}
		MySpriteLayer v = new MySpriteLayer(androidContext, androidView.getContext());
		v.setParentView(androidView);
		android.view.View vv = v.getView();
		if(vv == null) {
			return(null);
		}
		androidView.addView(vv);
		vv.layout(0, 0, androidView.getWidth(), androidView.getHeight());
		return((SpriteLayer)v);
	}

	public cave.Image createImageFromResource(java.lang.String name) {
		if(androidView == null) {
			return(null);
		}
		return((cave.Image)cave.ImageForAndroid.forResource(name, androidView.getContext()));
	}

	public Texture createTextureForImage(cave.Image image) {
		return((Texture)new ImageTexture().setImage(image));
	}

	public Texture createTextureForColor(cave.Color color) {
		return((Texture)new ColorTexture().setColor(color));
	}

	public void deleteSpriteLayer(SpriteLayer layer) {
		if(androidView == null) {
			return;
		}
		MySpriteLayer ll = (MySpriteLayer)((layer instanceof MySpriteLayer) ? layer : null);
		if(ll == null) {
			return;
		}
		androidView.removeView(ll.getView());
	}

	public void deleteTexture(Texture texture) {
	}

	public void deleteAllTextures() {
	}

	private static class MyAnimatorListener implements android.animation.TimeAnimator.TimeListener
	{
		private BackendForAndroidView listener = null;

		public void onTimeUpdate(android.animation.TimeAnimator animation, long totalTime, long deltaTime) {
			listener.onTick(totalTime, deltaTime);
		}

		public BackendForAndroidView getListener() {
			return(listener);
		}

		public MyAnimatorListener setListener(BackendForAndroidView v) {
			listener = v;
			return(this);
		}
	}

	public void onTick(long totalTime, long deltaTime) {
		if(gameTime == null) {
			gameTime = new cape.TimeValue();
		}
		gameTime.setMilliSeconds(totalTime);
		Scene scene = sceneManager.getCurrentScene();
		if(scene != null) {
			scene.tick(gameTime, (double)(deltaTime / 1000.00));
		}
	}

	private void doStart() {
		sceneManager.start();
		if(animator == null) {
			animator = new android.animation.TimeAnimator();
			animator.setTimeListener(new MyAnimatorListener().setListener(this));
		}
		if(animator.isStarted() == false) {
			animator.start();
		}
		else if(animator.isPaused()) {
			try {
				animator.resume();
			}
			catch(java.lang.NoSuchMethodError e) {
				animator.start();
			}
		}
	}

	public void onStart() {
		isStarted = true;
		if(isInitialized) {
			doStart();
		}
	}

	public void onStop() {
		if(animator != null) {
			try {
				animator.pause();
			}
			catch(java.lang.NoSuchMethodError e) {
				animator.end();
			}
		}
		sceneManager.stop();
		isStarted = false;
	}

	public void onDestroy() {
		if(animator != null) {
			animator.end();
			animator = null;
		}
		sceneManager.cleanup();
		isInitialized = false;
	}
}
