package game.controller;

import java.util.Map;

import game.FrameIteration;
import game.collection.BufferedHashMap;
import game.collection.ShipCollection;
import persistence.table.entity.Ship;

public class ShipController implements FrameIteration, ObjectController 
{
	protected int id;
	protected double deltaTime = 0;
	protected ShipCollection collection;
	protected double substractAmount = 0;
	
	public ShipController(int shipId, ShipCollection collection)
	{
		this.id = shipId;
		this.collection = collection;
	}
	@Override
	public void add(String actionName, Map<String, Object> arguments)
	{
		
		if(actionName.equals("remove"))
		{
			double amount = Double.parseDouble((String) arguments.get("amount")) ;
			
			substractAmount = substractAmount + amount;
		}
		System.out.println(this + " : Added " + actionName + " with " + arguments + " substract is " + substractAmount);
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
		
		double increment = ship.getValue();
		double newIncrement = increment + (ship.getRate() * (deltaTime/1000));
		
		newIncrement = newIncrement - substractAmount;
		substractAmount = 0;
		
		if(newIncrement < ship.getArea())
		{
			increment = newIncrement;
		}
		else
		{
			increment = ship.getArea();
		}
		
		ship.setValue (increment);
		
		
		
		System.out.println(this + " : Incremented " + ship.getId() +" at " + newIncrement + " Area : " + ship.getArea() + "Substracted " + substractAmount);
		
		collection.put(ship.getId(), ship);
		return this;
	}

	@Override
	public void setDeltaTime(double deltaTime)
	{
		this.deltaTime = deltaTime;
	}

}
