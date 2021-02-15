package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Bag;
import model.Data;
import model.Role;
import model.SkillLevel;
import model.Trail;
import model.User;
import model.Utilities;

public class LoginController implements Initializable {
	private static User loggedUser;
	private TreeMap<String, User> users;
	private HashSet<Trail> trails;

	private static boolean signingUp = false;
	private static String imagePath;
	@FXML
	private TextField usernameField, userName, phoneNumber, firstName, lastName, addressField;

	@FXML
	private PasswordField passwordField, password, confirmPassword;
	@FXML
	private ComboBox<SkillLevel> skillLevel;

	@FXML
	private ImageView profilePicture;
	@FXML
	private Circle myCircle;

	@FXML
	private Label error1, error2, userNameError, passwordError, lNError, fNError, pError, passError;

	public Bag bag;

	public void createNewUser(ActionEvent e) throws IOException {
		refreshLabels();
		
		boolean canRegister = true;
		String uName = userName.getText();
		String fName = firstName.getText();
		String lName = lastName.getText();
		String pWord = password.getText();
		String pNumber = phoneNumber.getText();
		String address = addressField.getText();
		Role role = null;
		SkillLevel skillLevel1 = skillLevel.getValue();

		if (uName.equals("")) {
			canRegister = false;
			userNameError.setText("Error: Blank User Name");
		} else {
			canRegister = true;
		}
		if (fName.equals("")) {
			canRegister = false;
			fNError.setText("Error: Blank First Name");
		} else {
			canRegister = true;
		}
		if (lName.equals("")) {
			canRegister = false;
			lNError.setText("Error: Blank Last Name");
		} else {
			canRegister = true;
		}
		if (pWord.equals("")) {
			canRegister = false;
			passError.setText("Error: Blank Password");
		} else {
			canRegister = true;
		}
		if (pNumber.equals("")) {
			canRegister = false;
			pError.setText("Error: Blank Phone Number");
		} else {
			canRegister = true;
		}

		if (users.isEmpty()) {
			role = Role.ADMIN;
		} else {
			role = Role.USER;
		}

		User user = new User(fName, lName, pNumber, uName, pWord, role, skillLevel1, address);

		if (imagePath != null) {
			user.setProfilePicture(imagePath);
		} else {
			user.setProfilePicture("Pictures/defaultUser.jpg");
		}

		if (pWord.equals(confirmPassword.getText())) {

		} else {
			passwordError.setText("Passwords do not Match");
			canRegister = false;
		}

		if (users.containsKey(userName.getText())) {
			userNameError.setText("Username Already Exists");
			canRegister = false;
		} else {
			if (canRegister = true) {
				users.put(uName, user);
				changeSceneLogIn(e);
			}
		}

	}

	public void refreshLabels() {
		userNameError.setText("");
		fNError.setText("");
		lNError.setText("");
		passError.setText("");
		pError.setText("");

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

			if(users.get(uName).isActive()) {
			loggedUser = users.get(uName);
			Data.setLoggedUser(loggedUser);
			changeSceneLoggedIn(event);
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Your Account is Suspended");
				alert.show();
			}
		} else {
			error1.setText("Username or Passowrd is Incorrect");
			error2.setText("Failed to Log In");
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
