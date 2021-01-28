package model;

import java.util.TreeMap;

public class Data {
	private static TreeMap<String, User> users;
	
	
	public Data() {
		users = new TreeMap<String, User>();
	}


	public static TreeMap<String, User> getUsers() {
		return users;
	}


	public static void setUsers(TreeMap<String, User> users) {
		Data.users = users;
	}
}
