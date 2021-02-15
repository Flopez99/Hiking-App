package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class User implements Serializable {

	private String userName;
	private String fName;
	private String lName;
	private String address;
	private String phone;
	private boolean isActive;
	private Role role;
	private String password;
	private LinkedList<Hike> historyMap;
	private String profilePicture;

	public User(String fName, String lName, String pNumber, String userName, String password, Role role) {
		this.userName = userName;
		this.fName = fName;
		this.lName = lName;
		this.phone = pNumber;
		this.role = role;
		this.password = password;
		isActive = true;
		profilePicture = null;
		historyMap = new LinkedList<Hike>();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getLName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LinkedList<Hike> getHistoryMap() {
		return historyMap;
	}

	public void setHistoryMap(LinkedList<Hike> historyMap) {
		this.historyMap = historyMap;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", fName=" + fName + ", lName=" + lName + ", phone=" + phone
				+ ", isActive=" + isActive + ", role=" + role + ", password=" + password + "]";
	}

}
