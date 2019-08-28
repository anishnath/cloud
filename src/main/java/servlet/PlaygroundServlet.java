package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import k8.Playground;

/**
 * Servlet implementation class PlaygroundServlet
 */

@WebServlet(name = "playground", value = "/playground")
public class PlaygroundServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlaygroundServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getSession().setAttribute("csrf_token", request.getSession().getId());
		 RequestDispatcher dispatcher = getServletContext()
			      .getRequestDispatcher("/playground.jsp");
			    dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		String csrf_token = request.getParameter("csrf_token");
		
		System.out.println("action -->" + action);
		System.out.println("csrf_token -->" + csrf_token);
		 PrintWriter out = response.getWriter();
		
		if(null == csrf_token || csrf_token.length()==0)
		{
			out.println("<font size=\"4\" color=\"red\"> Invalid CSRF Token</font>");
            return;
		}
            
        HttpSession session = request.getSession();
        String sessionID = session.getId();
        
        System.out.println("sessionID from POST" + sessionID);
        System.out.println("sessionID from FORM" + csrf_token);
        
        if(!sessionID.equalsIgnoreCase(csrf_token))
        {
        	out.println("<font size=\"4\" color=\"red\"> CSRF Token Expired  Login Again to continue</font>");
            return;
        }
        
        
        try {
        	
        	if(request.getSession().getAttribute(action)!=null)
        	{
        		String host = (String)request.getSession().getAttribute(action);
        		System.out.println("Host from Session" + host);
        		out.println(host);
        	}
        	else{
        		String host = Playground.launchPlaygroundPods("playground",action);
        		
        		Thread.sleep(5000);
        		
    			request.getSession().setAttribute(action, host);
    			out.println(host);
        	}
			return;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("<font size=\"4\" color=\"red\"> " +e +" </font>");
		}
        
        
        
				
	}

}
