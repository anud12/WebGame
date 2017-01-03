var windowManager = 
{
	initialize : function(desktop, panel)
	{
		windowManager.desktop = desktop;
		windowManager.panel = panel;
	},

		
	newWindow : function (arg)
	{
		
	    //Create doms
	    var dom = $("<window-frame></window-frame>");
	    var title = $("<window-title></window-title>");
	    var windowControls = $("<window-controls></window-controls>");
	    
	    var minimize = $("<button>_</button>");
	    var close = $("<button>X</button>");
	    
	    var windowDom = $("<window></window>");
	    var menuBar = $("<menu-bar></menu-bar>");
	    var contentContainer = $("<content-container></content-container>");
	    var content = $("<content></content>");
	    var sideButton = $("<button></button>");
	    
	    //Create return object
	    var returnWindow = {};
	    returnWindow.dom = dom;
	    returnWindow.title = title;
	    returnWindow.windowControls = windowControls;
	    returnWindow.minimize = minimize;
	    returnWindow.windowDom = windowDom;
	    returnWindow.menuBar = menuBar;
	    returnWindow.contentContainer = contentContainer;
	    returnWindow.content = content;
	    returnWindow.sideButton = sideButton;
	    returnWindow.close = close;
	    
	    //Insert doms
	    dom.append(title);
	    dom.append(windowControls);
	    dom.append(windowDom);
	    windowDom.append(menuBar);
	    contentContainer.append(content);
	    windowDom.append(contentContainer);
	    
	    windowControls.append(minimize);
	    
	    if(arg.closeable)
	    	windowControls.append(close);
	    
	    windowManager.desktop.append(dom);
	    windowManager.panel.append(sideButton);
	    
	    //Configure doms events
	    $(dom).draggable( {
	    	containment: "desktop",
	    	scroll: false,
	    	handle:title,
	    });
	    
	    $(dom).resizable( {
	    	containment: "desktop",
	    	handles: " n, e, s, w, ne, se, sw, nw",
	    	resize : function(event, ui)
	    	{
	    		if(ui.size.width < "250")
    			{
	    			dom.addClass("mobile");
    			}
	    		else
	    		{
	    			dom.removeClass("mobile");
	    		}
	    	}
	    });
	    if(typeof(arg.width) != "undefined")
	    	$(dom).width(arg.width);
	    else
	    	$(dom).width(200);
	    
	    if(typeof(arg.height) != "undefined")
	    	$(dom).height(arg.height);
	    else
	    	$(dom).height(100);
	    
	    //Add classes
	    title.text(arg.windowName);
	    sideButton.text(arg.windowName);
	    $(dom).addClass("unfocus");
	    if(typeof(arg.className) != "undefined")
	    	$(dom).addClass(arg.className);
	    
	    sideButton.addClass("visible");
	    sideButton.addClass(arg.className);
	    
	    $(title).click(function(event)
	    {
	    	windowManager.focus(dom,sideButton,event);
	    })
	    $(dom).click(function(event)
		{
	    	windowManager.focus(dom,sideButton,event);
		});
	    minimize.click(function()
	    {
	    	windowManager.hideWindow(dom,sideButton);
	    });
	    close.click(function()
	    {
	    	windowManager.closeWindow(dom,sideButton);
	    });
	    sideButton.click(function()
	    {
	    	windowManager.showWindow(dom,sideButton);
	    });
	    
	    return returnWindow;
	},
	focus : function (dom,button,event)
	{
		
		if(!dom.hasClass("focus"))
		{
			//Save states
			var scrollLocation = $(dom).find("content-container").scrollTop();
			
			//Modify dom
			$("window-frame.focus").addClass("unfocus");
			$("window-frame.focus").removeClass("focus");
			$(dom).addClass("focus");
			$(dom).removeClass("unfocus");
			
			$("panel > .focus").removeClass("focus");
			$(button).addClass("focus");
			$(dom).appendTo("desktop");
			
			//Apply saved states
			$(dom).find("content-container").scrollTop(scrollLocation);
		}
	},

	showWindow : function (domIdentifier, button)
	{
	    $(domIdentifier).show();
	    $(button).addClass("visible");
	    windowManager.focus(domIdentifier,button);
	},
	hideWindow : function (domIdentifier, button)
	{
	    $(domIdentifier).hide();
	    $(button).removeClass("visible");
	    button.removeClass("focus");
	},
	closeWindow : function (domIdentifier, button)
	{
		$(domIdentifier).remove();
		$(button).remove();
	}
}
