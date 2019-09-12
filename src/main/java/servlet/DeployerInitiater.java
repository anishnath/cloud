package servlet;

import java.util.List;

import db.UsersData;

public class DeployerInitiater {

	private DeployerListner deployerListner;
	private UsersData usersData;
	private String id;
	private String userName;
	private List<String> envList;
	private List<String> commandList;
	private List<String> conatinerArgs;

	public void registerDeployerListner(DeployerListner mListener,UsersData usersData, List<String> envList, List<String> commandList, List<String> conatinerArgs) {
		this.deployerListner = mListener;
		this.usersData = usersData;
		this.envList=envList;
		this.commandList =commandList;
		this.conatinerArgs = conatinerArgs;
	}
	
	
	public void registerDeployerListner(DeployerListner mListener,UsersData usersData, String id)
	{
		this.deployerListner = mListener;
		this.usersData = usersData;
		this.id =id;
	}
	
	public void registerDeployerListner(DeployerListner mListener,String username)
	{
		this.deployerListner = mListener;
		this.userName = username;
		
	}
	


	public void performProvisioing() {

		// An Async task always executes in new thread
		new Thread(new Runnable() {
			public void run() {

				// perform any operation
				System.out.println("Performing operation in Asynchronous Task");

				// check if listener is registered.
				if (deployerListner != null) {

					// invoke the callback method of class A
					deployerListner.doProvisioning(usersData,envList,commandList,conatinerArgs);
				}
			}
		}).start();
	}
	
	
	public void performStackProvisioing() {

		// An Async task always executes in new thread
		new Thread(new Runnable() {
			public void run() {

				// perform any operation
				System.out.println("Performing Stack operation in Asynchronous Task");

				// check if listener is registered.
				if (deployerListner != null) {

					// invoke the callback method of class A
					deployerListner.doProvisioningWordPress(userName);
				}
			}
		}).start();
	}
	
	public void performJoomlaProvisioning() {

		// An Async task always executes in new thread
		new Thread(new Runnable() {
			public void run() {

				// perform any operation
				System.out.println("Performing Joomla operation in Asynchronous Task");

				// check if listener is registered.
				if (deployerListner != null) {

					// invoke the callback method of class A
					deployerListner.doProvisioningJoomla(userName);
				}
			}
		}).start();
	}
	
	public void deleteProvisioing() {

		// An Async task always executes in new thread
		new Thread(new Runnable() {
			public void run() {

				// perform any operation
				System.out.println("Performing operation in Asynchronous Task");

				// check if listener is registered.
				if (deployerListner != null) {

					// invoke the callback method of class A
					deployerListner.deleteDeployment(usersData,id);
				}
			}
		}).start();
	}

}
