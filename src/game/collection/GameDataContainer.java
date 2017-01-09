package game.collection;

import java.util.HashMap;
import java.util.Iterator;

import game.GameContext;
import persistence.Persistence;

public class GameDataContainer extends HashMap<String, BufferedHashMap<Integer, Object>>
{
	protected Persistence persistence;
	public GameDataContainer(GameContext context)
	{
		this.put("Ship", new ShipCollection(context));
		
	}
	public Persistence getPersistence()
	{
		return persistence;
	}

	public void setPersistence(Persistence persistence)
	{
		this.persistence = persistence;
		
		ShipCollection collection = (ShipCollection) this.get("Ship");
		collection.setPersistence(persistence);
	}
	public void swap()
	{
		Iterator<BufferedHashMap<Integer, Object>> keyIterator = this.values().iterator();
		while(keyIterator.hasNext())
		{
			BufferedHashMap map = keyIterator.next();
			map.swap();
		}
	}
}
