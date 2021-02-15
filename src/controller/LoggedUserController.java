package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import model.Bag;
import model.Data;
import model.Trail;
import model.User;
import model.Utilities;

public class LoggedUserController implements Initializable {
	private static User loggedUser;
	private TreeMap<String, User> users;
	private static HashSet<Trail> trails;
	
	@FXML
	public Label fName;
	@FXML
	public ImageView userPicture;
	
	
	public void changeNameLabel() {
		fName.setText(loggedUser.getFName());
	}
	
	public void showUserImage() {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(loggedUser.getProfilePicture()));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			userPicture.setImage(image);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void changeSceneManagement(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Management.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		
	}
	public void changeSceneTrailRegister(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/TrailRegister.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		
	}
	public void changeSceneViewAccount(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAccount.fxml"));
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
		trails = Data.getTrails();
		loggedUser = Data.getLoggedUser();
		showUserImage();
		changeNameLabel();
		
	}

}
