package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Data;
import model.Hike;
import model.Role;
import model.User;

public class ViewUserController implements Initializable {

	@FXML
	private TextField userName;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField phoneNumber;

	@FXML
	private Label role;
	@FXML
	private TextField address;

	@FXML
	private TextField skillLevel;

	@FXML
	private ImageView profilePicture;

	@FXML
	private TableView<Hike> historyTable;

	@FXML
	private TableColumn<Hike, String> trailName;

	@FXML
	private TableColumn<Hike, String> dateStarted;

	@FXML
	private TableColumn<Hike, String> dateEnded;

	@FXML
	private Button promoteButton, disableButton;

	@FXML
	private Label statusLabel;

	private User pickedUser;
	
	public static Stage stage;
	private ObservableList<Hike> hikingHistory;

	public void changeSceneManagement(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Management.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	public void disableUser(ActionEvent event) {
		if (pickedUser.isActive()) {
			pickedUser.setActive(false);
			statusLabel.setText("Suspended");

		} else {
			pickedUser.setActive(true);
			statusLabel.setText("Active");

		}
	}

	public void promoteToAdmin(ActionEvent event) {
		if (pickedUser.getRole() != Role.ADMIN) {
			pickedUser.setRole(Role.ADMIN);
			role.setText(Role.ADMIN + "");
		} else {
			pickedUser.setRole(Role.USER);
			role.setText(Role.USER + "");

		}
	}

	public void selectHike(ActionEvent e) throws IOException {
		Hike hike = historyTable.getSelectionModel().getSelectedItem();

		Data.setPickedHike(hike);

		changeSceneHikeView(e);
	}

	private void changeSceneHikeView(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/HikeView.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		stage = window;
	}

	public void fillInfo() {
		firstName.setText(pickedUser.getFName());
		lastName.setText(pickedUser.getLName());
		phoneNumber.setText(pickedUser.getPhone());
		address.setText(pickedUser.getAddress());
		userName.setText(pickedUser.getUserName());
		skillLevel.setText(pickedUser.getSkillLevel().toString());
		if (pickedUser.isActive()) {
			statusLabel.setText("Active");
			disableButton.setText("Suspend");
		} else {
			statusLabel.setText("Suspended");
			disableButton.setText("Enable");
		}

		if (pickedUser.getRole() != Role.ADMIN) {
			promoteButton.setText("Promote to Admin");
			role.setText(Role.USER + "");

		} else {
			promoteButton.setText("Demote");
			role.setText(Role.ADMIN + "");

		}

		showUserImage();

	}

	public void deleteUser(ActionEvent e) throws IOException {
		
		Data.getUsers().remove(pickedUser.getUserName());
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("User Deleted");
		alert.setContentText("User Deleted Succesfully");
		alert.show();
		changeSceneManagement(e);
	}
	
	public void showUserImage() {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(pickedUser.getProfilePicture()));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			profilePicture.setImage(image);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pickedUser = Data.getPickedUser();
		hikingHistory = FXCollections.observableArrayList(pickedUser.getHistoryMap());

		trailName.setCellValueFactory(new PropertyValueFactory<Hike, String>("trailName"));
		dateStarted.setCellValueFactory(new PropertyValueFactory<Hike, String>("dateAndTimeStarted"));
		dateEnded.setCellValueFactory(new PropertyValueFactory<Hike, String>("dateAndTimeEnded"));

		historyTable.setItems(hikingHistory);

		fillInfo();
	}

	public static Stage getStage() {
		return stage;
	}
}
