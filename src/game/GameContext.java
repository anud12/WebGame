package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import persistence.Persistence;
import persistence.table.entity.Ship;
import persistence.table.entity.Tables;
import spring.Spring;
import test.game.ShipUpdate;

public class GameContext
{
	protected HashMap<Integer, FrameIteration> map;
	protected ExecutorService executor;
	
	public HashMap<Integer, FrameIteration> getMap()
	{
		return map;
	}

	public void setMap(HashMap<Integer, FrameIteration> map)
	{
		this.map = map;
	}



	public GameContext()
	{
		map = new HashMap<Integer, FrameIteration>();
		executor = Executors.newCachedThreadPool();
	}

	
	
	public void init()
	{
		Persistence persistence = (Persistence) Spring.getPersistence().getBean("Persistence");
		
		Iterator<Ship> shipIterator = Tables.getShips().values().iterator();
		
		while(shipIterator.hasNext())
		{
			Ship ship = shipIterator.next();
			
			ShipUpdate action = new ShipUpdate(ship.getId());
			
			map.put(ship.getId(), action);
		}
	}
	
	public void update(double deltaTime)
	{
		Iterator<Integer> iterator = map.keySet().iterator();
		
		List<Future<FrameIteration>> frame = new ArrayList<Future<FrameIteration>>();
		
		while(iterator.hasNext())
		{
			Integer integer = iterator.next();
			
			FrameIteration frameIteration = map.get(integer);
			
			frameIteration.setDeltaTime(deltaTime);
			
			frame.add(executor.submit(frameIteration));
			
		}
		Iterator<Future<FrameIteration>> futureIteration = frame.iterator();
		while(futureIteration.hasNext())
		{
			Future<FrameIteration> future = futureIteration.next();
			
			try
			{
				future.get().commit();
			}
			catch (InterruptedException | ExecutionException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
	}
}
