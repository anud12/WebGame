package test.web;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

import persistence.Persistence;
import persistence.table.entity.Tables;
import persistence.table.entity.User;
import spring.Spring;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession();
		String username = (String) request.getParameter("username");
		String password = (String) request.getParameter("password");
			
		
		
		Iterator<User> iterator = Tables.getUsers().values().iterator();
		
		boolean found = false;
		while(iterator.hasNext())
		{
			User user = iterator.next();
			
			if(user.getName().equals(username))
			{
				session.setAttribute("user", user);
				response.sendRedirect("something.jsp");
				
				found = true;
				break;
			}
		}
		
		if(!found)
		{
			response.sendRedirect("/HelloWorld");
		}
		
	}

}
