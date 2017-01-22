package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import game.collection.GameDataContainer;

public class GameContext
{
	protected HashMap<Object, FrameIteration> commands;
	protected ExecutorService executor;
	protected GameDataContainer dataContainer;
	
	public HashMap<Object, FrameIteration> getCommands()
	{
		return commands;
	}

	public void setCommands(HashMap<Object, FrameIteration> map)
	{
		this.commands = map;
	}
	
	
	public GameDataContainer getDataContainer()
	{
		return dataContainer;
	}

	public void setDataContainer(GameDataContainer dataContainer)
	{
		this.dataContainer = dataContainer;
	}

	public GameContext()
	{
		commands = new HashMap<Object, FrameIteration>();
		executor = Executors.newCachedThreadPool();
	}

	public void init()
	{
		
	}
	
	public void update(int deltaTimeMS)
	{
		System.out.println(this + " : Update");
		Iterator<Object> iterator = commands.keySet().iterator();
		
		List<Future<FrameIteration>> frame = new ArrayList<Future<FrameIteration>>();
		
		while(iterator.hasNext())
		{
			Object key = iterator.next();
			
			FrameIteration frameIteration = commands.get(key);
			
			frameIteration.setDeltaTimeMS(deltaTimeMS);
			
			frame.add(executor.submit(frameIteration));
			
		}
		Iterator<Future<FrameIteration>> futureIteration = frame.iterator();
		while(futureIteration.hasNext())
		{
			Future<FrameIteration> future = futureIteration.next();
			
			try
			{
				future.get();
			}
			catch (InterruptedException | ExecutionException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 		}
		dataContainer.swap();
	}
}
