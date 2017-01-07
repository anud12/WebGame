package game.collection;

import java.util.Iterator;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import game.GameContext;
import persistence.Persistence;
import persistence.table.entity.Ship;
import test.game.ShipUpdate;

public class ShipCollection extends BufferedHashMap<Integer, Object>
{	
	protected GameContext gameContext;
	protected Persistence persistence;
	
	public ShipCollection(GameContext context)
	{
		this.gameContext = context;
		
	}
	
	
	
	public Persistence getPersistence()
	{
		return persistence;
	}



	public void setPersistence(Persistence persistence)
	{
		this.persistence = persistence;
	}



	@Override
	public Object put(Integer integer, Object ship)
	{
		Object returnShip = super.put(integer, ship);
		
		if(!this.containsKey(integer))
		{
			
			System.out.println("New ship " + ((Ship) ship).getId());
			gameContext.getCommands().put(ship, new ShipUpdate(((Ship) ship).getId(), this));
			
			return returnShip;
		}
		
		System.out.println("Put : " + persistence);		
		return returnShip;
	}
	
	@Override
	public Object remove(Object ship)
	{
		
		System.out.println("Removed ship" + ((Ship)ship).getId());
		return super.remove(ship);
	}
	
	@Override
	public void swap()
	{
		super.swap();
		
		System.out.println("Swap :" + persistence);
		
		if(persistence != null)
		{
			Session session = persistence.getSessionFactory().openSession();
			session.beginTransaction();
			
			Iterator<Object> iterator = this.getReadMap().values().iterator();
			while(iterator.hasNext())
			{
				Object ship = iterator.next();
				
				session.merge(ship);
				System.out.println(ship);
				session.flush();
			}
			session.close();
		}
		
	}
}
