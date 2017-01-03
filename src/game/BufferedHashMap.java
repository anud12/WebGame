package game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BufferedHashMap<T,V> implements Map<T,V>
{
	
	protected HashMap<T,V> readMap;
	protected HashMap<T,V> writeMap;
	
	public BufferedHashMap()
	{
		readMap = new HashMap<>();
		writeMap = new HashMap<>();
	}
	
	public void swap()
	{
		HashMap<T,V> aux = readMap;
		readMap = writeMap;
		writeMap = aux;
	}
	
	@Override
	public void clear()
	{
		writeMap.clear();
	}

	@Override
	public boolean containsKey(Object arg0)
	{
		return readMap.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0)
	{
		return readMap.containsValue(arg0);
	}

	@Override
	public Set<java.util.Map.Entry<T,V>> entrySet()
	{
		return readMap.entrySet();
	}

	@Override
	public V get(Object arg0)
	{
		return readMap.get(arg0);
	}

	@Override
	public boolean isEmpty()
	{
		return readMap.isEmpty();
	}

	@Override
	public Set<T> keySet()
	{		
		return readMap.keySet();
	}

	@Override
	public V put(T arg0, V arg1)
	{
		return writeMap.put(arg0, arg1);
	}

	@Override
	public void putAll(Map<? extends T, ? extends V> arg0)
	{
		writeMap.putAll(arg0);
	}

	@Override
	public V remove(Object arg0)
	{
		return writeMap.remove(arg0);
	}

	@Override
	public int size()
	{
		return readMap.size();
	}

	@Override
	public Collection<V> values()
	{
		return readMap.values();
	}
	
	
}
