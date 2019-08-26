package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Servlet implementation class PollServlet
 */
@WebServlet(name = "poll", value = "/poll",asyncSupported = true)
public class PollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String dns = System.getenv("DNS");

	

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PollServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		String host = request.getParameter("host");
		
		System.out.println("Host-- " + host);
		
		
		if(null==host || host.length()==0)
			return;
		
		if(host.endsWith(dns))
		{
			String csrf_token = request.getParameter("csrf_token");
			HttpSession session = request.getSession();
	        String sessionID = session.getId();
	        if(!sessionID.equalsIgnoreCase(csrf_token))
	        {
//	        	out.println("<font size=\"4\" color=\"red\"> CSRF Token Expired  Login Again to continue</font>");
//	            return;
	        }
			
//	        AsyncContext asyncContext = request.startAsync(); 
//	        
//	        asyncContext.start(new Runnable() {
//	            @Override
//	            public void run () {
//	                int msg = poll(host);
//	                out.println(msg);
//	                asyncContext.complete();
//	            }
//	        });
	        
	        int msg = poll(host);
	        out.println(msg);
	        
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private int poll (String host) {
		
		int code=404;
		
		try {
			boolean isLive = false;
			
			for (int i = 0; i < 15; i++) { // Max 15 Try 
				OkHttpClient client = new OkHttpClient();
				Request requests = new Request.Builder().url("https://"+host).build();

				Response responses = client.newCall(requests).execute();

				code = responses.code();

				System.out.println("Code -- >" + code);

				responses.body().close();

				if (code == 200) {
					
					isLive=true;
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return code;
		
    }
	

}
