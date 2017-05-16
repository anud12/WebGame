class Window
{
	onResize()
	{
		if(this.dom.width() < "250")
		{
			this.dom.addClass("mobile");
		}
		else
		{
			this.dom.removeClass("mobile");
		}
	}
	closeButton()
	{
		var eventList = this.eventHandlerLists.onCloseButton;
		for(var functionName in eventList)
		{
			var funct = eventList[functionName];
			funct(this);
		}
	}
	onCloseButton(funct)
	{
		this.eventHandlerLists.onCloseButton.push(funct);
	}
	frameClick()
	{
		var eventList = this.eventHandlerLists.onFrameClick;
		for(var functionName in eventList)
		{
			var funct = eventList[functionName];
			funct(this);
		}
	}
	onFrameClick(funct)
	{
		this.eventHandlerLists.onFrameClick.push(funct);
	}
	focus()
	{
		
	}
	
	setWindowManager(windowManager)
	{
		this.windowManager = windowManager;
	}
	createMetadata()
	{
		this.eventHandlerLists = {};
		var eventList = this.eventHandlerLists;
		eventList.onCloseButton = [];
		eventList.onFrameClick = [];
	}
	/*Arguments
	 * height
	 * width
	 * title
	 * closeable
	 * containedDom
	 * className
	 * id
	*/
	constructor(args)
	{
		//Creating metadata
		this.createMetadata();
		//Add metadata
		this.id = args.id;
		var thisObject = this;
		
		//Create doms
		this.dom = $("<window-frame></window-frame>");
		this.title = $("<window-title></window-title>");
		this.windowControls = $("<window-controls></window-controls>");
		
		this.minimize = $("<button>_</button>");
		this.close = $("<button>X</button>");
		
		this.windowDom = $("<window></window>");
		this.menuBar = $("<menu-bar></menu-bar>");
		this.contentContainer = $("<content-container></content-container>");
		this.content = $("<content></content>");
		this.sideButton = $("<button></button>");
		
		//Merge doms
		
		this.dom.append(this.title);
		this.dom.append(this.windowControls);
		this.dom.append(this.windowDom);
		
		this.windowDom.append(this.menuBar);
		this.contentContainer.append(this.content);
		this.windowDom.append(this.contentContainer);
		
		this.windowControls.append(this.minimize);
		
		if(args.closeable)
			this.windowControls.append(this.close);
		
		//Add Events
		this.dom.draggable ({
	    	containment: args.containedDom,
	    	scroll: false,
	    	handle: this.title,
	    });

		this.dom.resizable({
	    	containment: args.containedDom,
	    	handles: " n, e, s, w, ne, se, sw, nw"
		})
		this.dom.css("position", "");
		this.dom.click(function()
		{
			thisObject.frameClick();
		})
		this.close.click(function()
		{
			thisObject.closeButton();
		});
		this.dom.resize(function()
		{
			thisObject.onResize();
		});
		
		//Remove Z-index from ui-resizable-handle
		this.dom.find(".ui-resizable-handle").css({"z-index" : ""}) 
		
		if(typeof(args.width) !="undefined")
		{
			this.dom.width(args.width);
		}
		else
		{
			this.dom.width(200);
		}
		if(typeof(args.height) !="undefined")
		{
			this.dom.height(args.height);
		}
		else
		{
			this.dom.height(100);
		}
		
		//Add classes
		this.title.text(args.title);
		if(typeof(args.className) !="undefined")
		{	
			this.sideButton.addClass(args.className);
			this.dom.addClass(args.className);
		}
		
		this.sideButton.addClass("visible");
		this.sideButton.addClass(args.className);
	}
}