var ShipManager =
{

	init : function(dom)
	{
		ShipManager.dom = dom;
		ShipManager.container = $("<container></container");
		ShipManager.dom.append(ShipManager.container);
		
		ShipManager.ships = [];
		ShipManager.shipKeys = [];
		ShipManager.callbacks = [];
		
		ajax.setMetaDataCallback(ShipManager.ajaxParse);
	},
	
	
	ajaxParse : function(data)
	{
		message = JSON.parse(data['message']);
		
		array = message['user']['ship'];
		
		for(var i = 0 ; i < array.length; i++)
		{
			element = array[i];
			ShipManager.updateShip(element);
		}
	},
	
	
	updateShip(arrayShip)
	{
		isNew = false;
		if(typeof(ShipManager.ships[arrayShip['name']]) == 'undefined')
		{
			ShipManager.shipKeys.push(arrayShip['name']);
			
			ShipManager.ships[arrayShip['name']] = {};
			ShipManager.ships[arrayShip['name']]['update'] = function(){};
			isNew = true;
		}
		
		var ship = ShipManager.ships[arrayShip['name']];
		
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
			button.text(ship['name']);
			button.click(function()
			{
				var shipWindow = ShipManager.createShipDom(ship)
				ship['window'] = shipWindow;
				ShipManager.buildDom(ship, shipWindow);
			});
			container.append(button);
			
			ShipManager.createShipDom(ship);
		}
		
		for(var i = 0 ; i < ShipManager.callbacks.length ; i++)
		{
			if(typeof(ShipManager.callbacks[i]) != "undefined")
			{
				var action = ShipManager.callbacks[i];
				action();
			}
		}
		
	},
	
	addUpdateCallback : function(callback)
	{
		this.callbacks.push(callback);
	},
	buildDom(ship, shipWindow)
	{
		var window = windowManager.newWindow({windowName: ship['name'], closeable: true, height: 250, className: "green"});
		window.content.append(shipWindow);
		
		console.log(ship);
	},
	
	createShipDom(ship)
	{
		shipContainer = $("<ship-container></ship-container");
		
		title = $("<p class = 'title'></p>");
		title.text(ship['name']);
		shipContainer.append(title);
		
		category = "Statistics";
		valueList = $("<ul class = 'value-list'></ul>")
		shipContainer.append(valueList);
		
		categoryTitle = $("<p class = 'category-title'></p>");
		categoryTitle.text(category);
		valueList.append(categoryTitle);
		
		console.log(ship);
				
		var values = [];
			
		for(var valueNumber = 0 ; valueNumber < ship.keyValues.length ; valueNumber++)
		{
			valueName = ship.keyValues[valueNumber]
			shipValue = ship[valueName];
			
			listObject = $("<li></li>");
			valueList.append(listObject);
			
			attribute = $("<attribute></attribute");
			listObject.append(attribute);
			
			label = $("<label></label>")
			label.text(valueName);
			attribute.append(label);
			
			value = $("<p class = 'value'></p>");
			value.text(shipValue)
			attribute.append(value);
			
			values.push({name:valueName, dom: value});
		}
		
		
		
		ship.update = function()
		{
			for(valueNumber = 0 ; valueNumber < values.length ; valueNumber++)
			{
				values[valueNumber].dom.text(ship[values[valueNumber].name]);
			}
			
			
			
		}
		ship.update();
		return shipContainer;
	}
}