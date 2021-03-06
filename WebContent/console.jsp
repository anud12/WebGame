<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link href="https://fonts.googleapis.com/css?family=Share+Tech+Mono" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Roboto+Mono" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css?family=Cousine" rel="stylesheet">
	
	<link href="css/components/button.css" rel="stylesheet">
	<link href="css/components/context-menu.css" rel="stylesheet">
	<link href="css/components/input.css" rel="stylesheet">
	<link href="css/components/form.css" rel="stylesheet">
	<link href="css/components/font.css" rel="stylesheet">
	<link href="css/components/p.css" rel="stylesheet">
	<link href="css/components/ul.css" rel="stylesheet">
	<link href="css/components/attribute.css" rel="stylesheet">
	<link href="css/components/ship-container.css" rel="stylesheet">
	<link href="css/components/part.css" rel="stylesheet">
	
	<link href="css/jquery-ui/jquery-ui.css" rel="stylesheet">
	<link href="css/jquery-ui/progressbar.css" rel="stylesheet">
	<link href="css/jquery-ui/resizable-handle.css" rel="stylesheet">
	<link href="css/jquery-ui/draggable.css" rel="stylesheet">
	
	<link href="css/window/window.css" rel="stylesheet">
	<link href="css/window/title.css" rel="stylesheet">
	<link href="css/window/sub-menu.css" rel="stylesheet">
	<link href="css/window/window-controls.css" rel="stylesheet">
	
	<link href="css/window/recolor/red.css" rel="stylesheet">
	<link href="css/window/recolor/blue.css" rel="stylesheet">
	<link href="css/window/recolor/green.css" rel="stylesheet">
	
	<link href="css/panel/panel.css" rel="stylesheet">
	<link href="css/panel/panelContainer.css" rel="stylesheet">
	
	<link href="css/window-manager.css" rel="stylesheet">
	<link href="css/desktop.css" rel="stylesheet">
	
	
	<style>
		body
		{
			margin:0px;
			background: white;
			font-family: "Open Sans";
			font-size:12px;
			overflow:hidden;
		}
		button
		{
			font-family: inherit;
		}
		div
		{
			/*border:0.5px solid lightgray;*/
		}
		#ajaxResponse
		{
			
			margin-left:50%;
			width:50%;
			height:100%;
			position:absolute;
			top:0px;
			overflow:overlay;
			background-color:white;
			padding:-1px;
			position:fixed; right:0px; 
			white-space: pre;
			
			font-family:monospace;
			font-size:10px;
			lineheight:9px;
			
		}
		
		#shipList, #shipContainers
		{
			width:calc(50% - 4px);
			display:inline-table;
			border:1px solid lightgray;
		}
		#shipContainers
		{
		}
		
	</style>
	<title>Index</title>
</head>

<body style="overflow:initial">

	<script src="jquery-ui-1.12.1.custom/external/jquery/jquery.js"></script>
	<script src="jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
	<script src="javascript/desktopJS/windowManager.js"></script>
	<script src="javascript/desktopJS/taskbar.js"></script>
	<script src="javascript/ajax/ajax.js"></script>
	<script src="javascript/ajax/ShipManager.js"></script>
	<script src="javascript/ajax/PartManager.js"></script>
	<script src="javascript/Programs/userPanel.js"></script>
	<script src="javascript/Programs/minimap.js"></script>
	
	
	<script>
	$(function()
	{
		
		userPanel.initialize($("#userPanel"));
		ajax.init($("#ajaxResponse"));
		ajax.setMetaDataCallback(function(data)
		{
			$("#ajaxResponse").html("");
			$("#ajaxResponse").append(JSON.stringify(JSON.parse(data.message), null, "  "));
		});
		
		PartManager.init();
		
		ShipManager.init($("#shipList"));
		ShipManager.buildDom = function(ship, dom)
		{
			$("#shipContainers").html(dom);
			console.log("click");
			
			var actionButton = $("<button></button>");
			actionButton.click(function()
			{
				var returnValue = {};
				returnValue["Input"] = {};
				
				returnValue["Input"]["name"] = "remove";
				returnValue["Input"]["target"] = ship.id + "";
				returnValue["Input"]["arguments"] = {};
				returnValue["Input"]["arguments"]["amount"] = "-2";
				returnValue["Input"]["arguments"]["duration"] = "5000";
				ajax.scheduleSend(returnValue, function(){});
			});
			actionButton.text("Decrement " + ship.name);
			
			dom.append(actionButton);
			
			var actionButton = $("<button></button>");
			actionButton.click(function()
			{
				var returnValue = {};
				returnValue["Input"] = {};
				
				returnValue["Input"]["name"] = "move";
				returnValue["Input"]["target"] = ship.id + "";
				returnValue["Input"]["arguments"] = {};
				returnValue["Input"]["arguments"]["x"] = "4";
				returnValue["Input"]["arguments"]["y"] = "2";
				ajax.scheduleSend(returnValue, function(){});
			});
			actionButton.text("Move to (4,2) " + ship.name);
			
			dom.append(actionButton);
			
			var actionButton = $("<button></button>");
			actionButton.click(function()
			{
				var returnValue = {};
				returnValue["Input"] = {};
				
				returnValue["Input"]["name"] = "move";
				returnValue["Input"]["target"] = ship.id + "";
				returnValue["Input"]["arguments"] = {};
				returnValue["Input"]["arguments"]["x"] = "0";
				returnValue["Input"]["arguments"]["y"] = "0";
				ajax.scheduleSend(returnValue, function(){});
			});
			actionButton.text("Move to (0,0) " + ship.name);
			
			dom.append(actionButton);
			
			var actionButton = $("<button></button>");
			actionButton.click(function()
			{
				var returnValue = {};
				returnValue["Input"] = {};
				
				returnValue["Input"]["name"] = "addPart";
				returnValue["Input"]["target"] = ship.id + "";
				returnValue["Input"]["arguments"] = {};
				returnValue["Input"]["arguments"]["id"] = "1";
				returnValue["Input"]["arguments"]["action"] = "add";
				ajax.scheduleSend(returnValue, function(){});
			});
			actionButton.text("Add part type 1 to " + ship.name);
			dom.append(actionButton);
			
			var actionButton = $("<button></button>");
			actionButton.click(function()
			{
				var returnValue = {};
				returnValue["Input"] = {};
				
				returnValue["Input"]["name"] = "addPart";
				returnValue["Input"]["target"] = ship.id + "";
				returnValue["Input"]["arguments"] = {};
				returnValue["Input"]["arguments"]["id"] = "2";
				returnValue["Input"]["arguments"]["action"] = "add";
				ajax.scheduleSend(returnValue, function(){});
			});
			actionButton.text("Add part type 2 to " + ship.name);
			dom.append(actionButton);
			
			var actionButton = $("<button></button>");
			actionButton.click(function()
			{
				var returnValue = {};
				returnValue["Input"] = {};
				
				returnValue["Input"]["name"] = "addPart";
				returnValue["Input"]["target"] = ship.id + "";
				returnValue["Input"]["arguments"] = {};
				returnValue["Input"]["arguments"]["id"] = "4";
				returnValue["Input"]["arguments"]["action"] = "remove";
				ajax.scheduleSend(returnValue, function(){});
			});
			actionButton.text("Remove part 4 from " + ship.name);
			
			dom.append(actionButton);
		}
		
		connection =  $("#connection");
		var pingProgress = $("<div id='progress'></div>").progressbar({value:false, max:40});
		connection.find("content").html("");
		connection.find("content").append(pingProgress);
		connection.find("content").append("<div id='ping-value'></div>");
		
		ajax.setMetaDataCallback(function(data){
			connection.find("content > #ping-value").html("");
			if(data.fail)
			{
				pingProgress.progressbar("value", false);
				connection.find("content > #ping-value").append("Unable to connect");
			}
			else
			{
				pingProgress.progressbar("value", data.ping);
				connection.find("content > #ping-value").append(data.ping);
			}
			});
		
				
		Minimap.initialize($("#minimap"));
		
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
	})
	</script>
	
	<div style = "overflow:overlay; position:fixed; width:50%; height:100%; top:0x; margin-right:50%;display:block; ">
		<div id="connection">
			<content></content>
		</div>
		<div id="userPanel"></div>
		<div id="shipList"></div>
		<div id="shipContainers"></div>
		<div id="minimap"></div>
		
	</div>
	<div style = "">
		<div id="ajaxResponse" style = ""></div>
	</div>
</body>