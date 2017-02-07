package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	String user;
	String pass;
	String salt;
	
	public User (@JsonProperty("user")String user, @JsonProperty("pass")String pass,@JsonProperty("salt") String  salt){
		this.user = user;
		this.pass= pass;
		this.salt = salt;
	
	}
	
	
	public String getUser(){
		return user;
	}
	
	public void setUser(String user){
		this.user=user;
	}
	
	public String getPass(){
		return pass;
	}
	
	public String getSalt() {
		return salt;
	}


	public void setSalt(String salt) {
		this.salt = salt;
	}


	public void setPass(String pass){
		this.pass=pass;
	}
}
