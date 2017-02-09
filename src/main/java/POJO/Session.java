package POJO;

public class Session {

	String id;
	String user;
	long expireTime;
	
	
	public Session (String id, String user, long expireTime){
		this.id=id;
		this.user=user;
		this.expireTime =expireTime;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public long getExpireTime() {
		return expireTime;
	}


	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}
}
