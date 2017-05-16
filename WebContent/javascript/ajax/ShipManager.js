var ShipManager =
{

	init : function(dom)
	{
		ShipManager.dom = dom;
		ShipManager.container = $("<ship-list></ship-list");
		ShipManager.dom.append(ShipManager.container);
		
		ShipManager.ships = {};
		ShipManager.shipKeys = [];
		ShipManager.selectedShips = [];
		ShipManager.updateCallbacks = [];
		ShipManager.removeCallbacks = [];
		
		ajax.setMetaDataCallback(ShipManager.ajaxParse);
	},
	
	
	ajaxParse : function(data)
	{
		var parsedShipKeys = []; 
		message = JSON.parse(data['message']);
		
		array = message['data']['user']['ship'];
		
		for(var i = 0 ; i < array.length; i++)
		{
			arrayShip = array[i];
						
			if(typeof(ShipManager.ships[arrayShip['name']]) == 'undefined')
			{
				ShipManager.createShip(arrayShip);
			}
			ShipManager.updateShip(arrayShip);
			
			parsedShipKeys.push(arrayShip['name']);
		}
		
		//console.log(parsedShipKeys);
		//console.log(ShipManager.ships);
		
		for(var i = 0 ; i < (ShipManager.shipKeys.length); i++)
		{
			if(parsedShipKeys.indexOf(ShipManager.shipKeys[i]) == -1)
			{
				var shipName = ShipManager.shipKeys[i];
				ShipManager.deleteShip(ShipManager.ships[shipName]);
				i--;
			}
		}
	},
	
	createShip: function (arrayShip)
	{	
		ShipManager.shipKeys.push(arrayShip['name']);
		ShipManager.ships[arrayShip['name']] = {};
		savedShip = ShipManager.ships[arrayShip['name']];
		
		savedShip.dom = {};
		savedShip.update = function(){};
		
		container = ShipManager.container;
		
		//CreateButton
		savedShip.dom.buttons = $("<div  class='shipButtons'></div>");
		var buttonsDiv = savedShip.dom.buttons;
		
		var button = $("<button class='inlineButtonFill'></button>");
		button.text(arrayShip['name']);
		button.click(function()
		{
			var shipWindow = ShipManager.createShipDom(arrayShip)
			savedShip.dom.window = shipWindow;
			ShipManager.buildDom(arrayShip, shipWindow);
		});
		
		var selectButton = $("<button class='inlineButton45'></button>");
		selectButton.text("[ ]");
		selectButton.click(function()
		{
			
			var selectedShips = ShipManager.selectedShips;
			
			if(selectedShips.indexOf(arrayShip['name']) == -1)
			{
				
				selectedShips.push(arrayShip['name']);
				selectButton.text("[X]");
			}		
			else
			{
				var index = selectedShips.indexOf(arrayShip["name"]);
				selectedShips.splice(index, 1);
				selectButton.text("[ ]");
			}
				
			console.log(selectedShips);
		});
		buttonsDiv.append(button);
		buttonsDiv.append(selectButton);
		
		container.append(savedShip.dom.buttons);
		
		ShipManager.createShipDom(arrayShip);
	},
	
	updateShip : function(arrayShip)
	{
		var ship = ShipManager.ships[arrayShip['name']];
				
		keys = Object.keys(arrayShip);
		
		for(var j = 0 ; j < keys.length ; j++)
		{
			key = keys[j];
			ship[key] = arrayShip[key];
		}
		
		ship.update(arrayShip);
		
		for(var i = 0 ; i < ShipManager.updateCallbacks.length ; i++)
		{
			if(typeof(ShipManager.updateCallbacks[i]) != "undefined")
			{
				var action = ShipManager.updateCallbacks[i];
				action();
				
			}
		}
		
	},
	
	deleteShip : function(arrayShip)
	{		
		var ship = arrayShip;
		
		//Deleting doms
		$(ship.dom.buttons).remove();
		$(ship.dom.window).remove();
		
		//Deleting from ships
		delete(ShipManager.ships[arrayShip["name"]]);
		
		//Deleting from shipKeys
		var index = ShipManager.shipKeys.indexOf(arrayShip["name"]);
		ShipManager.shipKeys.splice(index, 1);
		ShipManager.selectedShips.pop(arrayShip["name"]);
		
		console.log(ShipManager.ships);
		console.log(ShipManager.shipKeys);
		
		for(var i = 0 ; i < ShipManager.removeCallbacks.length ; i++)
		{
			if(typeof(ShipManager.removeCallbacks[i]) != "undefined")
			{
				var action = ShipManager.removeCallbacks[i];
				action();
				
			}
		}
	},
	
	addUpdateCallback : function(callback)
	{
		this.updateCallbacks.push(callback);
	},
	addRemoveCallback : function(callback)
	{
		this.removeCallbacks.push(callback);
	},
	
	buildDom(ship, shipWindow)
	{
		var window = windowManager.newWindow({windowName: ship['name'], closeable: true, height: 250, className: "green"});
		window.content.append(shipWindow);
		
	},
	
	createShipDom(ship)
	{
		var savedShip = ShipManager.ships[ship['name']];
		
		shipContainer = $("<ship-container></ship-container");
		savedShip.dom.container = shipContainer;
				
		var title = $("<p class = 'title'></p>");
		title.text(ship['name']);
		shipContainer.append(title);
		
		var category = "Statistics";
		valueList = $("<ul class = 'value-list'></ul>")
		shipContainer.append(valueList);
		
		var categoryTitle = $("<p class = 'category-title'></p>");
		categoryTitle.text(category);
		valueList.append(categoryTitle);
				
		savedShip.dom.values = [];
			
		for(var valueNumber = 0 ; valueNumber < ship.keyValues.length ; valueNumber++)
		{
			var valueName = ship.keyValues[valueNumber]
			shipValue = ship[valueName];
			
			var listObject = $("<li></li>");
			valueList.append(listObject);
			
			var attribute = $("<attribute></attribute");
			listObject.append(attribute);
			
			var label = $("<label></label>")
			label.text(valueName);
			attribute.append(label);
			
			var value = $("<p class = 'value'></p>");
			value.text(shipValue)
			attribute.append(value);
			
			savedShip.dom.values.push({name:valueName, dom: value});
		}
		
		savedShip.dom.parts = [];
		
		for(var i = 0 ; i < ship.parts.length ; i++)
		{
			var part = ship.parts[i];
			var partDom = PartManager.buildDom(ship, part.name);
			
			shipContainer.append(partDom);
		}
		
		ship.update = function(shipData)
		{			
			for(valueNumber = 0 ; valueNumber < savedShip.dom.values.length ; valueNumber++)
			{
				var value = savedShip.dom.values[valueNumber];
				
				value.dom.text(shipData[value.name]);
			}
			
		}
		ship.update(ship);
		return shipContainer;
	}
}