package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Data;
import model.User;

public class ManagementController implements Initializable{
	private static User loggedUser;
	private TreeMap<String, User> users;
	
	
	public void changeSceneLoggedIn(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/AdminLogged.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = Data.getUsers();
		loggedUser = Data.getLoggedUser();
	}

}
