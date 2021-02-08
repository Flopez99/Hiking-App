package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.TreeMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

public class User implements Serializable {
	
	private SimpleStringProperty userName;
	private SimpleStringProperty fName;
	private SimpleStringProperty lName;
	private SimpleStringProperty address;
	private SimpleStringProperty phone;
	private boolean isActive;
	private Role role;
	private String password;
	private LinkedList<Trail> history;
	private TreeMap<String, Trail> historyMap;
	private String profilePicture;

	private LinkedList<Trail> currentTrail;

	public User(String fName, String lName, String pNumber, String userName, String password, Role role) {
		this.userName = new SimpleStringProperty(userName);
		this.fName = new SimpleStringProperty(fName);
		this.lName = new SimpleStringProperty(lName);
		this.phone = new SimpleStringProperty(pNumber);
		this.role = role;
		this.password = password;
		isActive = true;
		this.history = history;
		currentTrail = new LinkedList<Trail>();
		profilePicture = null;
	}

	public String getUserName() {
		return userName.get();
	}

	public void setUserName(SimpleStringProperty userName) {
		this.userName = userName;
	}

	public String getFName() {
		return fName.get();
	}

	public void setfName(SimpleStringProperty fName) {
		this.fName = fName;
	}

	public String getLName() {
		return lName.get();
	}

	public void setlName(SimpleStringProperty lName) {
		this.lName = lName;
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(SimpleStringProperty address) {
		this.address = address;
	}

	public String getPhone() {
		return phone.get();
	}

	public void setPhone(SimpleStringProperty phone) {
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

	public LinkedList<Trail> getHistory() {
		return history;
	}

	public void setHistory(LinkedList<Trail> history) {
		this.history = history;
	}

	public TreeMap<String, Trail> getHistoryMap() {
		return historyMap;
	}

	public void setHistoryMap(TreeMap<String, Trail> historyMap) {
		this.historyMap = historyMap;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public LinkedList<Trail> getCurrentTrail() {
		return currentTrail;
	}

	public void setCurrentTrail(LinkedList<Trail> currentTrail) {
		this.currentTrail = currentTrail;
	}

	@Override
	public String toString() {
		return "User [userName=" + userName + ", fName=" + fName + ", lName=" + lName + ", phone=" + phone
				+ ", isActive=" + isActive + ", role=" + role + ", password=" + password + "]";
	}

	
}
