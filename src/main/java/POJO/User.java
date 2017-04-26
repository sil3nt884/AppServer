package POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	String user;
	String pass;
	String salt;
	String email;
	
	


	public User (@JsonProperty("user")String user, @JsonProperty("pass")String pass,@JsonProperty("salt") String  salt, @JsonProperty("email") String email){
		this.user = user;
		this.pass= pass;
		this.salt = salt;
		this.email=email;
	
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
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	
}
