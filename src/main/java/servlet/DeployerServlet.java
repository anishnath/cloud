package servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import db.SQLLiteDBManager;
import db.UsersData;

/**
 * Servlet implementation class DeployerServlet
 */
@WebServlet("/DeployerServlet")
public class DeployerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Object> JSONROOT = new HashMap<String, Object>();
	
	
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeployerServlet() {
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String user_name = (String)request.getSession().getAttribute("user_name");
		

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

					List<UsersData> listUser = SQLLiteDBManager.ListUserData(user_name);
					JSONROOT.put("Result", "OK");
					JSONROOT.put("Records", listUser);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);

					response.getWriter().print(jsonArray);

				}

				if (action.equals("create") || action.equals("update")) {

					String docker_image = request.getParameter("docker_image");
					String expose_port = request.getParameter("expose_port");

					if (null == docker_image || docker_image.trim().length() == 0) {
						JSONROOT.put("Result", "ERROR");
						JSONROOT.put("Message", "Please Input a Valid Docker Image");
						String error = gson.toJson(JSONROOT);
						response.getWriter().print(error);
						return;
					}
					
					OkHttpClient client = new OkHttpClient();

					StringBuilder dockerURL = new StringBuilder().append("https://index.docker.io/v1/repositories/");

					if (!docker_image.contains("gcr.io"))

					{

						if (docker_image.contains(":")) {
							String[] arr = docker_image.split(":");
							if (arr.length > 1) {
								System.out.println(arr.length);
								dockerURL.append(arr[0]);
								dockerURL.append("/tags");
								dockerURL.append("/");
								dockerURL.append(arr[1]);
							}
						} else {
							dockerURL.append(docker_image);
							dockerURL.append("/tags");
							dockerURL.append("/");
							dockerURL.append("latest");
						}

						System.out.println(dockerURL.toString());

						Request requests = new Request.Builder().url(dockerURL.toString()).build();

						Response responses = client.newCall(requests).execute();

						int code = responses.code();

						System.out.println("Content2 Length -->" + code);

						responses.body().close();

						if (code != 200) {
							JSONROOT.put("Result", "ERROR");
							JSONROOT.put("Message", "Please Provide a valid Docker Image reigtry to pull");
							String error = gson.toJson(JSONROOT);
							response.getWriter().print(error);

						}

					}
					

					if (null == expose_port || expose_port.trim().length() == 0) {
						JSONROOT.put("Result", "ERROR");
						JSONROOT.put("Message", "Please Input a Valid Container Port");
						String error = gson.toJson(JSONROOT);
						response.getWriter().print(error);
						return;
					}

					try {
						Integer.parseInt(expose_port);
					} catch (NumberFormatException e) {
						JSONROOT.put("Result", "ERROR");
						JSONROOT.put("Message", "Container Port MUST Integer");
						String error = gson.toJson(JSONROOT);
						response.getWriter().print(error);
						return;
					}
					
					UsersData usersData = new UsersData();
					
					String enviroment_vars = request.getParameter("enviroment_vars");
					
					List<String> envList = new ArrayList<String>(5);
					List<String> commandList =  new ArrayList<>(3);
					List<String> conatinerArgs = new ArrayList<>(10);
					
					if(enviroment_vars!=null && enviroment_vars.length()>0)
					{
					
						usersData.setEnviroment_vars(enviroment_vars);
						Properties properties = new Properties();
						InputStream is = new ByteArrayInputStream(enviroment_vars.getBytes());
						properties.load(is);
						for(String key : properties.stringPropertyNames()) {
							  String value = properties.getProperty(key);
							  envList.add(key + "=" + value);
							  
							}
						
					}
					
					
					
					String container_command = request.getParameter("container_command");
					
					if(container_command!=null && container_command.trim().length()>0)
					{
						usersData.setContainer_command(container_command);
						commandList.add(container_command);
					}
					
					
					
					String args = request.getParameter("args");
					
					if(args!=null && args.length()>1)
					{
						usersData.setArgs(args);
						String[] tmp = args.split(",");
						
						for (int i = 0; i < tmp.length; i++) {
							conatinerArgs.add(tmp[i]);
						}
								
					}
					
					
					

					
					usersData.setUsername(user_name);
					usersData.setDocker_image(docker_image);
					usersData.setExpose_port(expose_port);
					
					
					SQLLiteDBManager.inserUserData(usersData);
					
					
					UsersData  userdata1=  SQLLiteDBManager.GetUserData(user_name);
					
					
					
					
					DeployerInitiater deployerInitiater = new DeployerInitiater();
					DeployerListner deployerListner = new DeployerProvisioning();
					
					deployerInitiater.registerDeployerListner(deployerListner, userdata1,envList,commandList,conatinerArgs);
					deployerInitiater.performProvisioing();
					

					JSONROOT.put("Result", "OK");
					JSONROOT.put("Record", userdata1);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);

					response.getWriter().print(jsonArray);
					
					return;
					
					

				}

				if (action.equals("delete")) {
					
					String docker_image = request.getParameter("docker_image");
					String expose_port = request.getParameter("expose_port");
					String id = request.getParameter("id");
					
					System.out.println("id-->>" + id);
					
					SQLLiteDBManager.updateDeploymentStatus(user_name, id, "DELETED");
					
					UsersData  userdata1=  SQLLiteDBManager.GetUserData(user_name);
					
					DeployerInitiater deployerInitiater = new DeployerInitiater();
					DeployerListner deployerListner = new DeployerProvisioning();
					
					deployerInitiater.registerDeployerListner(deployerListner, userdata1,id);
					
					deployerInitiater.deleteProvisioing();
					
					
					JSONROOT.put("Result", "OK");
					JSONROOT.put("Record", userdata1);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);

					response.getWriter().print(jsonArray);
					
					return;

				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JSONROOT.put("Result", "ERROR");
				JSONROOT.put("Message", ex.getMessage());
				String error = gson.toJson(JSONROOT);
				response.getWriter().print(error);
			}
		}

	}

}
