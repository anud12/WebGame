package web.ajax.json;

import org.json.simple.JSONObject;

public interface JSONMarshaller<T>
{
	public JSONObject marshal(T object);
}
