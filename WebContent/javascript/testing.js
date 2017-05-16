var serverResponse;
var controls;
var div;
$(function()
{

	var panel = new Panel();
	
	windowManager = new WindowManager();
	$("body").append(windowManager.dom);
	
	connection =  windowManager.newWindow({title:"Connection status"});
	serverResponse =  windowManager.newWindow({title:"Server Response"});
	
	shipList = windowManager.newWindow({title:"Ship list", className: "green", width:200, height:250});
	var user = windowManager.newWindow({title:"User", className: "blue", height:150});
	
	var red = windowManager.newWindow({title:"Long window name please", className: "red"});
	
	
	userPanel.initialize(user.dom.find("content"));
	
	
	var pingProgress = $("<div id='progress'></div>").progressbar({value:false, max:40});
	connection.dom.find("content").html("");
	connection.dom.find("content").append(pingProgress);
	connection.dom.find("content").append("<div id='ping-value'></div>");
	serverResponse.content.addClass("font-mono");
	
	
	ajax.init(serverResponse.dom.find("content"));
	ajax.setMetaDataCallback(function(data){
		connection.dom.find("content > #ping-value").html("");
		if(data.fail)
		{
			pingProgress.progressbar("value", false);
			connection.dom.find("content > #ping-value").append("Unable to connect");
		}
		else
		{
			pingProgress.progressbar("value", data.ping);
			connection.dom.find("content > #ping-value").append(data.ping);
		}
		
		
		serverResponse.dom.find("content").html("");
		serverResponse.dom.find("content").append(JSON.stringify(JSON.parse(data.message), null, "  "));
		});
	
	PartManager.init();
	ShipManager.init(shipList.dom.find("content"));
	
	//Minimap
	
	minimapDom = windowManager.newWindow({title:"Minimap"});
	
	Minimap.initialize(minimapDom.dom.find("content"));
	
	$(minimapDom.contentContainer).css("overflow", "hidden");
	$(minimapDom.dom).resize(function()
			{
				console.log(minimapDom.contentContainer.height());
				var height = minimapDom.contentContainer.height();
				var width = minimapDom.contentContainer.width();
				Minimap.resize(height, width);
			})
	
	Minimap.onClick(function(location)
			{
				var returnArray = [];
				var selectedShips = ShipManager.selectedShips;
				
				for(var i = 0 ; i < selectedShips.length ; i++)
				{
					var shipID = ShipManager.ships[selectedShips[i]].id;
					var returnValue = {};
					returnValue["Input"] = {};
					
					returnValue["Input"]["name"] = "move";
					returnValue["Input"]["target"] = shipID + "";
					returnValue["Input"]["arguments"] = {};
					returnValue["Input"]["arguments"]["x"] = Math.round(location.x) + "";
					returnValue["Input"]["arguments"]["y"] = Math.round(location.y) + "";
					returnArray.push(returnValue);
				}
				
				for(var i = 0 ; i < returnArray.length ; i++)
				{
					ajax.scheduleSend(returnArray[i], function(){});
					console.log(returnArray[i]);
				}
	});
	ShipManager.addUpdateCallback(function()
			{
				for(var i = 0; i < ShipManager.shipKeys.length ; i++)
					{
						var key = ShipManager.shipKeys[i];
						
						var ship = ShipManager.ships[key];
						
						var object = {};
						object.x = ship.x / 100;
						object.y = ship.y / 100;
						object.color = "green";							
						Minimap.update(ship.name, object);
					}
			});
	
	//var chat =  newWindow("Chat").find("content").append($("attribute-list"))
	//chat.find("menu-bar").append("<button>Options</button>");
	//chat.find("content").append($("chat"));
	
	$(".ui-resizable-handle").removeAttr("style");
});