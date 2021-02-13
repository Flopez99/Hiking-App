package model;

import java.util.HashMap;
import java.util.TreeMap;

public class Data {
	private static TreeMap<String, User> users;
	private static HashMap<String, Trail> trails;
	private static User loggedUser;
	private static Trail pickedTrail;

	public Data() {
		users = new TreeMap<String, User>();
		users.put("user", new User("Lorenzo", "Malo", "123465234", "user", "password", Role.ADMIN));
		trails = new HashMap<String, Trail>(100000);
		trails.put("Rosh Pit",
				new Trail("Rosh Pit", "1711 Avalon Pines Dr", 12344.0, 1000, Type.LOOP, Difficulty.HARD));
		loggedUser = users.get("user");
		loggedUser.getHistoryMap().add(new Hike("name", "dateTimeStart","dateTimeEnd", "hikingDistance + ", "duration + ","hikeAveragePace + ", null));
		pickedTrail = null;
	}

	public static Trail getPickedTrail() {
		return pickedTrail;
	}

	public static void setPickedTrail(Trail pickedTrail) {
		Data.pickedTrail = pickedTrail;
	}

	public static HashMap<String, Trail> getTrails() {
		return trails;
	}

	public static void setTrails(HashMap<String, Trail> trails) {
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
