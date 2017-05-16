class PanelButton
{
	/*Arguments 
	 * 
	*/
	constructor(id, dom)
	{
		//Store metadata
		this.id = id;
		this.dom = dom;
		
		this.eventHandlerLists = {};
		this.eventHandlerLists.onClick = [];
		
		var thisObject = this;
		this.dom.click(function()
		{
			thisObject.click();
		});
	}
	onClick(funct)
	{
		this.eventHandlerLists.onClick.push(funct);
	}
	click()
	{
		var eventList = this.eventHandlerLists.onClick;
		for(var functionName in eventList)
		{
			var funct = eventList[functionName];
			funct(this);
		}
	}
}