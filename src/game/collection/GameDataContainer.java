package game.collection;

import java.util.HashMap;
import java.util.Map;

import game.GameContext;
import persistence.Persistence;

public class GameDataContainer extends HashMap<String, Map<Integer, Object>>
{
	protected Persistence persistence;
	public GameDataContainer(GameContext context)
	{
		this.put("Ship", new ShipCollection(context, this));
		this.put("Part", new HashMap<Integer,Object>());
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
		((ShipCollection) this.get("Ship")).swap();
	}
}
