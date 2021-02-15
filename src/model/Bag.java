package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashSet;
import java.util.TreeMap;

public class Bag implements Serializable{

	private TreeMap<String, User> users;
	private HashSet<Trail> trails;

	public Bag(TreeMap<String, User> users, HashSet<Trail> trails) {
		super();
		this.users = users;
		this.trails = trails;

	}

	public TreeMap<String, User> getUsers() {
		return users;
	}

	public void setUsers(TreeMap<String, User> users) {
		this.users = users;
	}

	public HashSet<Trail> getTrails() {
		return trails;
	}

	public void setTrails(HashSet<Trail> trails) {
		this.trails = trails;
	}

}
