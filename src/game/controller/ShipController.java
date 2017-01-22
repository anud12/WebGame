package game.controller;

import java.util.HashMap;
import java.util.Map;

import game.FrameIteration;
import game.collection.BufferedHashMap;
import game.collection.ShipCollection;
import game.controller.action.IAction;
import persistence.table.entity.Ship;
import test.game.controller.TestAction;

public class ShipController implements FrameIteration, ObjectController 
{
	protected int id;
	protected int deltaTimeMS = 0;
	protected ShipCollection collection;
	protected Map<String,IAction> actions;
		
	
	public ShipController(int shipId, ShipCollection collection)
	{
		this.id = shipId;
		this.collection = collection;
		this.actions = new HashMap<>();
		
		actions.put("remove", new TestAction());
	}
	@Override
	public void add(String actionName, Map<String, Object> arguments)
	{
		System.out.println(this + " : Add command, requrest");
		if(actionName.equals("remove"))
		{
			actions.get(actionName).addArguments(arguments);
			System.out.println(this + " : Added test command, arg :" + arguments);
		}
		
	}

	@Override
	public void clear()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void queue(String actionName, Map<String, Object> arguments)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object call() throws Exception
	{
		
		Ship ship = (Ship) collection.get(id);
		ship.calculateProperties();
		
		long increment = ship.getEnergy();
		long newIncrement = (long) (increment + (ship.getRate() * deltaTimeMS));
		
		System.out.println(this + " : DeltaTime " + deltaTimeMS + " newIncrement " + newIncrement + " rate " + ship.getRate());
		
		if(newIncrement < ship.getArea())
		{
			increment = newIncrement;
		}
		else
		{
			increment = ship.getArea();
		}
		
		ship.setEnergy (increment);
		
		IAction<Ship> action = actions.get("remove");
		do
		{
			action.setDeltaTimeMS(deltaTimeMS);
			action.setTarget(ship);
			action.call();
			
		}
		while(false);
		
		
		
		ship = action.getTarget();
		
		System.out.println(this + " : Incremented " + ship.getId() +" at " + newIncrement + " Area : " + ship.getArea());
		
		collection.put(ship.getId(), ship);
		return this;
	}

	@Override
	public void setDeltaTimeMS(int deltaTimeMS)
	{
		this.deltaTimeMS = deltaTimeMS;
	}

}
