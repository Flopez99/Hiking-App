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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Data;
import model.Hike;
import model.Role;
import model.User;

public class ViewAccountController implements Initializable {

	@FXML
	private TextField firstName1, lastName1, phoneNumber1, address1, userName1;
	@FXML
	private PasswordField password1;
	@FXML
	private ImageView profilePicture1;

	@FXML
	private TableView<Hike> historyTable;

	@FXML
	private TableColumn<Hike, String> trailName, dateStarted, dateEnded;

	public static Stage stage;

	private User loggedUser;
	private ObservableList<Hike> hikingHistory;
	private boolean updating = false;

	@FXML
	void changeSceneUserLoggedIn(ActionEvent event) throws IOException {
		Parent secondRoot = null;
		if (loggedUser.getRole().equals(Role.ADMIN)) {
			secondRoot = FXMLLoader.load(getClass().getResource("/view/AdminLogged.fxml"));
		} else {
			secondRoot = FXMLLoader.load(getClass().getResource("/view/LoggedIn.fxml"));

		}
		Scene secondScene = new Scene(secondRoot);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(secondScene);
		window.show();
	}

	public void updateAccount(ActionEvent event) {

		firstName1.setDisable(updating);
		lastName1.setDisable(updating);
		phoneNumber1.setDisable(updating);
		address1.setDisable(updating);
		password1.setDisable(updating);

		loggedUser.setfName(firstName1.getText());
		loggedUser.setlName(lastName1.getText());
		loggedUser.setPhone(phoneNumber1.getText());
		loggedUser.setAddress(address1.getText());
		loggedUser.setPassword(password1.getText());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Information");
		alert.setContentText("Update Succesful");
		alert.show();

	}

	public void fillInfo() {
		firstName1.setText(loggedUser.getFName());
		lastName1.setText(loggedUser.getLName());
		phoneNumber1.setText(loggedUser.getPhone());
		address1.setText(loggedUser.getAddress());
		userName1.setText(loggedUser.getUserName());
		password1.setText(loggedUser.getPassword());

		showUserImage();

		userName1.setDisable(true);
		firstName1.setDisable(true);
		lastName1.setDisable(true);
		phoneNumber1.setDisable(true);
		address1.setDisable(true);
		password1.setDisable(true);
	}

	public void showUserImage() {
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(loggedUser.getProfilePicture()));
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			profilePicture1.setImage(image);

		} catch (IOException e) {
			e.printStackTrace();
		}
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
				profilePicture1.setImage(image);

				loggedUser.setProfilePicture(selectedFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				BufferedImage imageB = ImageIO.read(new File(loggedUser.getProfilePicture()));
				Image image = SwingFXUtils.toFXImage(imageB, null);
				profilePicture1.setImage(image);

			} catch (IOException e) {
				e.printStackTrace();
			}

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loggedUser = Data.getLoggedUser();
		hikingHistory = FXCollections.observableArrayList(loggedUser.getHistoryMap());

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
