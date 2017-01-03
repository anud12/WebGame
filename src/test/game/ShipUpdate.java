package test.game;

import org.hibernate.Session;

import game.FrameIteration;
import persistence.Persistence;
import persistence.table.entity.Ship;
import spring.Spring;

public class ShipUpdate implements FrameIteration
{
	protected int id;
	protected double deltaTime;
	protected Session dbSession;
	
	public ShipUpdate(int id)
	{
		this.id = id;
	}
	
	@Override
	public void setDeltaTime(double deltaTime)
	{
		this.deltaTime = deltaTime;
	}
	
	@Override
	public void commit()
	{
		dbSession.close();
	}

	@Override
	public Object call() throws Exception
	{
		Persistence persistence = (Persistence) Spring.getPersistence().getBean("Persistence");
		
		dbSession = persistence.getSessionFactory().openSession();
		
		dbSession.beginTransaction();
		
		Ship ship = (Ship) dbSession.createQuery("from Ship u where u.id = '" + id + "'").getSingleResult();
		
		//Ship ship = (Ship) Tables.getShips().get(id);
		
		double increment = ship.getValue();
		
		double newIncrement = increment + (ship.getRate() * (deltaTime/1000)); 
		
		if(newIncrement < ship.getArea())
		{
			increment = newIncrement;
		}
		
		ship.setValue (increment);
		
		dbSession.update(ship);
		dbSession.getTransaction().commit();
		
		System.out.println("Incremented " + newIncrement + " Area : " + ship.getArea());
		return this;
	}
	
}
