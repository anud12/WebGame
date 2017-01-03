package test.spring;

import javax.servlet.annotation.WebServlet;

import org.hsqldb.server.Servlet;

@WebServlet(name = "Spring", loadOnStartup = 1)
public class TestBean extends Servlet
{
	protected String test;
	public TestBean()
	{
		test = "Test";
		System.out.println(this.toString() + " constructor finished");
	}
	public String getTest()
	{
		return test;
	}
	public void setTest(String test)
	{
		this.test = test;
	}
	
}
