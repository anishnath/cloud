package db;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.RandomStringUtils;

public class SQLLiteConnectionManager {
	
	private static final String dburl = System.getenv("DBFILE");
	
	private static SQLLiteConnectionManager instance;
    private Connection connection;
    private String url = "jdbc:sqlite:/opt/zerocloud/users.db";
    private String username = "root";
    private String password = "localhost";
    
    
    private SQLLiteConnectionManager()  {
        try {
        	Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(dburl);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed1 : " + ex.getMessage());
        } catch (SQLException e) {
			// TODO Auto-generated catch block
        	System.out.println("Database Connection Creation Failed2 : " + e.getMessage());
		}
    }
    
    private SQLLiteConnectionManager(String url)  {
        try {
        	Class.forName("org.sqlite.JDBC");
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

    public static SQLLiteConnectionManager getInstance()  {
        if (instance == null) {
            instance = new SQLLiteConnectionManager();
        } else
			try {
				if (instance.getConnection().isClosed()) {
				    instance = new SQLLiteConnectionManager();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Database Connection Creation Failed5 : " + e.getMessage());
			}

        return instance;
    }
	
    
    public static SQLLiteConnectionManager getInstance(String url) {
    	
    	//System.out.println("URL --> " + url);
    	
        if (instance == null) {
            instance = new SQLLiteConnectionManager(url);
        } else
			try {
				if (instance.getConnection().isClosed()) {
				    instance = new SQLLiteConnectionManager(url);
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
		 
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			
		}
 
		System.out.println("Successfully Slept for 10 Seconds ");
        try (Connection conn = DriverManager.getConnection(dburl)) {
            if (conn != null) {
            	Class.forName("org.sqlite.JDBC");
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (Exception e) {
        	
            System.out.println(e.getMessage());
        }
        
        
        Connection c = null;
        Statement stmt = null;
        String sql = "";
        try {
           Class.forName("org.sqlite.JDBC");
           
           c = DriverManager.getConnection(dburl);
           System.out.println("Opened database successfully");
           System.out.println("Going to Create users Table");

           stmt = c.createStatement();
           sql = "CREATE TABLE users (\n" +
                   "    id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                   "    username NVARCHAR(120)  NULL,\n" +
                   "    password NVARCHAR(120)  NULL,\n" +
                   "    quota INTEGER DEFAULT 3,\n" +
                   "    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP\n" +
                   ");";
           
           System.out.println("SQL Query users " + sql);
           
           stmt.executeUpdate(sql);
           stmt.close();
           c.close();
           
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         }
        finally {
			try {
				c.close();
			} catch (SQLException e) {
				System.err.println( "Failed to Close Connection while creating user table: " + e.getMessage() );
			}
		}
        
        try{
       
        	 c = DriverManager.getConnection(dburl);
        stmt = c.createStatement();

           
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
           
           System.out.println("SQL Query users_data " + sql);
           stmt.executeUpdate(sql);
           stmt.close();
           c.close();
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           
        }
        finally {
			try {
				c.close();
			} catch (SQLException e) {
				System.err.println( "Failed to Close Connection while creating user table: " + e.getMessage() );
			}
		}
        
        try{
        	
        	 c = DriverManager.getConnection(dburl);

           // stmt.executeUpdate(sql);
        	stmt = c.createStatement();
            sql = "CREATE TABLE users_data_sql (\n" +
                    "    id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                    "    username NVARCHAR(120) NOT  NULL,\n" +
                    "    dbusername NVARCHAR(120) NOT  NULL,\n" +
                    "    dbname NVARCHAR(50) NOT  NULL,\n" +
                    "    password  NVARCHAR(40)   NULL,\n" +
                    "    quota INTEGER DEFAULT 6,\n" +
                    "    status  NVARCHAR(40)  DEFAULT ACTIVE,\n" +
                    "    Timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                    "    FOREIGN KEY(id) REFERENCES users(username)\n" +
                    ");";
            
            System.out.println("SQL Query users_data_sql " + sql);
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
         } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            
         }
        
        System.out.println("Table created successfully");
        
        System.out.println("Creating Sample Users created successfully");
        
        try {
			Users users = new Users();
			users.setUsername("system@localhost.com");
			users.setPassword(RandomStringUtils.randomAlphabetic(10).toLowerCase());
			
			boolean isUserexists = SQLLiteDBManager.checkUser(users);
			
			if(!isUserexists)
			{
				SQLLiteDBManager.insertUser(users);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

	
	public static void main(String[] args) {
		//createNewDatabase("jdbc:sqlite:/Users/aninath/Downloads/zerocloud/users.db");
		createNewDatabase();
	}

}
