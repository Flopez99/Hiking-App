package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import application.Main;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import model.Bag;
import model.Data;
import model.Role;
import model.SkillLevel;
import model.Trail;
import model.Type;
import model.User;
import model.Utilities;

public class LoginController implements Initializable {
	private static User loggedUser;
	private TreeMap<String, User> users;
	private HashSet<Trail> trails;

	private static boolean signingUp = false;
	private static String imagePath;
	@FXML
	private TextField usernameField, userName, phoneNumber, firstName, lastName;

	@FXML
	private PasswordField passwordField, password;
	@FXML
	private ComboBox<SkillLevel> skillLevel;

	@FXML
	private ImageView profilePicture;
	@FXML
	private Circle myCircle;

	public Bag bag;

	public void createNewUser(ActionEvent e) throws IOException {
		String uName = userName.getText();
		String fName = firstName.getText();
		String lName = lastName.getText();
		String pWord = password.getText();
		String pNumber = phoneNumber.getText();
		Role role = null;

		if (users.isEmpty()) {
			role = Role.ADMIN;
		} else {
			role = Role.USER;
		}

		User user = new User(fName, lName, pNumber, uName, pWord, role);

		if (imagePath != null) {
			user.setProfilePicture(imagePath);
		} else {
			user.setProfilePicture("Pictures/defaultUser.jpg");
		}
		users.put(uName, user);

		userName.clear();
		password.clear();
		phoneNumber.clear();
		firstName.clear();
		lastName.clear();

		changeSceneLogIn(e);
	}

	public void selectPicture(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("JPG Files", "*.jpg"));
		File selectedFile = fc.showOpenDialog(stage);

		if (selectedFile != null) {

			try {
				BufferedImage bufferedImage = ImageIO.read(selectedFile);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				myCircle.setFill(new ImagePattern(image));

				imagePath = selectedFile.getAbsolutePath();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				BufferedImage imageB = ImageIO.read(new File("Pictures/defaultUser.jpg"));
				Image image = SwingFXUtils.toFXImage(imageB, null);
				myCircle.setFill(new ImagePattern(image));
				imagePath = "Pictures/defaultUser.jpg";

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void logIn(ActionEvent event) throws IOException {
		String uName = usernameField.getText();
		String pWord = passwordField.getText();

		if (users.containsKey(uName) && users.get(uName).getPassword().equals(pWord)) {

			loggedUser = users.get(uName);
			Data.setLoggedUser(loggedUser);
			changeSceneLoggedIn(event);
		} else {
			System.out.println("Error");
		}

	}

	public void save() throws IOException {

		System.out.println(users);
		bag = new Bag(users, trails);
		bag.setUsers(users);
		bag.setTrails(trails);
		Utilities.writeBinary(bag);
		System.out.println("Program ended");

	}

	public void changeSceneLoggedIn(ActionEvent e) throws IOException {
		Parent secondRoot = null;
		if (loggedUser.getRole().equals(Role.ADMIN)) {
			secondRoot = FXMLLoader.load(getClass().getResource("/view/AdminLogged.fxml"));
		} else {
			secondRoot = FXMLLoader.load(getClass().getResource("/view/LoggedIn.fxml"));

		}
		Scene secondScene = new Scene(secondRoot);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(secondScene);
		window.show();
	}

	public void changeSceneSignUp(ActionEvent e) throws IOException {
		signingUp = true;
		Parent root = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	public void changeSceneLogIn(ActionEvent e) throws IOException {
		signingUp = false;
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	public void fillData() throws ClassNotFoundException, IOException {
		FileInputStream fis = new FileInputStream("RawData/data.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Bag b = (Bag) (ois.readObject());


		users = b.getUsers();
		trails = b.getTrails();
		ois.close();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = Data.getUsers();
		trails = Data.getTrails();


//		try {
//			fillData();
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}

		System.out.println(users);
		
		if (signingUp) {
			skillLevel.getItems().removeAll(skillLevel.getItems());
			skillLevel.getItems().addAll(SkillLevel.AMATEUR, SkillLevel.EXPERIENCED, SkillLevel.PROFESIONAL);
			BufferedImage imageB = null;
			try {
				imageB = ImageIO.read(new File("Pictures/defaultUser.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Image image = SwingFXUtils.toFXImage(imageB, null);
			myCircle.setFill(new ImagePattern(image));
		}
	}

}
