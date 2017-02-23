var Minimap =
{
	initialize : function(dom)
	{
		this.container = dom;
		
		this.toolbar = $("<div></div>");
		this.container.append(this.toolbar);
		this.mouseLocation = $("<div>Location (X,Y)</div>");
		this.toolbar.append(this.mouseLocation);
		
		Minimap.canvas =  $("<canvas></canvas>")[0];
		Minimap.canvas.width = 500;
		Minimap.canvas.height = 500;
		
		$(Minimap.canvas).mousemove(this.mouseHoverEvent)
		$(Minimap.canvas).click(this.mouseClickEvent);
		
		dom.append(this.canvas);
		
		this.clearColor = "black";
		
		this.canvas.getContext('2d').fillStyle = this.clearColor;
		this.canvas.getContext('2d').fillRect(0,0,10000,10000);
		this.canvas.getContext('2d').fill();
		this.objects = {};
		
		this.callbacks = [];
	},
	
	resize : function()
	{
		Minimap.canvas.width = this.container.width;
		Minimap.canvas.height = this.container.height;
	},
	
	update : function(name, object)
	{
		
		if(typeof(this.objects[name]) != "undefined")
		{
			var oldObject = this.objects[name];
			
			var context = this.canvas.getContext('2d');
						
			context.beginPath();
			context.fillStyle = this.clearColor;
			context.arc(oldObject.x, oldObject.y , 4, 0, 2 * Math.PI);
			context.fill();
			
			context.beginPath();
			context.fillStyle = object.color;
			context.arc(object.x, object.y , 3, 0, 2 * Math.PI);
			context.fill();
		}
		
		this.objects[name] = object;
	},
	
	mouseHoverEvent : function(event)
	{
		var x = event.pageX - $(Minimap.canvas).position().left;
		var y = event.pageY - $(Minimap.canvas).position().top;
		$(Minimap.mouseLocation).text("Location (" + x + "," + y + ")");
	},
	
	onClick : function(callback)
	{
		this.callbacks.push(callback);
	},
	mouseClickEvent : function(event)
	{
		var X = event.pageX - $(Minimap.canvas).position().left;
		var Y = event.pageY - $(Minimap.canvas).position().top;
		var location = {x:X, y:Y}
		for(var i = 0 ; i < Minimap.callbacks.length ; i++)
		{
			var action = Minimap.callbacks[i];
			action(location);
		}
	}
}