PartManager =
{
	init : function()
	{
		ajax.setMetaDataCallback(PartManager.parse);
	},
	buildDom : function(shipTarget, partType)
	{
		console.log(partType);
		var object = PartManager.object;
		console.log(object);
		var part = object[partType];
		
		var returnDom = $("<part></part>");
		var button = $("<button></button>");
		returnDom.append(button);
		button.click(function()
		{
			console.log(part.name);
			returnDom.toggleClass("maximized");
		});
		button.text(part.name);
		
		var statsContainer = $("<ul class = 'value-list'></ul>");
		returnDom.append(statsContainer);
		
		for(var i = 0 ; i < part.keyValues.length ; i++)
		{
			var stat = part.keyValues[i];
			var statDom = $("<li></li>");
			statsContainer.append(statDom);
			
			var attribute = $("<attribute></attribute");
			statDom.append(attribute);
			
			var label = $("<label></label>")
			label.text(stat);
			attribute.append(label);
			
			var value = $("<p class = 'value'></p>");
			value.text(part[stat])
			attribute.append(value);
		}
		
		var removeAction = $("<button></button>");
		statsContainer.append(removeAction);
		removeAction.text("Remove");
		removeAction.click(function()
		{
			
		});
		
		
		
		return returnDom;
	},
	parse : function(string)
	{
		message = JSON.parse(string['message']);
		PartManager.object = message['data']['parts'];
	}
	
}