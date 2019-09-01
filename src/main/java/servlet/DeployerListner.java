package servlet;

import java.util.List;

import db.UsersData;

public interface DeployerListner {
	
	void doProvisioningWordPress(String  username);
	String doProvisioning(UsersData userdata, List<String> envList, List<String> commandList, List<String> conatinerArgs);
	void deleteDeployment(UsersData userdata,String id);

}
