var serverResponse;
var controls;
var div;
$(function()
{
	
	panelManager.setParent($("window-manager"));
	taskbar = panelManager.createPanel(panelManager.location.left);
	
	windowManager.initialize($("desktop"), taskbar );
	
	connection =  windowManager.newWindow({windowName:"Connection status"});
	serverResponse =  windowManager.newWindow({windowName:"Server Response"});
	
	shipList = windowManager.newWindow({windowName:"Ship list", className: "green", width:200, height:250});
	var user = windowManager.newWindow({windowName:"User", className: "blue", height:150});
	
	var red = windowManager.newWindow({windowName:"Long window name please", className: "red"});
	
	
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
		serverResponse.dom.find("content").append(data.message);
		});
	
	ShipManager.init(shipList.dom.find("content"));
	
	//var chat =  newWindow("Chat").find("content").append($("attribute-list"))
	//chat.find("menu-bar").append("<button>Options</button>");
	//chat.find("content").append($("chat"));
	
	$(".ui-resizable-handle").removeAttr("style");
});