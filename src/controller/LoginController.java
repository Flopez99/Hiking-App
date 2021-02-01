package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Data;
import model.Role;
import model.User;

public class LoginController implements Initializable{
	private static User loggedUser;
	private TreeMap<String, User> users;
	
	@FXML
	private TextField usernameField, passwordField,userName, password, phoneNumber, firstName, lastName;
	
	
	public void createNewUser(ActionEvent e) throws IOException{
		String uName = userName.getText();
		String fName = firstName.getText();
		String lName = lastName.getText();
		String pWord = password.getText();
		String pNumber = phoneNumber.getText();
		Role role = null;

		if (users.isEmpty()) {
			role = Role.ADMIN;
		} else {
			role = Role.PATRON;
		}

		User user = new User(fName, lName, pNumber, uName, pWord, role);

	//	user.setProfilePicture(selectPicture(e));
		System.out.println(user);
		users.put(uName, user);

		userName.clear();
		password.clear();
		phoneNumber.clear();
		firstName.clear();
		lastName.clear();

		System.out.println(users);

		changeSceneLogIn(e);
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
	
	public void changeSceneLoggedIn(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/LoggedIn.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	public void changeSceneSignUp(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	public void changeSceneLogIn(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = Data.getUsers();
		
	}

}
