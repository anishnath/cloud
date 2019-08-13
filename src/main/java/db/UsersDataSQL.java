package db;

import java.io.Serializable;

public class UsersDataSQL implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2620376524335699534L;
	
	private String id;
	private String username;
	private String dbname;
	private String dbusername;
	private String password;
	private String Timestamp;
	private String status;
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
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getDbusername() {
		return dbusername;
	}
	public void setDbusername(String dbusername) {
		this.dbusername = dbusername;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTimestamp() {
		return Timestamp;
	}
	public void setTimestamp(String timestamp) {
		Timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Timestamp == null) ? 0 : Timestamp.hashCode());
		result = prime * result + ((dbname == null) ? 0 : dbname.hashCode());
		result = prime * result + ((dbusername == null) ? 0 : dbusername.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		UsersDataSQL other = (UsersDataSQL) obj;
		if (Timestamp == null) {
			if (other.Timestamp != null)
				return false;
		} else if (!Timestamp.equals(other.Timestamp))
			return false;
		if (dbname == null) {
			if (other.dbname != null)
				return false;
		} else if (!dbname.equals(other.dbname))
			return false;
		if (dbusername == null) {
			if (other.dbusername != null)
				return false;
		} else if (!dbusername.equals(other.dbusername))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
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
		return "UsersDataSQL [id=" + id + ", username=" + username + ", dbname=" + dbname + ", dbusername=" + dbusername
				+ ", password=" + password + ", Timestamp=" + Timestamp + ", status=" + status + "]";
	}
	
	
	
	
	
	
	
	
}
