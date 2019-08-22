package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.sql.Timestamp;

public class SQLLiteDBManager {

	
	private static final String url = System.getenv("DBFILE");
	//private final static String url = "jdbc:sqlite:/Users/aninath/Downloads/zerocloud/users.db";

	 Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();

	public static void insertUser(Users user) throws ClassNotFoundException, SQLException {
		
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "INSERT INTO users (username,password) VALUES(?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
	}

	public static boolean checkUser(Users user) throws ClassNotFoundException, SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT username FROM users WHERE username=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUsername());
		ResultSet rs = pstmt.executeQuery();

		boolean isUserexists = false;
		int count = 0;
		while (rs.next()) {
			String uName = rs.getString("username");
			
			System.out.println("uName = " + uName);
			
			if (uName!=null && uName.length()>0)
			{
				isUserexists =  true;
			}
		}
		SQLLiteConnectionManager.getInstance().close();
		return isUserexists;

	}
	
	
	public static boolean verifyLogin(Users user) throws ClassNotFoundException, SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT username FROM users WHERE username=? and password=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		ResultSet rs = pstmt.executeQuery();

		boolean isUserexists = false;
		while (rs.next()) {
			String uName = rs.getString("username");
			if (uName!=null && uName.length()>0)
			{
				isUserexists =  true;
			}
				
		}
		SQLLiteConnectionManager.getInstance().close();
		return isUserexists;

	}

	public static List<Users> ListUser(String user_name) throws SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT id,username,quota,Timestamp  FROM users WHERE username=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_name);
		ResultSet rs = pstmt.executeQuery();

		List<Users> listUser = new ArrayList<>();
		int count = 0;
		while (rs.next()) {
			Users user = new Users();
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setQuota(rs.getInt("quota"));
			
			user.setTimestamp(rs.getString("Timestamp"));
			listUser.add(user);
		}
		SQLLiteConnectionManager.getInstance().close();
		return listUser;

	}
	
	
	public static List<UsersData> getPurgeRecord() throws SQLException {
		
		System.out.println("Inside Get Purged Record--");

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT id,username,docker_image,expose_port,expose_url,status,Timestamp,deployment_name,service_name,ingress_name,container_command,args,enviroment_vars  FROM users_data WHERE username not in (?) and status in (?) LIMIT 100 ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "system@localhost.com");
		pstmt.setString(2, "DEPLOYED");
		ResultSet rs = pstmt.executeQuery();
		
		System.out.println(sql);

		List<UsersData> listUser = new ArrayList<>();
		int count = 0;
		while (rs.next()) {
			UsersData user = new UsersData();
			
	
			
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setDocker_image(rs.getString("docker_image"));
			user.setExpose_port(rs.getString("expose_port"));
			user.setExpose_url(rs.getString("expose_url"));
			user.setStatus(rs.getString("status"));
			user.setTimestamp(rs.getString("Timestamp"));
			user.setDeploymentName(rs.getString("deployment_name"));
			user.setServiceName(rs.getString("service_name"));
			user.setIngressName(rs.getString("ingress_name"));
			user.setContainer_command(rs.getString("container_command"));
			user.setArgs(rs.getString("args"));
			user.setEnviroment_vars(rs.getString("enviroment_vars"));
			listUser.add(user);
		}
		SQLLiteConnectionManager.getInstance().close();
		return listUser;
	}
	
	
	public static List<UsersData> ListUserData(String user_name) throws SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT id,username,docker_image,expose_port,expose_url,status,Timestamp,deployment_name,service_name,ingress_name,container_command,args,enviroment_vars  FROM users_data WHERE username in (?,?) ORDER BY Timestamp DESC ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_name);
		pstmt.setString(2, "system@localhost.com");
		ResultSet rs = pstmt.executeQuery();

		List<UsersData> listUser = new ArrayList<>();
		int count = 0;
		while (rs.next()) {
			UsersData user = new UsersData();
			
			
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setDocker_image(rs.getString("docker_image"));
			user.setExpose_port(rs.getString("expose_port"));
			user.setExpose_url(rs.getString("expose_url"));
			user.setStatus(rs.getString("status"));
			user.setTimestamp(rs.getString("Timestamp"));
			user.setDeploymentName(rs.getString("deployment_name"));
			user.setServiceName(rs.getString("service_name"));
			user.setIngressName(rs.getString("ingress_name"));
			user.setContainer_command(rs.getString("container_command"));
			user.setArgs(rs.getString("args"));
			user.setEnviroment_vars(rs.getString("enviroment_vars"));
			listUser.add(user);
		}
		SQLLiteConnectionManager.getInstance().close();
		return listUser;
	}
	
	
	public static UsersData GetUserData(String user_name) throws SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT id,username,docker_image,expose_port,expose_url,status,Timestamp,deployment_name,service_name,ingress_name,container_command,args,enviroment_vars  FROM users_data WHERE username=? and id = (select max(id) from users_data)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_name);
		ResultSet rs = pstmt.executeQuery();

		UsersData user = new UsersData();
		int count = 0;
		while (rs.next()) {
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setDocker_image(rs.getString("docker_image"));
			user.setExpose_port(rs.getString("expose_port"));
			user.setExpose_url(rs.getString("expose_url"));
			user.setStatus(rs.getString("status"));
			user.setTimestamp(rs.getString("Timestamp"));
			user.setDeploymentName(rs.getString("deployment_name"));
			user.setServiceName(rs.getString("service_name"));
			user.setIngressName(rs.getString("ingress_name"));
			user.setContainer_command(rs.getString("container_command"));
			user.setArgs(rs.getString("args"));
			user.setEnviroment_vars(rs.getString("enviroment_vars"));
			
		}
		SQLLiteConnectionManager.getInstance().close();
		return user;
	}
	
	
	public static UsersData GetUserData2(String user_name,String id) throws SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT id,username,docker_image,expose_port,expose_url,status,Timestamp,deployment_name,service_name,ingress_name,container_command,args,enviroment_vars  FROM users_data WHERE username=? and id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_name);
		pstmt.setString(2, id);
		ResultSet rs = pstmt.executeQuery();

		UsersData user = new UsersData();
		int count = 0;
		while (rs.next()) {
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setDocker_image(rs.getString("docker_image"));
			user.setExpose_port(rs.getString("expose_port"));
			user.setExpose_url(rs.getString("expose_url"));
			user.setStatus(rs.getString("status"));
			user.setTimestamp(rs.getString("Timestamp"));
			user.setDeploymentName(rs.getString("deployment_name"));
			user.setServiceName(rs.getString("service_name"));
			user.setIngressName(rs.getString("ingress_name"));
			user.setContainer_command(rs.getString("container_command"));
			user.setArgs(rs.getString("args"));
			user.setEnviroment_vars(rs.getString("enviroment_vars"));
			
		}
		SQLLiteConnectionManager.getInstance().close();
		return user;
	}
	
	
	
	/**
	 * 
	 * @param username
	 * @param expose_url
	 * @param status is NOTDEPLOYED,DEPLOYED,TERMINATED
	 * @throws SQLException
	 */
	public static void updateDeploymentStatus(String username, String id, String status) throws SQLException {
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "UPDATE users_data SET status=? WHERE username=? and id =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, status);
		pstmt.setString(2, username);
		pstmt.setString(3, id );
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
		
	}
	
	public static void updateDeploymentInfo(String deploymentName, String host, String username, String id, String status) throws SQLException {
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "UPDATE users_data SET deployment_name=?, expose_url =? WHERE username=? and id =? and status =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, deploymentName);
		pstmt.setString(2, host);
		pstmt.setString(3, username);
		pstmt.setString(4, id);
		pstmt.setString(5, status);
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
		
	}
	
	public static int checkQuota(String username) throws ClassNotFoundException, SQLException{
		
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql	=	"SELECT COUNT(*) as count FROM users_data WHERE username=? and status in (?,?)";
		PreparedStatement pstmt	=	conn.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, "PROVISIONING");
		pstmt.setString(3, "DEPLOYED");
		ResultSet rs	=	pstmt.executeQuery();
		
		int count = 0;
		while(rs.next()){
			count = rs.getInt("count");
		}
		SQLLiteConnectionManager.getInstance().close();
		return count;
		
	}
	
	

	public static void inserUserData(UsersData userdata) throws SQLException {
		
		
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "INSERT INTO users_data (username,docker_image,expose_port,expose_url,container_command,args,enviroment_vars) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userdata.getUsername());
		pstmt.setString(2, userdata.getDocker_image());
		pstmt.setString(3, userdata.getExpose_port());
		pstmt.setString(4, userdata.getExpose_url());
		pstmt.setString(5, userdata.getContainer_command());
		pstmt.setString(6, userdata.getArgs());
		pstmt.setString(7, userdata.getEnviroment_vars());
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
	}
	
	
	public static void inserUserDataSQL(UsersDataSQL userdata) throws SQLException {
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "INSERT INTO users_data_sql (username,dbusername,dbname,password) VALUES(?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, userdata.getUsername());
		pstmt.setString(2, userdata.getDbusername());
		pstmt.setString(3, userdata.getDbname());
		pstmt.setString(4, userdata.getPassword());
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
	}
	
	
	public static List<UsersDataSQL> ListUserDataSQL(String user_name) throws SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT id,username,dbusername,dbname,password,Timestamp,status  FROM users_data_sql WHERE username in (?) ORDER BY Timestamp DESC ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_name);
		ResultSet rs = pstmt.executeQuery();

		List<UsersDataSQL> listUser = new ArrayList<>();
		int count = 0;
		while (rs.next()) {
			UsersDataSQL user = new UsersDataSQL();
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setDbusername(rs.getString("dbusername"));
			user.setDbname(rs.getString("dbname"));
			user.setPassword(rs.getString("password"));
			user.setTimestamp(rs.getString("Timestamp"));
			user.setStatus(rs.getString("status"));
			listUser.add(user);
		}
		SQLLiteConnectionManager.getInstance().close();
		return listUser;
	}
	
	
	public static UsersDataSQL GetUserDataLASTRecord(String user_name) throws SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT id,username,dbusername,dbname,password,Timestamp,status  FROM users_data_sql WHERE username=? and id = (select max(id) from users_data_sql)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_name);
		ResultSet rs = pstmt.executeQuery();

		UsersDataSQL user = new UsersDataSQL();
		int count = 0;
		while (rs.next()) {
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setDbusername(rs.getString("dbusername"));
			user.setDbname(rs.getString("dbname"));
			user.setPassword(rs.getString("password"));
			user.setTimestamp(rs.getString("Timestamp"));
			user.setStatus(rs.getString("status"));
			
		}
		SQLLiteConnectionManager.getInstance().close();
		return user;
	}
	
	
	public static UsersDataSQL GetUserSQLData2(String user_name,String id) throws SQLException {

		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "SELECT id,username,dbusername,dbname,password,Timestamp,status  FROM users_data_sql WHERE username=? and id = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, user_name);
		pstmt.setString(2, id);
		ResultSet rs = pstmt.executeQuery();

		UsersDataSQL user = new UsersDataSQL();
		int count = 0;
		while (rs.next()) {
			user.setId(rs.getString("id"));
			user.setUsername(rs.getString("username"));
			user.setDbusername(rs.getString("dbusername"));
			user.setDbname(rs.getString("dbname"));
			user.setPassword(rs.getString("password"));
			user.setTimestamp(rs.getString("Timestamp"));
			user.setStatus(rs.getString("status"));
			
		}
		SQLLiteConnectionManager.getInstance().close();
		return user;
	}
	
	public static void deleteFromUserDataSQL(String username, String id) throws SQLException {
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "DELETE FROM users_data_sql WHERE username=? and id =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, id);
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
		
	}
	
	
	public static void updateSQLStatus(String username, String id, String status) throws SQLException {
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "UPDATE users_data_sql SET status=? WHERE username=? and id =?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, status);
		pstmt.setString(2, username);
		pstmt.setString(3, id );
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
		
	}
	
	public static int checkQuotaSQL(String username) throws ClassNotFoundException, SQLException{
		
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql	=	"SELECT COUNT(*) as count FROM users_data_sql WHERE username=? and status=?";
		PreparedStatement pstmt	=	conn.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.setString(2, "ACTIVE");
		ResultSet rs	=	pstmt.executeQuery();
		
		int count = 0;
		while(rs.next()){
			count = rs.getInt("count");
		}
		SQLLiteConnectionManager.getInstance().close();
		return count;
		
	}
	
	
	
	
	
	
	public static void purgeRecord(String id, String username) throws SQLException
	{
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "DELETE FROM users";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		
		sql = "DELETE FROM users_data";
		 pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
		
		
		
	}
	
	
	
	public static void truncateTable() throws SQLException
	{
		Connection conn = SQLLiteConnectionManager.getInstance(url).getConnection();
		String sql = "DELETE FROM users";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		
		sql = "DELETE FROM users_data";
		 pstmt = conn.prepareStatement(sql);
		pstmt.executeUpdate();
		SQLLiteConnectionManager.getInstance().close();
		
		
		
	}

	public static void main(String[] args) throws Exception {
		
		//truncateTable();

		Users users = new Users();
		users.setUsername("C");
		users.setPassword("A");

		insertUser(users);
		
		boolean count = checkUser(users);
		
		//System.out.println("Count == " + count);
		
		users.setUsername("D");
		
        count = checkUser(users);
		
		//System.out.println("Count == " + count);
		
		UsersData usersData =  new UsersData();
		usersData.setUsername("A");
		
		usersData.setDocker_image("nginx:latest");
		usersData.setExpose_port("80,443");
		
		String url = UUID.randomUUID().toString().substring(0,10);
		usersData.setExpose_url(url);
		
		inserUserData(usersData);

		List<Users> x = ListUser("A");

		for (Iterator iterator = x.iterator(); iterator.hasNext();) {
			Users user = (Users) iterator.next();
			System.out.println(user);
		}
		
		List<UsersData> usersdata = ListUserData("A");
		
		for (Iterator iterator = usersdata.iterator(); iterator.hasNext();) {
			UsersData usersData2 = (UsersData) iterator.next();
			System.out.println(usersData2);
		}
		

		users.setUsername("B");
		insertUser(users);
		
		

		x = ListUser("B");

		for (Iterator iterator = x.iterator(); iterator.hasNext();) {
			Users user = (Users) iterator.next();
			System.out.println(user);
		}
		
		
		usersData =  new UsersData();
		usersData.setUsername("A");
		usersData.setDocker_image("nginx:latest");
		usersData.setExpose_port("80,443");
		
		String url1 = UUID.randomUUID().toString().substring(0,10);
		usersData.setExpose_url(url1);
		
		inserUserData(usersData);
		
		
		usersdata = ListUserData("A");
		
		for (Iterator iterator = usersdata.iterator(); iterator.hasNext();) {
			UsersData usersData2 = (UsersData) iterator.next();
			System.out.println(usersData2);
		}
		
		
		updateDeploymentStatus("A", url1, "TERMINATED");
		
		usersdata = ListUserData("A");
		
		for (Iterator iterator = usersdata.iterator(); iterator.hasNext();) {
			UsersData usersData2 = (UsersData) iterator.next();
			System.out.println(usersData2);
		}
		
		System.out.println("Quota--" + checkQuota("A"));

		
		usersdata = getPurgeRecord();
		
		for (Iterator iterator = usersdata.iterator(); iterator.hasNext();) {
			UsersData usersData2 = (UsersData) iterator.next();
			
			System.out.println("Inside Timespatp ");
			
			java.util.Date date = new java.util.Date();
		    Timestamp timestamp1 = new Timestamp(date.getTime());
		 
		     // create a calendar and assign it the same time
		    Calendar cal = Calendar.getInstance();
		    cal.setTimeInMillis(timestamp1.getTime());
		 
		    // add a bunch of seconds to the calendar 
		    cal.add(Calendar.SECOND, 98765);
			
			String timestap = usersData2.getTimestamp();
			
			java.sql.Timestamp t1 = java.sql.Timestamp.valueOf(timestap);
			
			System.out.println(t1);
		}
		
	}

}
