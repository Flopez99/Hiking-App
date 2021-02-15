package model;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

public class Data {
	private static TreeMap<String, User> users;
	private static HashSet<Trail> trails;
	private static User loggedUser;
	private static Trail pickedTrail;
	private static Hike pickedHike;
	private static User pickedUser;

	public Data() throws ClassNotFoundException, IOException {
		users = new TreeMap<String, User>();
		// users.put("user", new User("Lorenzo", "Malo", "123465234", "user",
		// "password", Role.ADMIN));
		// users.put("1", new User("Lorenzo", "Malo", "123465234", "user", "password",
		// Role.ADMIN));
//
		trails = new HashSet<Trail>(100000);
		// trails.add(new Trail("Rosh Pit", "1711 Avalon Pines Dr", 50, 60, Type.LOOP,
		// Difficulty.HARD));
//		
//		System.out.println(trails);
//		loggedUser = users.get("user");
//		loggedUser.setProfilePicture("Pictures/defaultUser.jpg");
//		
//		LinkedList<String> picturesTaken = new LinkedList<String>();
//		picturesTaken.add("Pictures/defaultUser.jpg");
//		picturesTaken.add("Pictures/Emma.jpg");
//		
//		Hike hike = new Hike("name", "dateTimeStart","dateTimeEnd", "hikingDistance + ", "duration + ","hikeAveragePace + ",picturesTaken);
//		
		// loggedUser.getHistoryMap().add(hike);

		pickedTrail = null;
		// pickedHike = loggedUser.getHistoryMap().get(0);
	}

	public static User getPickedUser() {
		return pickedUser;
	}

	public static void setPickedUser(User pickedUser) {
		Data.pickedUser = pickedUser;
	}

	public static Hike getPickedHike() {
		return pickedHike;
	}

	public static void setPickedHike(Hike pickedHike) {
		Data.pickedHike = pickedHike;
	}

	public static Trail getPickedTrail() {
		return pickedTrail;
	}

	public static void setPickedTrail(Trail pickedTrail) {
		Data.pickedTrail = pickedTrail;
	}

	public static HashSet<Trail> getTrails() {
		return trails;
	}

	public static void setTrails(HashSet<Trail> trails) {
		Data.trails = trails;
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
