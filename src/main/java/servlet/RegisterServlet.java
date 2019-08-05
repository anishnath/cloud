package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import db.SQLLiteDBManager;
import db.Users;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		// TODO Auto-generated method stub

		String user_name = req.getParameter("email");
        String password = req.getParameter("password");
        
 
        
        if(null == user_name || user_name.trim().length()==0)
        {
        	req.setAttribute("error", "Email Is Empty ");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        
        if(null == password || password.length()==0)
        {
        	req.setAttribute("error", "Password Is Empty ");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
            
        }
        
        Users uses = new Users();
        user_name = user_name.trim();
        uses.setUsername(user_name);
        uses.setPassword(password);
        
       try {
		boolean count =  SQLLiteDBManager.checkUser(uses);
		
		System.out.println("isUserExists " + count);
		
		if(count)
		{
			req.setAttribute("error", "Email Exists, Please Try Login ");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
		}
		
		 SQLLiteDBManager.insertUser(uses);
		 
		 
		 
		 HttpSession currentSession = req.getSession(); 
         currentSession.setAttribute("user_name", user_name);
         currentSession.setMaxInactiveInterval(10*60); //5 minuti di inattività massima
         
         System.out.println("Redirect to Dahsboard Page");
         
         resp.sendRedirect("add.jsp"); //vai alla pagina success.jsp
		 
		
	} catch (Exception e) {
		req.setAttribute("error", e.getMessage());
        req.getRequestDispatcher("login.jsp").forward(req, resp);
	} 
       
      
        

	}

}
