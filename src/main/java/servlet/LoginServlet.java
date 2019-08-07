package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.SQLLiteDBManager;
import db.Users;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String user_name = req.getParameter("email");
        String password = req.getParameter("password");
		
        
        if(null == user_name || user_name.trim().length()==0)
        {
        	req.setAttribute("error", "Email Is Empty ");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        
        if(!isValid(user_name))
        {
        	req.setAttribute("error", "Email is not valid");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        
        if(null == password || password.length()==0)
        {
        	req.setAttribute("error", "Password Is Empty ");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
            
        }
        
        user_name = user_name.trim();
        
        Users users = new Users();
        users.setPassword(password);
        users.setUsername(user_name);;

        
        try {
			boolean count = SQLLiteDBManager.verifyLogin(users);
			
			System.out.println("count --> " + count);
			
			if(!count)
			{
				req.setAttribute("error", "Invalid Login");
	            req.getRequestDispatcher("login.jsp").forward(req, resp);
	            return;
			}
			
		} catch (Exception e) {

	        	req.setAttribute("error", "Invalid Login ");
	            req.getRequestDispatcher("login.jsp").forward(req, resp);
	            return;

		} 
        
		// TODO Auto-generated method stub
		HttpSession oldSession = req.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate(); //invalida la sessione se esiste
        }
        
        HttpSession currentSession = req.getSession(); //crea una nuova sessione
        currentSession.setAttribute("user_name", user_name);
        currentSession.setMaxInactiveInterval(5*60); //5 minuti di inattività massima
        
        resp.sendRedirect("add.jsp"); //vai alla pagina success.jsp
        
	}
	
	
	public static boolean isValid(String email) 
    { 
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        return pat.matcher(email).matches(); 
    } 

}
