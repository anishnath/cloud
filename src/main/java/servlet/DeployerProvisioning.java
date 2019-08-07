package servlet;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import db.SQLLiteDBManager;
import db.UsersData;
import k8.K8Deployer;

public class DeployerProvisioning implements DeployerListner {
	
	
//	private static String namespace = "thisisatest1";
	
	private static final String namespace = System.getenv("NAMESPACE");
	private static final String dns = System.getenv("DNS");
	


	

	@Override
	public void doProvisioning(UsersData userdata, List<String> envList, List<String> commandList,
			List<String> conatinerArgs) {
		
		try {
			SQLLiteDBManager.updateDeploymentStatus(userdata.getUsername(), userdata.getId(), "PROVISIONING");
			
			
			String username = userdata.getUsername();
			String imagename = userdata.getDocker_image();
			String image = userdata.getDocker_image();

			int port = Integer.valueOf(userdata.getExpose_port());

			String deploymentname = RandomStringUtils.randomAlphabetic(10).toLowerCase();
			String label = RandomStringUtils.randomAlphabetic(10).toLowerCase();

			String host = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "."+ dns;

			System.out.println(deploymentname);
			System.out.println(label);
			System.out.println(host);
			
			
			
			K8Deployer.deploy(namespace, username, imagename, image, deploymentname, label, commandList, conatinerArgs, envList, host, port);
			
			
			
			System.out.println("Check Container Status in First 20 Seconds ");
			
			Thread.sleep(20*1000);
			
			boolean isRunning = K8Deployer.isPodRunning(namespace, username, imagename, image, deploymentname, label, host, port);
			
			SQLLiteDBManager.updateDeploymentInfo(deploymentname, host, username, userdata.getId(), "PROVISIONING");
			
			if(!isRunning)
			{
			
			for (int i = 0; i < 5; i++) {
				
				System.out.println("Check Container Status in Second 20 Seconds for times[ " + i + "]" + deploymentname + " " + username);
				
				Thread.sleep(20*1000);
				
				K8Deployer.isPodRunning(namespace, username, imagename, image, deploymentname, label, host, port);
				
				if(isRunning)
				{
					SQLLiteDBManager.updateDeploymentStatus(userdata.getUsername(), userdata.getId(), "DEPLOYED");
					break;
				}
			}	
			
			}
			
			else{
				SQLLiteDBManager.updateDeploymentStatus(userdata.getUsername(), userdata.getId(), "DEPLOYED");
			}
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	@Override
	public void deleteDeployment(UsersData userdata, String id) {
		
		try {
			UsersData user = SQLLiteDBManager.GetUserData2(userdata.getUsername(), id);
			
			System.out.println("user.getDeploymentName() --" + user.getDeploymentName());
			
			if(user.getDeploymentName()!=null)
			{
			
			K8Deployer.deleteDeployment(namespace, user.getDeploymentName());
			
			SQLLiteDBManager.updateDeploymentStatus(user.getUsername(), user.getId(), "PURGED");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
