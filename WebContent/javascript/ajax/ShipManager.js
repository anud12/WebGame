var ShipManager =
{

	init : function(dom)
	{
		ShipManager.dom = dom;
		ShipManager.container = $("<container></container");
		ShipManager.dom.append(ShipManager.container);
		
		ShipManager.ships = [];
		
		ajax.setMetaDataCallback(ShipManager.ajaxParse);
	},
	
	
	ajaxParse : function(data)
	{
		var message = JSON.parse(data['message']);
		
		var array = message['user']['ship'];
		
		for(var i = 0 ; i < array.length; i++)
		{
			var element = array[i];
			ShipManager.updateShip(element);
		}
	},
	
	
	updateShip(arrayShip)
	{
		isNew = false;
		if(typeof(ShipManager.ships[arrayShip['identity']['name']]) == 'undefined')
		{
			ShipManager.ships[arrayShip['identity']['name']] = {};
			ShipManager.ships[arrayShip['identity']['name']]['update'] = function(){};
			isNew = true;
		}
		
		ship = ShipManager.ships[arrayShip['identity']['name']];
		
		keys = Object.keys(arrayShip);
		
		for(var j = 0 ; j < keys.length ; j++)
		{
			key = keys[j];
			ship[key] = arrayShip[key];
		}
		
		ship.update();
		
		if(isNew)
		{
			ShipManager.buildDom(ship);
		}
	},
	
	
	buildDom(ship)
	{
		container = ShipManager.container;
		
		button = $("<button class='ship'></button>");
		button.text(ship['identity']['name']);
		button.click(function()
		{
			ship['window'] = ShipManager.createWindow(ship);
			console.log(ship);
		});
		container.append(button);
	},
	
	
	createWindow(ship)
	{
		shipWindow = windowManager.newWindow({windowName: ship['identity']['name'], closeable: true, height: 250, className: "green"});
		
		var shipContainer = $("<ship-container></ship-container");
		shipWindow.content.append(shipContainer);
		
		var title = $("<p class = 'title'></p>");
		title.text(ship['identity']['name']);
		shipContainer.append(title);
		
		console.log(ship);
		
		var values = [];
		
		for(var categoryNumber = 0 ; categoryNumber < ship.values.length ; categoryNumber++)
		{
			
			category = ship['values'][categoryNumber];
			var valueList = $("<ul class = 'value-list'></ul>")
			shipContainer.append(valueList);
			
			var categoryTitle = $("<p class = 'category-title'></p>");
			categoryTitle.text(category['name']);
			valueList.append(categoryTitle);
			
			
			
			values[categoryNumber] = [];
			
			for(var valueNumber = 0 ; valueNumber < category['values'].length ; valueNumber++)
			{
				shipValue = ship['values'][categoryNumber]['values'][valueNumber];
				
				var listObject = $("<li></li>");
				valueList.append(listObject);
				
				var attribute = $("<attribute></attribute");
				listObject.append(attribute);
				
				var label = $("<label></label>")
				label.text(shipValue['name']);
				attribute.append(label);
				
				var value = $("<p class = 'value'></p>");
				attribute.append(value);
				
				values[categoryNumber][valueNumber] = value;
			}
		}
		
		ship.update = function()
		{
			console.log(values);
			for(categoryNumber = 0 ; categoryNumber < values.length ; categoryNumber++)
			{
				for(valueNumber = 0 ; valueNumber < values[categoryNumber].length ; valueNumber++)
				{
					values[categoryNumber][valueNumber].text(ship['values'][categoryNumber]['values'][valueNumber]['value']);
				}
			}
			
			
			
		}
		return shipWindow;
	}
}