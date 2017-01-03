var userPanel =
{
	initialize : function(dom)
	{
		var container = $("<div class='user-panel'></div>");
		
		if(typeof(userPanel.user) == "undefined")
		{
			userPanel.createLoginPanel(container);
		}
		$(dom).append(container);
	},
	
	createConfigPanel : function(container)
	{
		var userData = $("<div class='font-title'> Username </div>")
		container.append(userData);
		
		var control = $("<div class='control-container'></div>");
		container.append(control);
		
		var logoutButton = $("<button>Log out</button>");
		control.append(logoutButton);
		
	},
	
	createLoginPanel : function(container)
	{
		var form = $("<form></form").submit(function(event)
				{
					userPanel.login(container);
					event.preventDefault();
				})
		var username = $("<input type='textfield' class='username' placeholder='Username'></input>");
		form.append(username);
		
		var password = $("<input type='password' class='password' placeholder='Password'></input>");
		form.append(password);
		
		var submit = $("<button> Log in </button>");
		submit.click(function()
				{
					userPanel.login(container);
				});
		form.append(submit);
		
		container.append(form);
	},
	
	login : function(container)
	{
		var username = container.find(".username");
		var password = container.find(".password");
		
		var returnValue = {};
		
		returnValue["Login"] = {};
		
		returnValue["Login"]["username"] = username.val();
		returnValue["Login"]["password"] = password.val();
		
		ajax.scheduleSend(returnValue, userPanel.onSend);
	},
	onSend : function(message)
	{
		
	}
	
}