package test.game;

import java.util.Set;

import org.hibernate.Session;

import game.FrameIteration;
import game.collection.BufferedHashMap;
import game.collection.GameDataContainer;
import game.collection.ShipCollection;
import persistence.Persistence;
import persistence.table.entity.Ship;
import spring.Spring;

public class ShipUpdate implements FrameIteration
{
	protected int id;
	protected double deltaTime;
	protected ShipCollection shipCollection;
	
	public ShipUpdate(int id, ShipCollection gameDataContainer)
	{
		this.id = id;
		this.shipCollection = gameDataContainer;
	}
	
	@Override
	public void setDeltaTime(double deltaTime)
	{
		this.deltaTime = deltaTime;
	}
	
	@Override
	public Object call() throws Exception
	{
		BufferedHashMap<Integer, Object> shipMap = shipCollection;
				
		Ship ship = (Ship) shipMap.get(id);
		
		ship.calculateProperties();
		
		//Ship ship = (Ship) Tables.getShips().get(id);
		
		double increment = ship.getValue();
		
		double newIncrement = increment + (ship.getRate() * (deltaTime/1000)); 
		
		if(newIncrement < ship.getArea())
		{
			increment = newIncrement;
		}
		
		ship.setValue (increment);
		
		shipMap.put(ship.getId(), ship);
		
		System.out.println("Incremented " + newIncrement + " Area : " + ship.getArea());
		return this;
	}
	
}
