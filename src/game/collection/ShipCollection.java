package game.collection;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.hibernate.Session;


import game.GameContext;
import game.controller.ObjectController;
import game.controller.ship.ShipController;
import persistence.Persistence;
import persistence.table.entity.Ship;
import spring.Spring;

public class ShipCollection extends BufferedHashMap<Integer, Object>
{	
	protected GameContext gameContext;
	protected GameDataContainer dataContainer;
	protected Persistence persistence;
	protected HashMap<Integer, ObjectController> controllers;
	
	public ShipCollection(GameContext gameContext, GameDataContainer dataContainer)
	{
		this.gameContext = gameContext;
		this.dataContainer = dataContainer;
		System.out.println(this + " : Constructed");
		controllers = new HashMap<>();
	}
	
	@Override
	public Object put(Integer integer, Object ship)
	{
		Logger logger = Logger.getLogger("Ship " + integer);
		
		Object returnShip = super.put(integer, ship);
		
		Ship shipObject = (Ship) ship;
		
		if(!this.containsKey(integer))
		{
			System.out.println(this + " : Added ship " + shipObject.getId());
			createLogger(integer);
			
			ShipController controller = new ShipController(dataContainer);
			controller.setId(integer);
			
			gameContext.getObjectCommands().put(ship, controller);
			controllers.put(shipObject.getId(), controller);
			
			return returnShip;
		}
		
		logger.log(Level.FINEST, "Update ship");
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
	public GameContext getGameContext()
	{
		return gameContext;
	}
	public void setGameContext(GameContext gameContext)
	{
		this.gameContext = gameContext;
	}
	
	protected void createLogger(int shipId)
	{
		Logger logger = Logger.getLogger("Ship " + shipId);
		try
		{
			File file = new File("/log/ship/");
			System.out.println(file.getAbsolutePath());
			if(!file.exists())
			{
				
				file.mkdirs();
			}
			
			Handler handler = new FileHandler("/log/ship/" + shipId + ".log", 1073741824, 1, false);
			
			handler.setFormatter(new Formatter() {
				
				@Override
				public String format(LogRecord record)
				{
					StringBuilder builder = new StringBuilder();
					builder.append(new Date().getHours());
					builder.append(":");
					builder.append(new Date().getMinutes());
					builder.append(":");
					builder.append(new Date().getSeconds());
					
					builder.append(" "+ '\t' +" ");
					
					builder.append(record.getSourceClassName());
					builder.append(" "+ '\t' +" ");
					builder.append(record.getMessage());
					builder.append(System.lineSeparator());
					return builder.toString();
				}
			});
			handler.setLevel(Level.ALL);
			
			logger.addHandler(handler);
			logger.setLevel(Level.ALL);
		}
		catch (SecurityException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
