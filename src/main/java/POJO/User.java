package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	String user;
	String pass;
	
	public User (@JsonProperty("user")String user, @JsonProperty("pass")String pass){
		this.user = user;
		this.pass= pass;
	
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
	
	public void setPass(String pass){
		this.pass=pass;
	}
}
