package model;

import java.util.TreeMap;

public class Data {
	private static TreeMap<String, User> users;
	private static User loggedUser;

	public Data() {
		users = new TreeMap<String, User>();
		loggedUser = null;
	}

	
	public static User getLoggedUser() {
		return loggedUser;
	}

	public static void setLoggedUser(User loggedUser) {
		Data.loggedUser = loggedUser;
	}

	public static TreeMap<String, User> getUsers() {
		return users;
	}

	public static void setUsers(TreeMap<String, User> users) {
		Data.users = users;
	}
}
