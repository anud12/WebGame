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
		
		var ship = ShipManager.ships[arrayShip['identity']['name']];
		
		keys = Object.keys(arrayShip);
		
		for(var j = 0 ; j < keys.length ; j++)
		{
			key = keys[j];
			ship[key] = arrayShip[key];
		}
		
		ship.update();
		
		if(isNew)
		{
			container = ShipManager.container;
			
			button = $("<button class='ship'></button>");
			button.text(ship['identity']['name']);
			button.click(function()
			{
				var shipWindow = ShipManager.createShipDom(ship)
				ship['window'] = shipWindow;
				ShipManager.buildDom(ship, shipWindow);
			});
			container.append(button);
			
			ShipManager.createShipDom(ship);
		}
	},
	
	
	buildDom(ship, shipWindow)
	{
		var window = windowManager.newWindow({windowName: ship['identity']['name'], closeable: true, height: 250, className: "green"});
		window.content.append(shipWindow);
		
		console.log(ship);
	},
	
	createShipDom(ship)
	{
		var shipContainer = $("<ship-container></ship-container");
		
		var title = $("<p class = 'title'></p>");
		title.text(ship['identity']['name']);
		shipContainer.append(title);
		
		console.log(ship);
		
		var values = [];
		
		for(var categoryNumber = 0 ; categoryNumber < ship.statistics.length ; categoryNumber++)
		{
			
			category = ship['statistics'][categoryNumber];
			var valueList = $("<ul class = 'value-list'></ul>")
			shipContainer.append(valueList);
			
			var categoryTitle = $("<p class = 'category-title'></p>");
			categoryTitle.text(category['name']);
			valueList.append(categoryTitle);
			
			
			
			values[categoryNumber] = [];
			
			for(var valueNumber = 0 ; valueNumber < category['values'].length ; valueNumber++)
			{
				shipValue = ship['statistics'][categoryNumber]['values'][valueNumber];
				
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
			for(categoryNumber = 0 ; categoryNumber < values.length ; categoryNumber++)
			{
				for(valueNumber = 0 ; valueNumber < values[categoryNumber].length ; valueNumber++)
				{
					values[categoryNumber][valueNumber].text(ship['statistics'][categoryNumber]['values'][valueNumber]['value']);
				}
			}
			
			
			
		}
		ship.update();
		return shipContainer;
	}
}