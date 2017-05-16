class WindowManager
{
	constructor()
	{
		this.dom = $("<window-manager></window-manager>");
		this.panel = new Panel();
		this.desktop = $("<desktop></desktop>")
		//MergeDoms
		this.dom.append(this.panel.dom);
		this.dom.append(this.desktop);
		
		//Create metadata container
		this.windowList = {};
		this.windowList.insertIndex = 0;
		this.focusID = -1;
	}
	
	/*Arguments
	 * height : number
	 * width : number
	 * title : string
	 * closeable : boolean
	 * className : string
	 * id : number | optional
	*/
	
	newWindow(args)
	{
		var thisObject = this;
		
		//Create metadata
		if(typeof(args.id) == "undefined")
		{
			args.id = this.windowList.insertIndex;
			this.windowList.insertIndex++;
		}
		
		//Create doms
		args.containedDom = this.desktop;
		var window = new Window(args);
		var buttonArguments = 
		{
			label : args.title,
			id : args.id,
			className : args.className
		}
		var panelButton = this.panel.createButton(buttonArguments);
		
		//Merge Doms
		this.desktop.append(window.dom);
		
		//Store metadata
		this.windowList[args.id] = {
				window : window,
				panelButton : panelButton
		};
		
		
		//Add events
		window.onCloseButton(function(window)
		{
			console.log("HI HI!" + window.title.text());
		})
		window.onFrameClick(function(window)
		{
			thisObject.focus(window.id);
		})
		console.log(panelButton);
		panelButton.onClick(function(buttonObject)
		{
			thisObject.focus(buttonObject.id)
		})
		
		return window;
	}
	
	focus(id)
	{
		if(this.focusID == id)
		{
			return;
		}
		
		var windowData = this.windowList[id];
		
		this.desktop.append(windowData.window.dom);
		
		windowData.window.dom.toggleClass("focus");
		windowData.panelButton.dom.toggleClass("focus");
		
		if(this.focusID != -1)
		{
			var previousWindow = this.windowList[this.focusID];
			previousWindow.window.dom.toggleClass("focus");
			previousWindow.panelButton.dom.toggleClass("focus");
		}
		
		this.focusID = id;
	}
	
}