class Panel
{
	constructor()
	{
		//CreateDoms
		this.dom = $("<panel-container></panel-container>");
		this.panel = $("<panel></panel");
		
		this.rightPanel = $("<rightPanel></rightPanel>");
		
		//MergeDoms
		this.dom.append(this.panel);
		this.dom.append(this.rightPanel);
		
		this.rightPanel.append($("<button>TEST</button>"));

		//Create metadata containers
		this.buttonList = {};
	}

	/* Arguments 
	* label
	* id
	* className
	*/

	
	createButton(args)
	{
		var dom = $("<button></button>");
		dom.text(args.label);
		
		var ButtonObject = new PanelButton(args.id,dom);
		
		ButtonObject.dom.toggleClass(args.className)
		
		ButtonObject.id = args.id;
		ButtonObject.dom = dom;
		
		this.panel.append(dom);
		
		this.buttonList[args.id] = ButtonObject;
		
		console.log(this.buttonList);
		return ButtonObject;
	}
	getButton(id)
	{
		return this.buttonList[args.id];
	}
	
}