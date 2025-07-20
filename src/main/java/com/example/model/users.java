package com.example.model;

import java.io.Serializable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Scope("prototype")
@Component
public class users implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int UserID;
	private String Name;
	private BigInteger mobile;
	private String email;
	private String UserName;
	private String Password;
	private String profilePictureUrl;
	private boolean twoFactorEnabled;
	private String notificationPreference; // e.g., "email", "sms", "both"
	@ElementCollection(fetch = jakarta.persistence.FetchType.EAGER)
	private List<String> auditLogs = new ArrayList<>();
	public int getUserID() {
		return UserID;
	}
	public int setUserID() {	
	/////	
		
		return getUserID()+1;
		
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public BigInteger getMobile() {
		return mobile;
	}
	public void setMobile(BigInteger mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getProfilePictureUrl() { return profilePictureUrl; }
	public void setProfilePictureUrl(String url) { this.profilePictureUrl = url; }
	public boolean isTwoFactorEnabled() { return twoFactorEnabled; }
	public void setTwoFactorEnabled(boolean enabled) { this.twoFactorEnabled = enabled; }
	public String getNotificationPreference() { return notificationPreference; }
	public void setNotificationPreference(String pref) { this.notificationPreference = pref; }
	public List<String> getAuditLogs() { return auditLogs; }
	public void setAuditLogs(List<String> logs) { this.auditLogs = logs; }
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public users(int userID, String name, BigInteger mobile, String email, String userName, String password) {
		super();
		UserID = userID;
		Name = name;
		this.mobile = mobile;
		this.email = email;
		UserName = userName;
		Password = password;
	}
	public users() {
		System.out.println("User object called");

	}
	

	
}
