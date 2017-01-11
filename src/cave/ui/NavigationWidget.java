
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

package cave.ui;

public class NavigationWidget extends LayerWidget implements TitleContainerWidget, cave.KeyListener
{
	public static boolean switchToContainer(android.view.View widget, android.view.View newWidget) {
		NavigationWidget ng = null;
		android.view.View pp = cave.ui.Widget.getParent(widget);
		while(pp != null) {
			if(pp instanceof NavigationWidget) {
				ng = (NavigationWidget)pp;
				break;
			}
			pp = cave.ui.Widget.getParent(pp);
		}
		if(ng == null) {
			return(false);
		}
		return(ng.switchWidget(newWidget));
	}

	public static boolean pushToContainer(android.view.View widget, android.view.View newWidget) {
		NavigationWidget ng = null;
		android.view.View pp = cave.ui.Widget.getParent(widget);
		while(pp != null) {
			if(pp instanceof NavigationWidget) {
				ng = (NavigationWidget)pp;
				break;
			}
			pp = cave.ui.Widget.getParent(pp);
		}
		if(ng == null) {
			return(false);
		}
		return(ng.pushWidget(newWidget));
	}

	public static android.view.View popFromContainer(android.view.View widget) {
		NavigationWidget ng = null;
		android.view.View pp = cave.ui.Widget.getParent(widget);
		while(pp != null) {
			if(pp instanceof NavigationWidget) {
				ng = (NavigationWidget)pp;
				break;
			}
			pp = cave.ui.Widget.getParent(pp);
		}
		if(ng == null) {
			return(null);
		}
		return(ng.popWidget());
	}

	public static NavigationWidget findNavigationWidget(android.view.View widget) {
		android.view.View pp = cave.ui.Widget.getParent(widget);
		while(pp != null) {
			if(pp instanceof NavigationWidget) {
				return((NavigationWidget)pp);
			}
			pp = cave.ui.Widget.getParent(pp);
		}
		return(null);
	}

	private SwitcherLayerWidget contentArea = null;
	protected ActionBarWidget actionBar = null;
	private cape.Stack<android.view.View> widgetStack = null;
	private android.view.View dimWidget = null;
	private android.view.View sidebarWidget = null;
	private LayerWidget sidebarSlotLeft = null;
	private boolean sidebarIsFixed = false;
	private boolean sidebarIsDisplayed = false;
	private boolean enableSidebar = true;
	private boolean enableActionBar = true;
	private boolean actionBarIsFloating = false;
	private cave.Color actionBarBackgroundColor = null;
	private cave.Color actionBarTextColor = null;
	private cave.Color backgroundColor = null;
	private java.lang.String backImageResourceName = "backarrow";
	private java.lang.String burgerMenuImageResourceName = "burger";

	public NavigationWidget(cave.GuiApplicationContext ctx) {
		super(ctx);
		widgetStack = new cape.Stack<android.view.View>();
		actionBarBackgroundColor = cave.Color.black();
		actionBarTextColor = cave.Color.white();
	}

	public void onKeyEvent(cave.KeyEvent event) {
		if(event.isKeyPress(cave.KeyEvent.KEY_BACK)) {
			if((widgetStack != null) && (widgetStack.getSize() > 1)) {
				if(popWidget() != null) {
					event.consume();
				}
			}
		}
	}

	public samx.function.Procedure0 getMenuHandler() {
		return(null);
	}

	public java.lang.String getAppIconResource() {
		return(null);
	}

	public samx.function.Procedure0 getAppMenuHandler() {
		return(null);
	}

	public android.view.View createMainWidget() {
		return(null);
	}

	public void updateMenuButton() {
		if(actionBar == null) {
			return;
		}
		samx.function.Procedure0 handler = getMenuHandler();
		if((widgetStack != null) && (widgetStack.getSize() > 1)) {
			actionBar.configureLeftButton(backImageResourceName, new samx.function.Procedure0() {
				public void execute() {
					popWidget();
				}
			});
		}
		else if(enableSidebar == false) {
			actionBar.configureLeftButton(null, null);
		}
		else if(handler == null) {
			if(sidebarIsFixed == false) {
				actionBar.configureLeftButton(burgerMenuImageResourceName, new samx.function.Procedure0() {
					public void execute() {
						displaySidebarWidget();
					}
				});
			}
			else {
				actionBar.configureLeftButton(null, null);
			}
		}
		else {
			actionBar.configureLeftButton(burgerMenuImageResourceName, handler);
		}
	}

	public android.view.View createSidebarWidget() {
		return(null);
	}

	private void enableFixedSidebar() {
		if(((sidebarWidget == null) || (sidebarSlotLeft == null)) || sidebarIsFixed) {
			return;
		}
		hideSidebarWidget(false);
		sidebarIsFixed = true;
		sidebarSlotLeft.addWidget(sidebarWidget);
		updateMenuButton();
	}

	private void disableFixedSidebar() {
		if(sidebarIsDisplayed || (sidebarIsFixed == false)) {
			return;
		}
		cave.ui.Widget.removeFromParent(sidebarWidget);
		sidebarIsFixed = false;
		updateMenuButton();
	}

	private int updateSidebarWidthRequest(int sz) {
		int v = 0;
		if(((sidebarIsFixed == false) && sidebarIsDisplayed) && (sidebarWidget != null)) {
			android.view.View tlayer = cave.ui.Widget.getParent(sidebarWidget);
			LayerWidget layer = null;
			if(tlayer instanceof LayerWidget) {
				layer = (LayerWidget)tlayer;
			}
			if(layer != null) {
				v = (int)(0.80 * sz);
				layer.setWidgetMaximumWidthRequest(v);
			}
		}
		return(v);
	}

	@Override
	public void computeWidgetLayout(int widthConstraint) {
		if(widthConstraint > context.getWidthValue("200mm")) {
			enableFixedSidebar();
		}
		else {
			disableFixedSidebar();
		}
		super.computeWidgetLayout(widthConstraint);
	}

	public void displaySidebarWidget(boolean animated) {
		if((sidebarIsFixed || sidebarIsDisplayed) || (sidebarWidget == null)) {
			return;
		}
		if(dimWidget == null) {
			dimWidget = (android.view.View)cave.ui.CanvasWidget.forColor(context, cave.Color.forRGBADouble(0.00, 0.00, 0.00, 0.80));
		}
		addWidget(dimWidget);
		sidebarIsDisplayed = true;
		final HorizontalBoxWidget box = new HorizontalBoxWidget(context);
		box.addWidget((android.view.View)forWidget(context, sidebarWidget));
		LayerWidget filler = new LayerWidget(context);
		cave.ui.Widget.setWidgetClickHandler((android.view.View)filler, new samx.function.Procedure0() {
			public void execute() {
				hideSidebarWidget();
			}
		});
		box.addWidget((android.view.View)filler, (double)1);
		int sidebarWidthRequest = updateSidebarWidthRequest(cave.ui.Widget.getWidth((android.view.View)this));
		addWidget((android.view.View)box);
		if(animated) {
			cave.ui.Widget.setAlpha((android.view.View)box, 0.00);
			final int sx = -sidebarWidthRequest;
			cave.ui.Widget.move((android.view.View)box, sx, cave.ui.Widget.getY((android.view.View)box));
			cave.ui.Widget.setAlpha(dimWidget, 0.00);
			WidgetAnimation anim = cave.ui.WidgetAnimation.forDuration(context, (long)250);
			anim.addCallback(new samx.function.Procedure1<java.lang.Double>() {
				public void execute(java.lang.Double completion) {
					int dx = (int)(sx + ((0 - sx) * completion));
					if(dx > 0) {
						dx = 0;
					}
					cave.ui.Widget.move((android.view.View)box, dx, cave.ui.Widget.getY((android.view.View)box));
					cave.ui.Widget.setAlpha(dimWidget, completion);
					cave.ui.Widget.setAlpha((android.view.View)box, completion);
				}
			});
			anim.start();
		}
	}

	public void displaySidebarWidget() {
		displaySidebarWidget(true);
	}

	public void hideSidebarWidget(boolean animated) {
		if(sidebarIsDisplayed == false) {
			return;
		}
		sidebarIsDisplayed = false;
		final android.view.View box = cave.ui.Widget.getParent(cave.ui.Widget.getParent(sidebarWidget));
		if(animated) {
			final int fx = -cave.ui.Widget.getWidth(sidebarWidget);
			WidgetAnimation anim = cave.ui.WidgetAnimation.forDuration(context, (long)250);
			anim.addCallback(new samx.function.Procedure1<java.lang.Double>() {
				public void execute(java.lang.Double completion) {
					int dx = (int)(fx * completion);
					cave.ui.Widget.move(box, dx, cave.ui.Widget.getY(box));
					cave.ui.Widget.setAlpha(dimWidget, 1.00 - completion);
				}
			});
			anim.setEndListener(new samx.function.Procedure0() {
				public void execute() {
					cave.ui.Widget.removeFromParent(sidebarWidget);
					cave.ui.Widget.removeFromParent(box);
					cave.ui.Widget.removeFromParent(dimWidget);
				}
			});
			anim.start();
		}
		else {
			cave.ui.Widget.removeFromParent(sidebarWidget);
			cave.ui.Widget.removeFromParent(box);
			cave.ui.Widget.removeFromParent(dimWidget);
		}
	}

	public void hideSidebarWidget() {
		hideSidebarWidget(true);
	}

	public void createBackground() {
		cave.Color bgc = getBackgroundColor();
		if(bgc != null) {
			addWidget((android.view.View)cave.ui.CanvasWidget.forColor(context, bgc));
		}
	}

	@Override
	public void initializeWidget() {
		super.initializeWidget();
		createBackground();
		VerticalBoxWidget mainContainer = cave.ui.VerticalBoxWidget.forContext(context);
		if(enableActionBar) {
			actionBar = new ActionBarWidget(context);
			actionBar.setWidgetBackgroundColor(actionBarBackgroundColor);
			actionBar.setWidgetTextColor(actionBarTextColor);
			java.lang.String appIcon = getAppIconResource();
			if(cape.String.isEmpty(appIcon) == false) {
				actionBar.configureRightButton(appIcon, getAppMenuHandler());
			}
		}
		if((actionBar != null) && (actionBarIsFloating == false)) {
			mainContainer.addWidget((android.view.View)actionBar);
		}
		contentArea = new SwitcherLayerWidget(context);
		if((actionBar != null) && (actionBarIsFloating == true)) {
			LayerWidget ll = new LayerWidget(context);
			ll.addWidget((android.view.View)contentArea);
			ll.addWidget((android.view.View)cave.ui.VerticalBoxWidget.forContext(context).addWidget((android.view.View)actionBar).addWidget((android.view.View)new CustomContainerWidget(context), 1.00));
			mainContainer.addWidget((android.view.View)ll, 1.00);
		}
		else {
			mainContainer.addWidget((android.view.View)contentArea, 1.00);
		}
		HorizontalBoxWidget superMainContainer = cave.ui.HorizontalBoxWidget.forContext(context);
		sidebarSlotLeft = new LayerWidget(context);
		superMainContainer.addWidget((android.view.View)sidebarSlotLeft);
		superMainContainer.addWidget((android.view.View)mainContainer, (double)1);
		addWidget((android.view.View)superMainContainer);
		sidebarWidget = createSidebarWidget();
		updateMenuButton();
		android.view.View main = createMainWidget();
		if(main != null) {
			pushWidget(main);
		}
	}

	public void updateWidgetTitle(java.lang.String title) {
		if(actionBar != null) {
			actionBar.setWidgetTitle(title);
		}
	}

	private void onCurrentWidgetChanged() {
		if(contentArea == null) {
			return;
		}
		android.view.View widget = null;
		java.util.ArrayList<android.view.View> widgets = cave.ui.Widget.getChildren((android.view.View)contentArea);
		if((widgets != null) && (cape.Vector.getSize(widgets) > 0)) {
			widget = cape.Vector.get(widgets, cape.Vector.getSize(widgets) - 1);
		}
		if((widget != null) && (widget instanceof DisplayAwareWidget)) {
			((DisplayAwareWidget)widget).onWidgetDisplayed();
		}
		if((widget != null) && (widget instanceof TitledWidget)) {
			updateWidgetTitle(((TitledWidget)widget).getWidgetTitle());
		}
		else {
			updateWidgetTitle("");
		}
		updateMenuButton();
	}

	public boolean pushWidget(android.view.View widget) {
		if((contentArea == null) || (widget == null)) {
			return(false);
		}
		widgetStack.push(widget);
		contentArea.addWidget(widget);
		onCurrentWidgetChanged();
		return(true);
	}

	public boolean switchWidget(android.view.View widget) {
		if(widget == null) {
			return(false);
		}
		popWidget();
		return(pushWidget(widget));
	}

	public android.view.View popWidget() {
		if(contentArea == null) {
			return(null);
		}
		android.view.View topmost = widgetStack.pop();
		if(topmost == null) {
			return(null);
		}
		cave.ui.Widget.removeFromParent(topmost);
		onCurrentWidgetChanged();
		return(topmost);
	}

	public boolean getEnableSidebar() {
		return(enableSidebar);
	}

	public NavigationWidget setEnableSidebar(boolean v) {
		enableSidebar = v;
		return(this);
	}

	public boolean getEnableActionBar() {
		return(enableActionBar);
	}

	public NavigationWidget setEnableActionBar(boolean v) {
		enableActionBar = v;
		return(this);
	}

	public boolean getActionBarIsFloating() {
		return(actionBarIsFloating);
	}

	public NavigationWidget setActionBarIsFloating(boolean v) {
		actionBarIsFloating = v;
		return(this);
	}

	public cave.Color getActionBarBackgroundColor() {
		return(actionBarBackgroundColor);
	}

	public NavigationWidget setActionBarBackgroundColor(cave.Color v) {
		actionBarBackgroundColor = v;
		return(this);
	}

	public cave.Color getActionBarTextColor() {
		return(actionBarTextColor);
	}

	public NavigationWidget setActionBarTextColor(cave.Color v) {
		actionBarTextColor = v;
		return(this);
	}

	public cave.Color getBackgroundColor() {
		return(backgroundColor);
	}

	public NavigationWidget setBackgroundColor(cave.Color v) {
		backgroundColor = v;
		return(this);
	}

	public java.lang.String getBackImageResourceName() {
		return(backImageResourceName);
	}

	public NavigationWidget setBackImageResourceName(java.lang.String v) {
		backImageResourceName = v;
		return(this);
	}

	public java.lang.String getBurgerMenuImageResourceName() {
		return(burgerMenuImageResourceName);
	}

	public NavigationWidget setBurgerMenuImageResourceName(java.lang.String v) {
		burgerMenuImageResourceName = v;
		return(this);
	}
}
