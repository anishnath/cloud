package db;

import java.io.Serializable;

public class UsersData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2620376526335699534L;
	
	private String id;
	private String username;
	private String docker_image;
	private String expose_port;
	private String expose_url;
	private String enviroment_vars;
	private String args;
	private String container_command;
	private String deploymentName;
	private String serviceName;
	private String ingressName;
	private String labelName;
	private String status;
	private String Timestamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDocker_image() {
		return docker_image;
	}
	public void setDocker_image(String docker_image) {
		this.docker_image = docker_image;
	}
	public String getExpose_port() {
		return expose_port;
	}
	public void setExpose_port(String expose_port) {
		this.expose_port = expose_port;
	}
	public String getExpose_url() {
		return expose_url;
	}
	public void setExpose_url(String expose_url) {
		this.expose_url = expose_url;
	}
	public String getEnviroment_vars() {
		return enviroment_vars;
	}
	public void setEnviroment_vars(String enviroment_vars) {
		this.enviroment_vars = enviroment_vars;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public String getContainer_command() {
		return container_command;
	}
	public void setContainer_command(String container_command) {
		this.container_command = container_command;
	}
	public String getDeploymentName() {
		return deploymentName;
	}
	public void setDeploymentName(String deploymentName) {
		this.deploymentName = deploymentName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getIngressName() {
		return ingressName;
	}
	public void setIngressName(String ingressName) {
		this.ingressName = ingressName;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTimestamp() {
		return Timestamp;
	}
	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Timestamp == null) ? 0 : Timestamp.hashCode());
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		result = prime * result + ((container_command == null) ? 0 : container_command.hashCode());
		result = prime * result + ((deploymentName == null) ? 0 : deploymentName.hashCode());
		result = prime * result + ((docker_image == null) ? 0 : docker_image.hashCode());
		result = prime * result + ((enviroment_vars == null) ? 0 : enviroment_vars.hashCode());
		result = prime * result + ((expose_port == null) ? 0 : expose_port.hashCode());
		result = prime * result + ((expose_url == null) ? 0 : expose_url.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ingressName == null) ? 0 : ingressName.hashCode());
		result = prime * result + ((labelName == null) ? 0 : labelName.hashCode());
		result = prime * result + ((serviceName == null) ? 0 : serviceName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsersData other = (UsersData) obj;
		if (Timestamp == null) {
			if (other.Timestamp != null)
				return false;
		} else if (!Timestamp.equals(other.Timestamp))
			return false;
		if (args == null) {
			if (other.args != null)
				return false;
		} else if (!args.equals(other.args))
			return false;
		if (container_command == null) {
			if (other.container_command != null)
				return false;
		} else if (!container_command.equals(other.container_command))
			return false;
		if (deploymentName == null) {
			if (other.deploymentName != null)
				return false;
		} else if (!deploymentName.equals(other.deploymentName))
			return false;
		if (docker_image == null) {
			if (other.docker_image != null)
				return false;
		} else if (!docker_image.equals(other.docker_image))
			return false;
		if (enviroment_vars == null) {
			if (other.enviroment_vars != null)
				return false;
		} else if (!enviroment_vars.equals(other.enviroment_vars))
			return false;
		if (expose_port == null) {
			if (other.expose_port != null)
				return false;
		} else if (!expose_port.equals(other.expose_port))
			return false;
		if (expose_url == null) {
			if (other.expose_url != null)
				return false;
		} else if (!expose_url.equals(other.expose_url))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ingressName == null) {
			if (other.ingressName != null)
				return false;
		} else if (!ingressName.equals(other.ingressName))
			return false;
		if (labelName == null) {
			if (other.labelName != null)
				return false;
		} else if (!labelName.equals(other.labelName))
			return false;
		if (serviceName == null) {
			if (other.serviceName != null)
				return false;
		} else if (!serviceName.equals(other.serviceName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UsersData [id=" + id + ", username=" + username + ", docker_image=" + docker_image + ", expose_port="
				+ expose_port + ", expose_url=" + expose_url + ", enviroment_vars=" + enviroment_vars + ", args=" + args
				+ ", container_command=" + container_command + ", deploymentName=" + deploymentName + ", serviceName="
				+ serviceName + ", ingressName=" + ingressName + ", labelName=" + labelName + ", status=" + status
				+ ", Timestamp=" + Timestamp + "]";
	}
	
	
	
}
