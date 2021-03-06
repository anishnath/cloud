package servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import db.SQLLiteDBManager;
import db.UsersData;
import k8.K8Deployer;
import k8.Playground;
import k8.PlaygroundConstants;
import k8.Stack;

/**
 * Servlet implementation class DeployerServlet
 */

@WebServlet(name = "deploy", value = "/deploy")
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
		
		JSONROOT.clear();
		
		System.out.println("action" +action);
		
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
					
					for (Iterator iterator = listUser.iterator(); iterator.hasNext();) {
						UsersData usersData = (UsersData) iterator.next();
						
							 
						if(usersData.getExpose_url()!=null && usersData.getExpose_url().length()>1)
						{
							String href = "<a href=\"http://"+usersData.getExpose_url()+"\" target=\"_blank\">"+usersData.getExpose_url()+"</a>";
							//System.out.println("A===>>" + href);
							usersData.setExpose_url(href);
						}
						
						
					}
					
					
					JSONROOT.put("Result", "OK");
					JSONROOT.put("Records", listUser);

					// Convert Java Object to Json
					String jsonArray = gson.toJson(JSONROOT);

					System.out.println("Here1--");
					response.getWriter().print(jsonArray);
					return;

				}

				else if (action.equals("create") || action.equals("update")) {

					String docker_image = request.getParameter("docker_image");
					String expose_port = request.getParameter("expose_port");
					
					int sqlquota = SQLLiteDBManager.checkQuota(user_name);
					
					if(sqlquota>2)
					{
						JSONROOT.put("Result", "ERROR");
						JSONROOT.put("Message", "You Have exceeded Deployment Limit, Please Delete esisting deployment to Continue");
						String error = gson.toJson(JSONROOT);
						response.getWriter().print(error);
						return;
					}
					

					if (null == docker_image || docker_image.trim().length() == 0) {
						JSONROOT.put("Result", "ERROR");
						JSONROOT.put("Message", "Please Input a Valid Docker Image");
						String error = gson.toJson(JSONROOT);
						response.getWriter().print(error);
						return;
					}
					
					OkHttpClient client = new OkHttpClient();

					StringBuilder dockerURL = new StringBuilder().append("https://index.docker.io/v1/repositories/");

					if (!docker_image.contains("gcr.io") && !docker_image.contains("quay.io"))

					{

						if (docker_image.contains(":")) {
							String[] arr = docker_image.split(":");
							if (arr.length > 1) {
								//System.out.println(arr.length);
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

						//System.out.println(dockerURL.toString());

						Request requests = new Request.Builder().url(dockerURL.toString()).build();

						Response responses = client.newCall(requests).execute();

						int code = responses.code();

						//System.out.println("Content2 Length -->" + code);

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
					List<String> commandList =  new ArrayList<String>(3);
					List<String> conatinerArgs = new ArrayList<String>(10);
					
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

				else if (action.equals("delete")) {
					
					String docker_image = request.getParameter("docker_image");
					String expose_port = request.getParameter("expose_port");
					String id = request.getParameter("id");
					
					//System.out.println("id-->>" + id);
					SQLLiteDBManager dbManager = new SQLLiteDBManager();
					dbManager.updateDeploymentStatus(user_name, id, "DELETED");
					
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
				
				else
				{
					
					if(action.equals("openterminal")) {
						
						System.out.println("ID--> " +request.getParameter("id"));
						
						UsersData userdata = SQLLiteDBManager.GetUserData2(user_name, request.getParameter("id"));
						
						if(userdata!=null)
						{
							
							String deploymentName = userdata.getDeploymentName();
							String status = userdata.getStatus();
							
							if("DEPLOYED".equals(status) || "PROVISIONING".equals(status))
							{
								MessageDigest md = MessageDigest.getInstance("MD5");
								String ns1 = "fixedsalt" + user_name;
								byte[] hashInBytes = md.digest(ns1.getBytes(StandardCharsets.UTF_8));

								String hash = Hex.encodeHexString(hashInBytes).toLowerCase();
								
								boolean isRunning = K8Deployer.isPodRunning(hash,  deploymentName);
								
								if(isRunning){
									
									String OAUTH = Playground.getAccessToken1(hash);
									
									if(null==OAUTH || OAUTH.length()==0)
									{
										response.getWriter().print(PlaygroundConstants.ERROR_INVLIAD_OUATH);
										return;
									}
									
									String podName = K8Deployer.getPodName(hash,  deploymentName);
									
									String existingPod = (String)request.getSession().getAttribute("existingPod");
									
									System.out.println("Existing Pod " + existingPod);
									
									String master = System.getenv("MASTER");
									
									List<String> envvar = new ArrayList<>();
									envvar.add("OAUTH="+OAUTH);
									envvar.add("MASTER="+master);
									envvar.add("POD_NAME="+podName);
									envvar.add("NAMESPACE="+hash);
									
									
									if(existingPod==null)
									{
										existingPod = Playground.launchTerminalPods("playground","kubeexec", envvar, null);
										System.out.println("Final Host1" + existingPod);
										String terminalPosName  = existingPod.substring(0,existingPod.lastIndexOf("."+System.getenv("DNS")));
										request.getSession().setAttribute("existingPod", terminalPosName);
										Thread.sleep(8000);
										System.out.println("terminalPosName " +  terminalPosName);
										response.getWriter().println(existingPod);
										return;
									}
									else {
										existingPod = Playground.launchTerminalPods("playground", "kubeexec", envvar, existingPod);
										Thread.sleep(8000);
										response.getWriter().println(existingPod);
										System.out.println("Final Host2  " + existingPod);
										return;
										
									}
									

									
									
									
								}else {
									response.getWriter().println(PlaygroundConstants.ERROR_POD_NOTRUNNING);
								}
								
							}
							
							
						}
						else {
							response.getWriter().println(PlaygroundConstants.ERROR_INVLIAD_DEPLOYMENT);
						}
						
						return;
						
					}
					
					
					int sqlquota = SQLLiteDBManager.checkQuota(user_name);
					
					if(sqlquota>2)
					{
						JSONROOT.put("Result", "ERROR");
						JSONROOT.put("Message", "You Have exceeded Deployment Limit, Please Delete esisting deployment to Continue");
						String error = gson.toJson(JSONROOT);
						response.getWriter().print(error);
						return;
					}
					
					
//					if(action.equals("mysql")) {
//					
//					Stack stack = new Stack();
//					stack.launchMySQL(user_name);
//					UsersData  userdata1=  SQLLiteDBManager.GetUserData(user_name);
//					
//					JSONROOT.put("Result", "OK");
//					JSONROOT.put("Record", userdata1);
//
//					// Convert Java Object to Json
//					String jsonArray = gson.toJson(JSONROOT);
//
//					response.getWriter().print(jsonArray);
//					
//					return;
//					
//				}
					
					if(action.equals("nginx")) {
						
						Stack stack = new Stack();
						stack.launchNginx(user_name);
						return;
						
					}
					
					if(action.equals("tomcat")) {
						
						Stack stack = new Stack();
						stack.launchTomcat(user_name);
						return;
						
					}
					
					if(action.equals("haproxy")) {
						Stack stack = new Stack();
						stack.launchHAPROXY(user_name);
						return;
						
					}
					
					if(action.equals("wordpress")) {
						
						DeployerInitiater deployerInitiater = new DeployerInitiater();
						DeployerListner deployerListner = new DeployerProvisioning();
						
						deployerInitiater.registerDeployerListner(deployerListner, user_name);
						
						deployerInitiater.performStackProvisioing();
						
						
						
						try{
						Thread.sleep(10000);
						}catch(Exception ex) {}
						

						
						return;
						
						
					}
					
				if(action.equals("joomla")) {
						
						DeployerInitiater deployerInitiater = new DeployerInitiater();
						DeployerListner deployerListner = new DeployerProvisioning();
						
						deployerInitiater.registerDeployerListner(deployerListner, user_name);
						
						deployerInitiater.performJoomlaProvisioning();
						
						
						
						try{
						Thread.sleep(10000);
						}catch(Exception ex) {}
						

						
						return;
						
						
					}
					
					
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
