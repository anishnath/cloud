package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import db.SQLLiteDBManager;
import db.UsersDataSQL;
import mysql.MYSQLDBManager;

@WebServlet("/MYSQLServlet")
public class MYSQLServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> JSONROOT = new HashMap<String, Object>();
	
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MYSQLServlet() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String user_name = (String)request.getSession().getAttribute("user_name");
		
		String action = request.getParameter("action");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		if(null == user_name || user_name.trim().length()==0)
		{
			JSONROOT.put("Result", "ERROR");
			JSONROOT.put("Message", "Please Login First to perform this Operation");
			String error = gson.toJson(JSONROOT);
			response.getWriter().print(error);
			return;
		}
		
		if (action != null) {
			try {

				response.setContentType("application/json");

				if (action.equals("list")) {
					
					List<UsersDataSQL> listUser = SQLLiteDBManager.ListUserDataSQL(user_name);
					
					JSONROOT.put("Result", "OK");
					JSONROOT.put("Records", listUser);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);

					response.getWriter().print(jsonArray);

					
				}
				
				if (action.equals("create")) {
					
					String dbusername = RandomStringUtils.randomAlphabetic(10).toLowerCase();
					String dbname = RandomStringUtils.randomAlphabetic(10).toLowerCase();
					String password = RandomStringUtils.randomAscii(16);
					
					int sqlquota = SQLLiteDBManager.checkQuotaSQL(user_name);
					
					if(sqlquota>6)
					{
						JSONROOT.put("Result", "ERROR");
						JSONROOT.put("Message", "You Have exceeded Database limit quota");
						String error = gson.toJson(JSONROOT);
						response.getWriter().print(error);
						return;
					}
					
					UsersDataSQL usersDataSQL = new UsersDataSQL();
					usersDataSQL.setDbname(dbname);
					usersDataSQL.setDbusername(dbusername);
					usersDataSQL.setPassword(password);
					usersDataSQL.setUsername(user_name);
					
					SQLLiteDBManager.inserUserDataSQL(usersDataSQL);
					
					MYSQLDBManager.createDB(dbusername, password, dbname);
					
					UsersDataSQL  userdata1= SQLLiteDBManager.GetUserDataLASTRecord(user_name);
					
					JSONROOT.put("Result", "OK");
					JSONROOT.put("Record", userdata1);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);

					response.getWriter().print(jsonArray);
					
					return;
					
					
				}
				
				if (action.equals("delete")) {
					
					String id = request.getParameter("id");
					SQLLiteDBManager.updateSQLStatus(user_name, id,"INACTIVE");
					UsersDataSQL  userdata1=  SQLLiteDBManager.GetUserSQLData2(user_name, id);
					
					if(userdata1.getDbname()!=null)
					{
						MYSQLDBManager.dropDB(userdata1.getDbname(),userdata1.getUsername());
					}
					
					JSONROOT.put("Result", "OK");
					JSONROOT.put("Record", userdata1);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);

					response.getWriter().print(jsonArray);
					
					return;
					
				}
				
			}catch (Exception ex)
			{
				ex.printStackTrace();
				JSONROOT.put("Result", "ERROR");
				JSONROOT.put("Message", ex.getMessage());
				String error = gson.toJson(JSONROOT);
				response.getWriter().print(error);
			}
		}
		
		
	}

}
