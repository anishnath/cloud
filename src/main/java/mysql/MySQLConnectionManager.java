package mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLConnectionManager {
	

	
	private static final String MYSQL_ROOT_DBCONNECT_URL = System.getenv("MYSQL_ROOT_DBCONNECT_URL");
	private static final String MYSQL_ROOT_PASSWORD = System.getenv("MYSQL_ROOT_PASSWORD");
	private static final String MYSQL_ROOT_USERNAME = System.getenv("MYSQL_ROOT_USERNAME");
	
	private static MySQLConnectionManager instance;
    private Connection connection;

    
    
    private MySQLConnectionManager()  {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	

        	
            this.connection = DriverManager.getConnection(MYSQL_ROOT_DBCONNECT_URL,MYSQL_ROOT_USERNAME,MYSQL_ROOT_PASSWORD);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed1 : " + ex.getMessage());
        } catch (SQLException e) {
        	
        	e.printStackTrace();
			// TODO Auto-generated catch block
        	System.out.println("Database Connection Creation Failed2 : " + e.getMessage());
		}
    }
    
    private MySQLConnectionManager(String url)  {
        try {
        	Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed3 : " + ex.getMessage());
        } catch (SQLException e) {
        	System.out.println("Database Connection Creation Failed4 : " + e.getMessage());
			e.printStackTrace();
		}
    }

    public Connection getConnection() {
        return connection;
    }

    public static MySQLConnectionManager getInstance()  {
        if (instance == null) {
            instance = new MySQLConnectionManager();
        } else
			try {
				if (instance.getConnection().isClosed()) {
				    instance = new MySQLConnectionManager();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Database Connection Creation Failed5 : " + e.getMessage());
			}

        return instance;
    }
	
    
    public static MySQLConnectionManager getInstance(String url) {
    	
    	//System.out.println("URL --> " + url);
    	
        if (instance == null) {
            instance = new MySQLConnectionManager(url);
        } else
			try {
				if (instance.getConnection().isClosed()) {
				    instance = new MySQLConnectionManager(url);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Database Connection Creation Failed6 : " + e.getMessage());
			}

        return instance;
    }
    
    
    public void close(){
		//System.out.println("Closing Connection");
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	
	public static void createNewDatabase( ) {
		 
		
 
        try (Connection conn = DriverManager.getConnection(MYSQL_ROOT_DBCONNECT_URL,MYSQL_ROOT_USERNAME,MYSQL_ROOT_PASSWORD)) {
            if (conn != null) {
            	Class.forName("com.mysql.jdbc.Driver");
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println(e.getMessage());
        }
        
        
        Connection c = null;
        Statement stmt = null;
        String sql = "";
        try {
           Class.forName("com.mysql.jdbc.Driver");
           
           c = DriverManager.getConnection(MYSQL_ROOT_DBCONNECT_URL,MYSQL_ROOT_USERNAME,MYSQL_ROOT_PASSWORD);
           System.out.println("Opened database successfully");

           stmt = c.createStatement();
           sql = "CREATE TABLE users (\n" +
                   "    id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                   "    username NVARCHAR(120)  NULL,\n" +
                   "    password NVARCHAR(120)  NULL,\n" +
                   "    quota INTEGER DEFAULT 3,\n" +
                   "    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP\n" +
                   ");";
           
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         }
        
        try{
        	
           
           System.out.println(sql);
          // stmt.executeUpdate(sql);
           
           sql = "CREATE TABLE users_data (\n" +
                   "    id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                   "    username NVARCHAR(120) NOT  NULL,\n" +
                   "    docker_image NVARCHAR(50) NOT  NULL,\n" +
                   "    expose_port  NVARCHAR(40)   NULL,\n" +
                   "    container_command  NVARCHAR(200)   NULL,\n" +
                   "    args  NVARCHAR(200)   NULL,\n" +
                   "    enviroment_vars  NVARCHAR(200)   NULL,\n" +
                   "    expose_url  NVARCHAR(40)   NULL,\n" +
                   "    deployment_name  NVARCHAR(40)   NULL,\n" +
                   "    service_name  NVARCHAR(40)   NULL,\n" +
                   "    ingress_name  NVARCHAR(40)   NULL,\n" +
                   "    status  NVARCHAR(40)  DEFAULT NOTDEPLOYED,\n" +
                   "    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                   "    FOREIGN KEY(id) REFERENCES users(username)\n" +
                   ");";
           
           System.out.println(sql);
           stmt.executeUpdate(sql);
           stmt.close();
           c.close();
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           
        }
        
        System.out.println("Table created successfully");
        
        System.out.println("Creating Sample Users created successfully");
        
        
        
    }

	
	public static void main(String[] args) {
		//createNewDatabase("jdbc:sqlite:/Users/aninath/Downloads/zerocloud/users.db");
		createNewDatabase();
	}



}
