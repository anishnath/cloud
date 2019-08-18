package servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;

import db.SQLLiteDBManager;
import db.Users;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {

	private static final Collection<String> SCOPES = Arrays.asList("email","profile");
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	
	private static final String GOOGLE_CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
	private static final String GOOGLE_CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");
	private static final String REDIRECT_URL = System.getenv("REDIRECT_URL");

	
	
	private GoogleAuthorizationCodeFlow flow;
	
	private AuthorizationCodeFlow authflow;

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String provider = (String)req.getParameter("provider");
		
		if("google".equalsIgnoreCase(provider))
		{
		
		String state = new BigInteger(130, new SecureRandom()).toString(32);  // prevent request forgery
	    req.getSession().setAttribute("state", state);

	    if (req.getAttribute("loginDestination") != null) {
	      req
	          .getSession()
	          .setAttribute("loginDestination", (String) req.getAttribute("loginDestination"));
	    } else {
	      req.getSession().setAttribute("loginDestination", "add.jsp");
	    }

	    
	   
	    flow = new GoogleAuthorizationCodeFlow.Builder(
	        HTTP_TRANSPORT,
	        JSON_FACTORY,
	        GOOGLE_CLIENT_ID, // CLIENT-ID
	        GOOGLE_CLIENT_SECRET,    // CLIENT-SECRET
	        SCOPES)
	        .build();

	    // Callback url should be the one registered in Google Developers Console
	    String url =
	        flow.newAuthorizationUrl()
	            .setRedirectUri(REDIRECT_URL)
	            .setState(state)            // Prevent request forgery
	            .build();
	    resp.sendRedirect(url);
	    
		}
		
		if("system".equalsIgnoreCase(provider)) {
			
			
			String user_name = req.getParameter("email");
				String password = req.getParameter("password");

				if (null == user_name || user_name.trim().length() == 0) {
					req.setAttribute("error", "Email Is Empty ");
					req.getRequestDispatcher("login.jsp").forward(req, resp);
					return;
				}

				if (!isValid(user_name)) {
					req.setAttribute("error", "Email is not valid");
					req.getRequestDispatcher("login.jsp").forward(req, resp);
					return;
				}

				if (null == password || password.length() == 0) {
					req.setAttribute("error", "Password Is Empty ");
					req.getRequestDispatcher("login.jsp").forward(req, resp);
					return;

				}

				user_name = user_name.trim();

				Users users = new Users();
				users.setPassword(password);
				users.setUsername(user_name);
				;

				try {
					boolean count = SQLLiteDBManager.verifyLogin(users);

					System.out.println("count --> " + count);

					if (!count) {
						req.setAttribute("error", "Invalid Login");
						req.getRequestDispatcher("login.jsp").forward(req, resp);
						return;
					}

				} catch (Exception e) {

					req.setAttribute("error", "Invalid Login ");
					req.getRequestDispatcher("login.jsp").forward(req, resp);
					return;

				}


				HttpSession oldSession = req.getSession(false);
				if (oldSession != null) {
					oldSession.invalidate(); 
				}

				HttpSession currentSession = req.getSession(); 
				currentSession.setAttribute("user_name", user_name);
				currentSession.setMaxInactiveInterval(5 * 60); 
																

				resp.sendRedirect("add.jsp"); 
				
				

			}
			
		}
	    
	 
	    
	    /**
	    
	    authflow = new AuthorizationCodeFlow.Builder(BearerToken
	            .authorizationHeaderAccessMethod(),
	            HTTP_TRANSPORT,
	            JSON_FACTORY,
	            new GenericUrl("https://github.com/login/oauth/access_token"),
	            new ClientParametersAuthentication(
	                TwitterOAuth2ClientCredentials.API_KEY, TwitterOAuth2ClientCredentials.API_SECRET),
	            TwitterOAuth2ClientCredentials.API_KEY,
	            "https://github.com/login/oauth/authorize")
	    		.setScopes(SCOPES)
	            .build();
	    
	    
	    String url =
	    		authflow.newAuthorizationUrl()
		            .setRedirectUri("http://localhost:8082/ZeroCloud/githuboauth2callback")
		            .setState(state)            // Prevent request forgery
		            .build();
		    resp.sendRedirect(url);
		    
		    **/
	    
	    
	  
	
	


	

	public static boolean isValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

}
