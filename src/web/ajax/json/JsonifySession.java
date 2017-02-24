package web.ajax.json;

import org.hibernate.Session;
import org.json.simple.JSONObject;

import persistence.Persistence;
import persistence.table.entity.Part;
import persistence.table.entity.User;
import spring.Spring;

import javax.servlet.http.HttpSession;

public class JsonifySession implements JSONMarshaller<HttpSession>
{
	protected JSONMarshaller<User> userMarshaller;
	protected JSONMarshaller<Part> partMarshaller;
	public JsonifySession (JSONMarshaller<User> userMarshaller,JSONMarshaller<Part> partMarshaller)
	{
		this.userMarshaller = userMarshaller;
		this.partMarshaller = partMarshaller;
	}
	@Override
	public JSONObject marshal(HttpSession session)
	{
		// TODO Auto-generated method stub
		JSONObject wrapperObject = null;
		
		JSONObject userObject = new JSONObject();
		
		int userId = -1;
		userId =  (int) session.getAttribute("user");
		
		if(userId != -1)
		{
			Persistence persistence = (Persistence) Spring.getPersistence().getBean("Persistence");
			
			Session dbsession = persistence.getSessionFactory().openSession();
			
			User user = (User) dbsession.createQuery("from User u where u.id = '" + userId + "'").getSingleResult();
			
			wrapperObject = userMarshaller.marshal(user);
						
			dbsession.close();
		}
		return wrapperObject;
	}

	
}
