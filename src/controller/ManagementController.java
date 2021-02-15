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

import org.controlsfx.control.CheckComboBox;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Data;
import model.Difficulty;
import model.Role;
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
	@FXML
	private TextField trailSearchBar;
	@FXML
	private TextField userSearchBar;

	List<Trail> trailNameList = FXCollections.observableArrayList();
	List<Trail> difficultyList = FXCollections.observableArrayList();
	List<Trail> typeList = FXCollections.observableArrayList();

	List<User> userNameList = FXCollections.observableArrayList();

	@FXML
	private CheckComboBox<Difficulty> difficultyChoiceBox;
	@FXML
	private CheckComboBox<Type> typeChoiceBox;

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

	
	public void changeSceneViewTrail(ActionEvent event) throws IOException {
		Data.setPickedTrail(trailTable.getSelectionModel().getSelectedItem());
		Parent root = FXMLLoader.load(getClass().getResource("/view/UpdateTrail.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	public void changeSceneViewUser(ActionEvent event) throws IOException {
		Data.setPickedUser(userTable.getSelectionModel().getSelectedItem());

		Parent root = FXMLLoader.load(getClass().getResource("/view/ViewUser.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	public void deleteTrail(ActionEvent event) {
		Data.getTrails().remove(trailTable.getSelectionModel().getSelectedItem());
		trailTable.refresh();

	}

	public void deleteUser(ActionEvent e) {
		Data.getUsers().remove(userTable.getSelectionModel().getSelectedItem().getUserName());
		userTable.refresh();
	}

	public void importTrails(ActionEvent event) {
		Utilities.fillTrailMap(trails);
	}

	public void importUsers(ActionEvent event) {
		Utilities.fillUserMap(users);
	}

	public void trailSearch() {
		trailNameList.clear();
		difficultyList.clear();
		typeList.clear();

		ObservableList<Difficulty> difficultyOption = difficultyChoiceBox.getCheckModel().getCheckedItems();
		ObservableList<Type> typeOption = typeChoiceBox.getCheckModel().getCheckedItems();

		if (trailSearchBar.getText() != "") {
			trailNameList = (Data.getTrails().stream()
					.filter(map -> map.getName().toLowerCase().startsWith(trailSearchBar.getText().toLowerCase())))
							.collect(Collectors.toList());

			if (difficultyOption.size() == 2) {
				difficultyList = (trailNameList.stream()
						.filter(trail -> trail.getDifficulty().equals(difficultyOption.get(0))
								|| trail.getDifficulty().equals(difficultyOption.get(1)))
						.collect(Collectors.toList()));
			} else if (difficultyOption.size() == 1) {
				difficultyList = (trailNameList.stream()
						.filter(trail -> trail.getDifficulty().equals(difficultyOption.get(0)))
						.collect(Collectors.toList()));
			} else if (difficultyOption.size() == 0 || difficultyOption.size() == 3) {
				difficultyList = trailNameList;
			}

			if (typeOption.size() == 2) {
				typeList = (difficultyList.stream().filter(
						trail -> trail.getType().equals(typeOption.get(0)) || trail.getType().equals(typeOption.get(1)))
						.collect(Collectors.toList()));
			} else if (typeOption.size() == 1) {
				typeList = (difficultyList.stream().filter(trail -> trail.getType().equals(typeOption.get(0)))
						.collect(Collectors.toList()));
			} else if (typeOption.size() == 0 || (typeOption.size() == 3)) {
				typeList = difficultyList;
			}

			ObservableList<Trail> observableList = FXCollections.observableList(typeList);

			trailTable.setItems(observableList);
		}
	}

	public void userSearch() {
		userList.clear();

		if (userSearchBar.getText() != "") {
			Map<String, User> result = null;

			result = (Data.getUsers().entrySet().stream()
					.filter(user -> user.getKey().toLowerCase().startsWith((userSearchBar.getText().toLowerCase())))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));

			for (java.util.Map.Entry<String, User> entry : result.entrySet()) {
				userList.add(entry.getValue());

			}

			userTable.setItems(userList);

		}
	}

	public void changeSceneLoggedUser(ActionEvent e) throws IOException {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = Data.getUsers();
		trails = Data.getTrails();
		loggedUser = Data.getLoggedUser();

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

		difficultyChoiceBox.setTitle("Difficulty");
		difficultyChoiceBox.getItems().removeAll(difficultyChoiceBox.getItems());
		difficultyChoiceBox.getItems().addAll(Difficulty.EASY, Difficulty.MODERATE, Difficulty.HARD);

		typeChoiceBox.setTitle("Type");
		typeChoiceBox.getItems().removeAll(typeChoiceBox.getItems());
		typeChoiceBox.getItems().addAll(Type.LOOP, Type.POINT_TO_POINT, Type.OUT_AND_BACK);

	}

}
