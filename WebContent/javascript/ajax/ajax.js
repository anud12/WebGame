var ajax = 
{
	init : function()
	{
		var rate = 1000;
		ajax.url = "http://192.168.0.10:8080/HelloWorld/Ajax";
		ajax.method = "POST";
		ajax.ping = 0;
		
		ajax.metaDataCallback = [];
		ajax.sendBuffer = [];
		ajax.sendRate = rate;
		ajax.lastDate = new Date().getTime();
		ajax.sleep = false;
		
		setInterval(ajax.send, rate);
	},
	setUrl : function(url)
	{
		ajax.url = url;
		
	},
	setMetaDataCallback : function(callback)
	{
		if(typeof(callback) == "function")
		{
			ajax.metaDataCallback.push(callback)
		}
	},
	wait : function()
	{
		var date = (new Date()).getTime();
		
		if((date - ajax.lastDate) < ajax.sendRate)
		{
			if(ajax.sleep)
			{
				return true;
			}
			ajax.sleep = true;
			setTimeout(ajax.send, ajax.sendRate - date + ajax.lastDate)
			return true;
		}
		else
		{
			ajax.sleep = false;
		}
		return false;
	},
	send : function()
	{
		if(ajax.wait())
		{
			return;
		};
		
		sendDate = (new Date()).getTime();
		
		sendArray = [];
		sendBuffer = ajax.sendBuffer;
		for(i = 0 ; i < sendBuffer.length ; i++)
		{
			sendArray.push(sendBuffer[i]["data"]);
		}
		
		sendArray.push({filler : {value1 :"0", value2 :[]}});
		
		//console.log(JSON.stringify(sendArray));
		
		$.ajax(
		{
			url:ajax.url,
			method:ajax.method,
			data : JSON.stringify(sendArray),
			mimeType: 'text/plain; charset=x-user-defined'
		})
		.fail(function( msg)
		{
			for(i = 0 ; i < ajax.metaDataCallback.length ; i++)
			{
				ajax.metaDataCallback[i]({
					ping : 0,
					fail : true,
					message : JSON.parse(msg)
					})
			}
		})
		.success(function(msg)
		{
			console.log(JSON.parse(msg));
			ajax.sendBuffer = [];
			
			ajax.lastDate = (new Date()).getTime();

	        responseTimeMs = (ajax.lastDate - sendDate) / 2;
			
			ajax.ping = responseTimeMs;
			
			for(i = 0 ; i < ajax.metaDataCallback.length ; i++)
			{
				ajax.metaDataCallback[i]({
					ping : responseTimeMs,
					fail : false,
					message : msg
					})
			}
			
		})
		
		
	},
	
	scheduleSend : function(sendData, callback)
	{
		var object = {};
		object["data"] = sendData;
		object["callback"] = callback;
		ajax.sendBuffer.push(object);
		ajax.send();
	}
		
}