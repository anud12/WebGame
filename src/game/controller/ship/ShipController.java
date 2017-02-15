package game.controller.ship;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import game.FrameIteration;
import game.collection.ShipCollection;
import game.controller.IAction;
import game.controller.ObjectController;
import persistence.table.entity.Ship;
import test.game.controller.TestAction;

public class ShipController implements FrameIteration, ObjectController 
{
	protected int id;
	protected int deltaTimeMS = 0;
	protected ShipCollection collection;
	protected Map<String,IAction<ShipWrapper>> actions;
	protected LinkedList<String> actionKeys;
	
	public ShipController(int shipId, ShipCollection collection)
	{
		this.id = shipId;
		this.collection = collection;
		this.actions = new HashMap<>();
		
		actions.put("remove", new TestAction());
		actions.put("part", new PartAction());
		actions.put("move", new MoveAction());
		
		actionKeys = new LinkedList<>();
		
		actionKeys.addLast("part");
		actionKeys.addLast("remove");
		actionKeys.addLast("move");
	}
	@Override
	public void add(String actionName, Map<String, Object> arguments)
	{
		System.out.println(this + " : Add command, requrest");
		if(actionName.equals("remove"))
		{
			actions.get(actionName).addArguments(arguments);
			System.out.println(this + " : Added test command, arg :" + arguments);
			return;
		}
		if(actionName.equals("move"))
		{
			actions.get(actionName).addArguments(arguments);
			System.out.println(this + " : Added move command, arg :" + arguments);
			return;
		}
	}
	@Override
	public Object call() throws Exception
	{
		
		Ship ship = (Ship) collection.get(id);
		ship.calculateProperties();
		
		ShipWrapper shipWrapper = new ShipWrapper(ship);
		
		Iterator<String> actionIterator = actionKeys.iterator();
		while(actionIterator.hasNext())
		{
			IAction<ShipWrapper> action = actions.get(actionIterator.next());
			action.setDeltaTimeMS(deltaTimeMS);
			action.setTarget(shipWrapper);
			action.call();
			shipWrapper.notifySubscribers();
		}
		
		
		System.out.println(this + " : Incremented " + ship.getId() +"  Area : " + ship.getArea());
		
		collection.put(ship.getId(), ship);
		return this;
	}

	@Override
	public void setDeltaTimeMS(int deltaTimeMS)
	{
		this.deltaTimeMS = deltaTimeMS;
	}

}
