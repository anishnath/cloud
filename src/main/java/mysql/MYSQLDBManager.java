package mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class MYSQLDBManager {
	
public static void createDB(String username, String password, String dbName) throws Exception {
		
		Connection conn = MySQLConnectionManager.getInstance().getConnection();
		
		try {
			String sql = "CREATE USER ?@'%' IDENTIFIED BY ?;";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.executeUpdate();
			MySQLConnectionManager.getInstance().close();
		} catch (Exception e) {
			//Ignore This Exception Users Already Exists 
		}
		
		conn = MySQLConnectionManager.getInstance().getConnection();
		
		String sql = "CREATE DATABASE "+dbName;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		//pstmt.setString(1, dbName);
		pstmt.execute();
		
		sql ="GRANT ALL PRIVILEGES ON " +  dbName + " . * TO '"+username+"'@'%';";
		pstmt = conn.prepareStatement(sql);
		pstmt.execute();
		
		MySQLConnectionManager.getInstance().close();
		
	}


public static void dropDB(String dbName,String username) throws Exception {
	
	Connection conn = MySQLConnectionManager.getInstance().getConnection();
	
	
	try {
		String sql = "DELETE USER ?@'%';";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, username);
		pstmt.executeUpdate();
		MySQLConnectionManager.getInstance().close();
	} catch (Exception e) {
		//Ignore This Exception Users Already Exists 
	}
	

	conn = MySQLConnectionManager.getInstance().getConnection();
	
	String sql = "DROP DATABASE "+dbName;
	PreparedStatement pstmt = conn.prepareStatement(sql);
	//pstmt.setString(1, dbName);
	pstmt.execute();
	
	MySQLConnectionManager.getInstance().close();
	
}


public static void main(String[] args) {
	
	try {
		createDB("ABCD","password","dbname");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

}
