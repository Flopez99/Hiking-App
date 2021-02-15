package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Utilities {

	public static Name[] emitName() {
		String firstNameFileName = "RawData/firstName.txt";
		String lastNameFileName = "RawData/lastName.txt";

		File firstNameFile = new File(firstNameFileName);
		File lastNameFile = new File(lastNameFileName);
		Scanner scanner1 = null;
		Scanner scanner2 = null;

		try {
			scanner1 = new Scanner(firstNameFile);
			scanner2 = new Scanner(lastNameFile);

		} catch (FileNotFoundException e) {
			System.out.println("ERROR:");
			System.out.println("Could not find file path.");
		}

		String[] firstNameArr = new String[2000];
		String[] lastNameArr = new String[2000];

		int i = 0;
		while (scanner1.hasNextLine() && scanner2.hasNextLine()) {
			firstNameArr[i] = scanner1.nextLine();
			lastNameArr[i] = scanner2.nextLine();
			i++;
		}

		Name[] nameArr = new Name[30001];
		int j = 0;
		while (j < nameArr.length) {
			Name name = new Name(firstNameArr[(int) (Math.random() * 2000)], lastNameArr[(int) (Math.random() * 2000)]);
			nameArr[j++] = name;

		}
		scanner1.close();
		scanner2.close();

		return nameArr;
	}

	public static void getTrailName() {
		String fileName = "c://lines.txt";
		List<String> list = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

			// br returns as stream and convert it into a List
			list = br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}

		list.forEach(System.out::println);

	}
	
	public static void fillTrailMap(HashSet<Trail> trails) {
		int index = 0;
		Random rand = new Random();
		Difficulty[] difficulty = {Difficulty.EASY,Difficulty.MODERATE,Difficulty.HARD};
		Type[] type = {Type.LOOP, Type.OUT_AND_BACK, Type.POINT_TO_POINT};
		
		
		while(index <= 1000) {
		String trailName = "Trail " + index;
		String address = "Address " + index;
		double distance = rand.nextDouble() * 1000;
		double eGain = rand.nextDouble() * 1000;
		
		Trail newTrail = new Trail(trailName, address, distance, eGain, type[rand.nextInt(3)], difficulty[rand.nextInt(3)]);
		System.out.println(newTrail);
		trails.add(newTrail);
		index++;
		}
	}

	public static void fillUserMap(TreeMap<String, User> userTree) {
		int index = 0;
		Random rand = new Random();
		Name[] names = emitName();
		String[] phoneNumbers = makePhoneNumbers();
		String userName = "";

		while (index <= 1000) {

			String firstName = names[rand.nextInt(names.length)].getFirstName();
			String lastName = names[rand.nextInt(names.length)].getLastName();
			String phoneNumber = phoneNumbers[rand.nextInt(phoneNumbers.length)];
			userName = firstName + lastName.charAt(0) + phoneNumber.substring(1);

			User user = new User(firstName, lastName, phoneNumber, userName, "123", Role.USER);

			userTree.put(userName, user);

			index++;
		}
		System.out.println("Done");
		userTree.entrySet().forEach(entry -> {
			// System.out.println(entry.getKey() + " " + entry.getValue());
		});
	}

	public static String[] makePhoneNumbers() {

		String[] phoneNumbers = new String[500];
		Random rand = new Random();
		String phoneNumber = "";

		for (int i = 0; i < phoneNumbers.length; i++) {
			int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
			int num2 = rand.nextInt(743);
			int num3 = rand.nextInt(10000);

			DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
			DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

			phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);

			phoneNumbers[i] = phoneNumber;

		}
		return phoneNumbers;
	}
	public static void writeBinary(Bag bag) throws IOException {
		FileOutputStream dos = new FileOutputStream("RawData/data.dat");
		ObjectOutputStream oos = new ObjectOutputStream(dos);
		oos.writeObject(bag);
		oos.close();

		System.out.println("Done writing!");
	}
	public static void readBinary(TreeMap<String, User> users,HashSet<Trail> trails ) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream("RawData/data.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Bag b = (Bag)(ois.readObject());

		//System.out.println(b.getUsers());
		users = b.getUsers();
		trails = b.getTrails();
		ois.close();


	}

}
