package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Data;
import model.Difficulty;
import model.Trail;
import model.Type;
import model.User;
import model.Utilities;

public class ManagementController implements Initializable {
	private static User loggedUser;
	private TreeMap<String, User> users;
	private HashSet<Trail> trails;

	@FXML
	private TableView<User> userTable;
	@FXML
	private TableColumn<User, String> usernameColumn;
	@FXML
	private TableColumn<User, String> firstNameColumn;
	@FXML
	private TableColumn<User, String> lastNameColumn;
	@FXML
	private TableColumn<User, String> phoneNumberColumn;
	@FXML
	private TableView<Trail> trailTable;
	@FXML
	private TableColumn<Trail, String> trailNameColumn;
	@FXML
	private TableColumn<Trail, String> trailAddressColumn;
	@FXML
	private TableColumn<Trail, Double> distanceColumn;
	@FXML
	private TableColumn<Trail, Double> elevationGainColumn;
	@FXML
	private TableColumn<Trail, String> typeColumn;
	@FXML
	private TableColumn<Trail, String> difficultyColumn;

	ObservableList<User> userList = FXCollections.observableArrayList();
	ObservableList<Trail> trailList = FXCollections.observableArrayList();

	@FXML
	void changeSceneAddNewTrail(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/AddNewTrail.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@FXML
	void changeSceneAddNewUser(ActionEvent event) {

	}

	@FXML
	void changeSceneLoggedUser(ActionEvent event) {

	}

	@FXML
	void changeSceneViewTrail(ActionEvent event) {

	}

	@FXML
	void changeSceneViewUser(ActionEvent event) {

	}

	@FXML
	void deleteTrail(ActionEvent event) {

	}

	@FXML
	void deleteUser(ActionEvent event) {

	}

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
		trails = Data.getTrails();
		loggedUser = Data.getLoggedUser();

		Utilities.fillUserMap(users);
		Utilities.fillTrailMap(trails);
		
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("fName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("lName"));
		phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("phone"));

		trailNameColumn.setCellValueFactory(new PropertyValueFactory<Trail, String>("name"));
		trailAddressColumn.setCellValueFactory(new PropertyValueFactory<Trail, String>("address"));
		distanceColumn.setCellValueFactory(new PropertyValueFactory<Trail, Double>("distance"));
		elevationGainColumn.setCellValueFactory(new PropertyValueFactory<Trail, Double>("elevationGain"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Trail, String>("type"));
		difficultyColumn.setCellValueFactory(new PropertyValueFactory<Trail, String>("difficulty"));

		
		List<Trail> result1 = null;
		result1 = (Data.getTrails().stream()
				.collect(Collectors.toList()));

		ObservableList<Trail> observableList = FXCollections.observableList(result1);

		
		trailTable.setItems(observableList);
		
		Map<String, User> result = null;
		result = (Data.getUsers().entrySet().stream()
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));

		for (java.util.Map.Entry<String, User> entry : result.entrySet()) {
			userList.add(entry.getValue());

		}

		userTable.setItems(userList);

	}

}
