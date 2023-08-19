package com.example.keycloaktest.controller;

public class IdentityDTO {
	
	private String identityId;
	private String loginId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String userType;
	private String message;
	private boolean isTemporaryCredentials;
	private boolean enabled;
	private Long createdTimestamp;

	public String getIdentityId() {
		return identityId;
	}
	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isTemporaryCredentials() {
		return isTemporaryCredentials;
	}
	public void setTemporaryCredentials(boolean isTemporaryCredentials) {
		this.isTemporaryCredentials = isTemporaryCredentials;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Long getCreatedTimestamp() {
		return createdTimestamp;
	}
	public void setCreatedTimestamp(Long createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}
}
