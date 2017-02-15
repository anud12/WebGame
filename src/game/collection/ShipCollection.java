package game.collection;

import java.util.HashMap;
import java.util.Iterator;

import org.hibernate.Session;

import game.GameContext;
import game.controller.ObjectController;
import game.controller.ship.ShipController;
import persistence.Persistence;
import persistence.table.entity.Ship;

public class ShipCollection extends BufferedHashMap<Integer, Object>
{	
	protected GameContext gameContext;
	protected Persistence persistence;
	protected HashMap<Integer, ObjectController> controllers;
	
	public ShipCollection(GameContext context)
	{
		System.out.println(this + " : Constructed");
		this.gameContext = context;
		controllers = new HashMap<>();
	}
	@Override
	public Object put(Integer integer, Object ship)
	{
		Object returnShip = super.put(integer, ship);
		
		Ship shipObject = (Ship) ship;
		
		if(!this.containsKey(integer))
		{
			
			System.out.println(this + " : Added ship " + shipObject.getId());
			
			ShipController controller = new ShipController(shipObject.getId(), this);
			
			gameContext.getCommands().put(ship, controller);
			controllers.put(shipObject.getId(), controller);
			
			return returnShip;
		}
		
		System.out.println(this + " : Update ship " + ((Ship) ship).getId());
		return returnShip;
	}
	
	@Override
	public Object remove(Object ship)
	{
		
		System.out.println(this + " : Removed ship " + ((Ship)ship).getId());
		return super.remove(ship);
	}
	
	@Override
	public void swap()
	{
		super.swap();
		System.out.println(this + " : Swap");
		if(persistence != null)
		{
			Session session = persistence.getSessionFactory().openSession();
			session.beginTransaction();
			
			Iterator<Object> iterator = this.getReadMap().values().iterator();
			while(iterator.hasNext())
			{
				Object ship = iterator.next();
				
				session.merge(ship);
				session.flush();
			}
			session.close();
		}
		
	}
	
	
	public Persistence getPersistence()
	{
		return persistence;
	}
	public void setPersistence(Persistence persistence)
	{
		this.persistence = persistence;
	}
	public HashMap<Integer, ObjectController> getControllers()
	{
		return controllers;
	}
	public void setControllers(HashMap<Integer, ObjectController> controllers)
	{
		this.controllers = controllers;
	}
}
