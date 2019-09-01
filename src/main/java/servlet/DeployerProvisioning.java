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
import k8.K8Deployer;

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
		UsersData usersData = new UsersData();
		// final KubernetesClient client = K8Deployer.getClient();

		try {
			String MYSQL_ROOT_PASSWORD = RandomStringUtils.randomAlphanumeric(14);
			String MYSQL_DATABASE = "wordpress";
			String MYSQL_USER = "wordpress";
			String MYSQL_PASSWORD = RandomStringUtils.randomAlphanumeric(10);

			String imageName = "mysql:5.6";
			String containerPort = "3306";

			StringBuilder envStringBuilder = new StringBuilder();
			envStringBuilder.append("MYSQL_ROOT_PASSWORD=" + MYSQL_ROOT_PASSWORD);
			envStringBuilder.append("\n");
			envStringBuilder.append("MYSQL_DATABASE=" + MYSQL_DATABASE);
			envStringBuilder.append("\n");
			envStringBuilder.append("MYSQL_USER=" + MYSQL_USER);
			envStringBuilder.append("\n");
			envStringBuilder.append("MYSQL_PASSWORD=" + MYSQL_PASSWORD);
			envStringBuilder.append("\n");

			List<String> envList = new ArrayList<String>(5);
			List<String> commandList = new ArrayList<String>(3);
			List<String> conatinerArgs = new ArrayList<String>(10);

			envList.add("MYSQL_ROOT_PASSWORD=" + MYSQL_ROOT_PASSWORD);
			envList.add("MYSQL_DATABASE=" + MYSQL_DATABASE);
			envList.add("MYSQL_USER=" + MYSQL_USER);
			envList.add("MYSQL_PASSWORD=" + MYSQL_PASSWORD);

			usersData.setUsername(username);
			usersData.setDocker_image(imageName);
			usersData.setExpose_port(containerPort);
			usersData.setEnviroment_vars(envStringBuilder.toString());

			SQLLiteDBManager.inserUserData(usersData);
			
			System.out.println("Iserted Datat" + usersData.toString());
			
		

			UsersData userdata1 = SQLLiteDBManager.GetUserData(username);

			String deploymentName = doProvisioning(userdata1, envList, commandList, conatinerArgs);

			System.out.println("Starting Stack WoedPress ");
			
			Thread.sleep(3000);

			imageName = "wordpress";
			containerPort = "80";

			usersData = new UsersData();

			envStringBuilder = new StringBuilder();
			envStringBuilder.append("WORDPRESS_DB_HOST=" + deploymentName);
			envStringBuilder.append("\n");
			envStringBuilder.append("WORDPRESS_DB_USER=" + MYSQL_USER);
			envStringBuilder.append("\n");
			envStringBuilder.append("WORDPRESS_DB_PASSWORD=" + MYSQL_PASSWORD);
			envStringBuilder.append("\n");
			envStringBuilder.append("WORDPRESS_DB_NAME=" + MYSQL_DATABASE);
			envStringBuilder.append("\n");

			envList = new ArrayList<String>(5);
			commandList = new ArrayList<String>(3);
			conatinerArgs = new ArrayList<String>(10);

			envList.add("WORDPRESS_DB_HOST=" + deploymentName);
			envList.add("WORDPRESS_DB_USER=" + MYSQL_USER);
			envList.add("WORDPRESS_DB_PASSWORD=" + MYSQL_PASSWORD);
			envList.add("WORDPRESS_DB_NAME=" + MYSQL_DATABASE);

			usersData.setUsername(username);
			usersData.setDocker_image(imageName);
			usersData.setExpose_port(containerPort);
			usersData.setEnviroment_vars(envStringBuilder.toString());

			SQLLiteDBManager.inserUserData(usersData);

			userdata1 = SQLLiteDBManager.GetUserData(username);

			doProvisioning(userdata1, envList, commandList, conatinerArgs);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
