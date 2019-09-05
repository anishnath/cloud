package servlet;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.apache.commons.codec.binary.Hex;

import db.SQLLiteConnectionManager;
import db.SQLLiteDBManager;
import db.UsersData;
import k8.K8Deployer;

/**
 * Application Lifecycle Listener implementation class LoaderListner
 *
 */
@WebServlet(value = "/THISSHOULDNOTCALLEDBYANYONE", loadOnStartup = 1)
public class LoaderServlet extends HttpServlet {

	private static final String namespace = System.getenv("NAMESPACE");
	private static final String dns = System.getenv("DNS");
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2704666358088618980L;

	public void init(ServletConfig config) {
		
		
		
		SQLLiteConnectionManager.createNewDatabase();
		System.out.println("Yes i have Created ");

		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				while (true) {

					long timeToSleep = 10L;

					// Create a TimeUnit object
					TimeUnit time = TimeUnit.MINUTES;

					System.out.println("Going to sleep for " + timeToSleep + " seconds");

					// using sleep() method
					try {
						
						System.out.println("Purging the Deployements");
						
						List<UsersData> usersdata =  SQLLiteDBManager.getPurgeRecord();
						
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
							
							java.sql.Timestamp t2 = java.sql.Timestamp.valueOf(timestap);
							
							long milliseconds =  timestamp1.getTime() - t2.getTime() ;
						    int seconds = (int) milliseconds / 1000;
						 
						    // calculate hours minutes and seconds
						    int hours = seconds / 3600;
						    int minutes = (seconds % 3600) / 60;
						    seconds = (seconds % 3600) % 60;
						 
						 
						    System.out.println("timestamp1: " + timestamp1);
						    System.out.println("timestamp2: " + t2);
						 
						    System.out.println("Difference: ");
						    System.out.println(" Hours: " + hours);
						    System.out.println(" Minutes: " + minutes);
						    System.out.println(" Seconds: " + seconds);
						    
						    
						    if(minutes>30)
						    {
						    	if(usersData2.getDeploymentName()!=null)
								{
								
						    		MessageDigest md = MessageDigest.getInstance("MD5");
									String ns1 = "fixedsalt" + usersData2.getUsername();
									byte[] hashInBytes = md.digest(ns1.getBytes(StandardCharsets.UTF_8));

									String hash = Hex.encodeHexString(hashInBytes).toLowerCase();
									
									System.out.println("Deleting Deployment in NS( " + hash + ")" + " Name: " + usersData2.getDeploymentName());
						    		
									K8Deployer.deleteDeployment(hash, usersData2.getDeploymentName());
									SQLLiteDBManager dbManager = new SQLLiteDBManager();
									dbManager.updateDeploymentStatus(usersData2.getUsername(), usersData2.getId(), "PURGED");
								}
						    }
						    
						    
						}
						
						
						
						time.sleep(timeToSleep);

						System.out.println("Slept for " + timeToSleep + " seconds");

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});
		t.start();
	}

}
