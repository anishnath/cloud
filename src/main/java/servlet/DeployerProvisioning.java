package servlet;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;

import db.SQLLiteDBManager;
import db.UsersData;
import db.UsersDataSQL;
import k8.K8Deployer;
import mysql.MYSQLDBManager;

public class DeployerProvisioning implements DeployerListner {

	// private static String namespace = "thisisatest1";

	private static final String dns = System.getenv("DNS");

	@Override
	public String doProvisioning(UsersData userdata, List<String> envList, List<String> commandList,
			List<String> conatinerArgs) {

		String deploymentname = RandomStringUtils.randomAlphabetic(10).toLowerCase();
		SQLLiteDBManager dbManager = new SQLLiteDBManager();

		try {
			dbManager.updateDeploymentStatus(userdata.getUsername(), userdata.getId(), "PROVISIONING");

			String username = userdata.getUsername();
			String imagename = userdata.getDocker_image();
			String image = userdata.getDocker_image();

			int port = Integer.valueOf(userdata.getExpose_port());

			String label = RandomStringUtils.randomAlphabetic(10).toLowerCase();

			String host = deploymentname + "." + dns;

			System.out.println(deploymentname);
			System.out.println(label);
			System.out.println(host);

			MessageDigest md = MessageDigest.getInstance("MD5");
			String ns1 = "fixedsalt" + username;
			byte[] hashInBytes = md.digest(ns1.getBytes(StandardCharsets.UTF_8));

			String hash = Hex.encodeHexString(hashInBytes).toLowerCase();

			K8Deployer.deploy(hash, username, imagename, image, deploymentname, label, commandList, conatinerArgs,
					envList, host, port);

			System.out.println("Check Container Status in First 20 Seconds ");

			Thread.sleep(20 * 1000);

			boolean isRunning = K8Deployer.isPodRunning(hash, username, imagename, image, deploymentname, label, host,
					port);

			SQLLiteDBManager.updateDeploymentInfo(deploymentname, host, username, userdata.getId(), "PROVISIONING");

			if (!isRunning) {

				for (int i = 0; i < 5; i++) {

					System.out.println("Check Container Status in Second 20 Seconds for times[ " + i + "]"
							+ deploymentname + " " + username);

					Thread.sleep(20 * 1000);

					K8Deployer.isPodRunning(hash, username, imagename, image, deploymentname, label, host, port);

					if (isRunning) {
						dbManager.updateDeploymentStatus(userdata.getUsername(), userdata.getId(), "DEPLOYED");
						break;
					}
				}

			}

			else {
				dbManager.updateDeploymentStatus(userdata.getUsername(), userdata.getId(), "DEPLOYED");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return deploymentname;

	}

	@Override
	public void deleteDeployment(UsersData userdata, String id) {
		SQLLiteDBManager dbManager = new SQLLiteDBManager();
		try {
			UsersData user = SQLLiteDBManager.GetUserData2(userdata.getUsername(), id);

			System.out.println("user.getDeploymentName() --" + user.getDeploymentName());

			if (user.getDeploymentName() != null) {

				MessageDigest md = MessageDigest.getInstance("MD5");
				String ns1 = "fixedsalt" + user.getUsername();
				byte[] hashInBytes = md.digest(ns1.getBytes(StandardCharsets.UTF_8));

				String hash = Hex.encodeHexString(hashInBytes).toLowerCase();
				K8Deployer.deleteDeployment(hash, user.getDeploymentName());

				dbManager.updateDeploymentStatus(user.getUsername(), user.getId(), "PURGED");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void doProvisioningWordPress(String username) {

		System.out.println("Inside doProvisioningWordPress ");
		
		

		try {
			
//			String MYSQL_ROOT_PASSWORD = RandomStringUtils.randomAlphanumeric(14);
//			String MYSQL_DATABASE = "wordpress";
//			String MYSQL_USER = "wordpress";
//			String MYSQL_PASSWORD = RandomStringUtils.randomAlphanumeric(10);
//
//			String imageName = "mysql:5.6";
//			String containerPort = "3306";
//
//			StringBuilder envStringBuilder = new StringBuilder();
//			envStringBuilder.append("MYSQL_ROOT_PASSWORD=" + MYSQL_ROOT_PASSWORD);
//			envStringBuilder.append("\n");
//			envStringBuilder.append("MYSQL_DATABASE=" + MYSQL_DATABASE);
//			envStringBuilder.append("\n");
//			envStringBuilder.append("MYSQL_USER=" + MYSQL_USER);
//			envStringBuilder.append("\n");
//			envStringBuilder.append("MYSQL_PASSWORD=" + MYSQL_PASSWORD);
//			envStringBuilder.append("\n");
//
//			List<String> envList = new ArrayList<String>(5);
//			List<String> commandList = new ArrayList<String>(3);
//			List<String> conatinerArgs = new ArrayList<String>(10);
//
//			envList.add("MYSQL_ROOT_PASSWORD=" + MYSQL_ROOT_PASSWORD);
//			envList.add("MYSQL_DATABASE=" + MYSQL_DATABASE);
//			envList.add("MYSQL_USER=" + MYSQL_USER);
//			envList.add("MYSQL_PASSWORD=" + MYSQL_PASSWORD);
//
//			usersData.setUsername(username);
//			usersData.setDocker_image(imageName);
//			usersData.setExpose_port(containerPort);
//			usersData.setEnviroment_vars(envStringBuilder.toString());
//
//			SQLLiteDBManager.inserUserData(usersData);
//			
//			System.out.println("Iserted Datat" + usersData.toString());
			
			//First We need to Add External Service 
			
			/**
			 * kind: Service
apiVersion: v1
metadata:
  name: mysql
  namespace: playground
spec:
  type: ExternalName
  externalName: mysql.default.svc.cluster.local
  ports:
  - port: 3306
			 */
			
			
			
			
		
			String dbusername = RandomStringUtils.randomAlphabetic(10).toLowerCase();
			String dbname = "wordpress_"+RandomStringUtils.randomAlphabetic(10).toLowerCase();
			String password = RandomStringUtils.randomAscii(16);
			
			
			
			
			
			UsersDataSQL usersDataSQL = new UsersDataSQL();
			usersDataSQL.setDbname(dbname);
			usersDataSQL.setDbusername(dbusername);
			usersDataSQL.setPassword(password);
			usersDataSQL.setUsername(username);
			
			SQLLiteDBManager.inserUserDataSQL(usersDataSQL);
			
			MYSQLDBManager.createDB(dbusername, password, dbname);
			
			UsersDataSQL  userdata1= SQLLiteDBManager.GetUserDataLASTRecord(username);
			


			System.out.println("Starting Stack WoedPress ");
			

			String imageName = "wordpress";
			String containerPort = "80";

			UsersData usersData = new UsersData();

			StringBuilder envStringBuilder = new StringBuilder();
			envStringBuilder.append("WORDPRESS_DB_HOST=" + "mysql");
			envStringBuilder.append("\n");
			envStringBuilder.append("WORDPRESS_DB_USER=" + userdata1.getDbusername());
			envStringBuilder.append("\n");
			envStringBuilder.append("WORDPRESS_DB_PASSWORD=" + userdata1.getPassword());
			envStringBuilder.append("\n");
			envStringBuilder.append("WORDPRESS_DB_NAME=" + userdata1.getDbname());
			envStringBuilder.append("\n");
			
			List<String> envList = new ArrayList<String>(5);
			List<String> commandList = new ArrayList<String>(3);
			List<String> conatinerArgs = new ArrayList<String>(10);


			envList.add("WORDPRESS_DB_HOST=" + "mysql");
			envList.add("WORDPRESS_DB_USER=" + dbusername);
			envList.add("WORDPRESS_DB_PASSWORD=" + password);
			envList.add("WORDPRESS_DB_NAME=" + dbname);

			usersData.setUsername(username);
			usersData.setDocker_image(imageName);
			usersData.setExpose_port(containerPort);
			usersData.setEnviroment_vars(envStringBuilder.toString());

			SQLLiteDBManager.inserUserData(usersData);

			UsersData userdata2 = SQLLiteDBManager.GetUserData(username);

			doProvisioning(userdata2, envList, commandList, conatinerArgs);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
