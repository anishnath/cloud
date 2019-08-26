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
        
        
        try {
			String host = Playground.launchPlaygroundPods("playground",action);

			
			boolean isLive = false;
			
			for (int i = 0; i < 4; i++) {
				OkHttpClient client = new OkHttpClient();
				Request requests = new Request.Builder().url("https://"+host).build();

				Response responses = client.newCall(requests).execute();

				int code = responses.code();

				System.out.println("Code -- >" + code);

				responses.body().close();

				if (code == 200) {
					
					isLive=true;
					break;
				}
				else {
					Thread.sleep(10000);
				}
			}
			
			
        	
			if(isLive)
			{
			String s = "<div class=\"embed-responsive embed-responsive-16by9\"><iframe class=\"embed-responsive-item\" src=\"https://"+host+"\"></iframe></div>";
			out.println(s);
			}
			
			else {
				String s = "<a href=\"https://"+host+"\" target=\"_blank\">Go to Python3 Shell</a> ";
				out.println(s);
			}
			
			
			
			
			return;
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			out.println("<font size=\"4\" color=\"red\"> " +e +" </font>");
		}
        
        
        
				
	}

}
