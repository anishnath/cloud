package servlet;

import java.util.List;

import db.UsersData;

public interface DeployerListner {
	
	void doProvisioning(UsersData userdata, List<String> envList, List<String> commandList, List<String> conatinerArgs);
	void deleteDeployment(UsersData userdata,String id);

}
