var panelManager = 
{
	
	setParent : function(dom)
	{
		var bottom = $("<panel-container></panel-container>");
		/*
		bottom.addClass("bottom");
		dom.prepend(bottom);
		panelManager.bottomContainer = bottom;
		*/
		var left = $("<panel-container></panel-container>");
		left.addClass("left");
		dom.prepend(left);
		panelManager.leftContainer = left;
	},
	
	createPanel : function(location)
	{
		var panel = $("<panel></panel>");
		
		switch(location)
		{
			case panelManager.location.bottom:
			{
				panelManager.bottomContainer.append(panel);
				break;	
			}
			case panelManager.location.left:
			{
				panelManager.leftContainer.append(panel);
				break;
			}
		}
		
		return panel;
	},

	location : {
		bottom:"bottom",
		left:"left"
	}
}