package k8;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import db.SQLLiteDBManager;
import db.UsersData;
import io.fabric8.kubernetes.client.KubernetesClient;
import servlet.DeployerInitiater;
import servlet.DeployerListner;
import servlet.DeployerProvisioning;

public class Stack {
	
	public  void launchMySQL(String username) throws Exception
	{
	
		UsersData usersData = new UsersData();
		//final KubernetesClient client = K8Deployer.getClient();
		
		String MYSQL_ROOT_PASSWORD= RandomStringUtils.randomAlphanumeric(14);
		String MYSQL_DATABASE="mysql";
		String MYSQL_USER="mysql";
		String MYSQL_PASSWORD=RandomStringUtils.randomAlphanumeric(10);
		
		String imageName="mysql:5.6";
		String containerPort="3306";
		
		
		StringBuilder envStringBuilder = new StringBuilder();
		envStringBuilder.append("MYSQL_ROOT_PASSWORD="+MYSQL_ROOT_PASSWORD);
		envStringBuilder.append("\n");
		envStringBuilder.append("MYSQL_DATABASE="+MYSQL_DATABASE);
		envStringBuilder.append("\n");
		envStringBuilder.append("MYSQL_USER="+MYSQL_USER);
		envStringBuilder.append("\n");
		envStringBuilder.append("MYSQL_PASSWORD="+MYSQL_PASSWORD);
		envStringBuilder.append("\n");
		
		List<String> envList = new ArrayList<String>(5);
		List<String> commandList =  new ArrayList<String>(3);
		List<String> conatinerArgs = new ArrayList<String>(10);
		
		envList.add("MYSQL_ROOT_PASSWORD="+MYSQL_ROOT_PASSWORD);
		envList.add("MYSQL_DATABASE="+MYSQL_DATABASE);
		envList.add("MYSQL_USER="+MYSQL_USER);
		envList.add("MYSQL_PASSWORD="+MYSQL_PASSWORD);
		
		
		
		usersData.setUsername(username);
		usersData.setDocker_image(imageName);
		usersData.setExpose_port(containerPort);
		usersData.setEnviroment_vars(envStringBuilder.toString());
		
		
		SQLLiteDBManager.inserUserData(usersData);
		
		UsersData  userdata1=  SQLLiteDBManager.GetUserData(username);
		
		DeployerInitiater deployerInitiater = new DeployerInitiater();
		DeployerListner deployerListner = new DeployerProvisioning();
		
		deployerInitiater.registerDeployerListner(deployerListner, userdata1,envList,commandList,conatinerArgs);
		deployerInitiater.performProvisioing();
		
		
	}
	
	public  void launchWordPress(String username) throws Exception
	{
		
		
		
		
		
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Stack stack = new Stack();
		stack.launchMySQL("test");
		
	}

}
