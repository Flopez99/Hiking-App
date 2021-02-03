package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Data;
import model.User;

public class LoggedUserController implements Initializable {
	private static User loggedUser;
	private TreeMap<String, User> users;

	
	@FXML
	public Label fName;
	
	public void changeNameLabel() {
		fName.setText(loggedUser.getfName().get());
	}
	
	
	public void changeSceneManagement(ActionEvent e) throws IOException {
		loggedUser = null;
		Parent root = FXMLLoader.load(getClass().getResource("/view/Management.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		
	}
	public void changeSceneTrailRegister(ActionEvent e) throws IOException {
		loggedUser = null;
		Parent root = FXMLLoader.load(getClass().getResource("/view/TrailRegister.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		
	}
	public void changeSceneLogOut(ActionEvent e) throws IOException {
		loggedUser = null;
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = Data.getUsers();
		loggedUser = Data.getLoggedUser();
		changeNameLabel();
	}

}
