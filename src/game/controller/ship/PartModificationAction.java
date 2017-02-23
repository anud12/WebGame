package game.controller.ship;

import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import game.collection.GameDataContainer;
import game.controller.IAction;
import persistence.table.entity.Part;

public class PartModificationAction implements IAction<ShipWrapper>
{
	protected GameDataContainer container;
	protected LinkedList<Map<String, Object>> argumentList;
	protected ShipWrapper ship;
	
	public PartModificationAction()
	{
		argumentList = new LinkedList<>();
	}
	@Override
	public IAction<ShipWrapper> call() throws Exception
	{
		Logger logger = Logger.getLogger("Ship " + ship.getId());
		//Check if there are any actions to do
		if(argumentList.size() < 1)
		{
			return this;
		}
		
		Map<String, Object> argument = (Map<String, Object>) argumentList.remove();
		
		String action = (String) argument.get("action");
		
		if(action.equals("add"))
		{
			addAction(argument);
		}
		if(action.equals("remove"))
		{
			removeAction(argument);
		}
		
		
		
		return this;
	}
	
	protected void addAction(Map argument)
	{
		int partID = Integer.parseInt((String) argument.get("id"));
		
		System.out.println(this + " : Container " + container);
		System.out.println(this + " : Part " + container.get("Part"));
		
		Part part = (Part) container.get("Part").get(partID);
		
		ship.getPartList().add((Part) part);
		System.out.println(this + " : Added part " + partID + " size (" + ship.getPartList().size() + " )");
	}
	protected void removeAction(Map argument)
	{
		int partID = Integer.parseInt((String) argument.get("id"));
		
		System.out.println(this + " : Container " + container);
		System.out.println(this + " : Part " + container.get("Part"));
		
		ship.getPartList().remove(partID);
		
		System.out.println(this + " : Removed part " + partID + " size (" + ship.getPartList().size() + " )");
	}
	
	@Override
	public void subscriptionNotice(ShipWrapper object)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArguments(Map<String, Object> arguments)
	{
		argumentList.add(arguments);
	}

	@Override
	public void setTarget(ShipWrapper object)
	{
		this.ship = object;
	}

	@Override
	public ShipWrapper getTarget()
	{
		return ship;
	}

	@Override
	public void setDeltaTimeMS(int deltaTime)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public JSONObject getJSONDefinition()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataContainer(GameDataContainer container)
	{
		this.container = container;
	}

}
