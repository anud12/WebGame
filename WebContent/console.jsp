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
			border:0.5px solid lightgray;
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
	<script src="javascript/Programs/userPanel.js"></script>
	
	
	<script>
	$(function()
	{
		userPanel.initialize($("#userPanel"));
		ajax.init($("#ajaxResponse"));
		ajax.setMetaDataCallback(function(data)
		{
			$("#ajaxResponse").html("");
			console.log(typeof(data.message));
			$("#ajaxResponse").append(JSON.stringify(JSON.parse(data.message), null, "  "));
		});
		
		$("#testButton").click(function()
		{
			var returnValue = {};
			returnValue["Input"] = {};
			
			returnValue["Input"]["name"] = "remove";
			returnValue["Input"]["arguments"] = {};
			returnValue["Input"]["arguments"]["amount"] = "100";
			ajax.scheduleSend(returnValue, function(){});
			console.log("Succesuffulllkdpok")
		});
		
	})
	</script>
	
	<div style = "position:absolute; width:50%; height:100%; top:0x; margin-right:50%;display:block;">
		<div id="userPanel"></div>
		<button id="testButton">Decrement by 100</button>
	</div>
	<div style = "">
		<div id="ajaxResponse" style = "white-space: pre; font-family:'Cousine'; font-size:10px; line-height:9px"></div>
	</div>
</body>