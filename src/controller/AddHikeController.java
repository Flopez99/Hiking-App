package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.controlsfx.control.CheckComboBox;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Data;
import model.Difficulty;
import model.Role;
import model.Trail;
import model.Type;
import model.User;
import model.Utilities;

public class AddHikeController implements Initializable {
	private static User loggedUser;
	private TreeMap<String, User> users;
	private HashSet<Trail> trails;
	@FXML
	private Parent root;
	@FXML
	private ContextMenu cm;
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
	private Slider distanceSlider, eGainSlider;
	@FXML
	private Label distanceLabel, eGainLabel;
	@FXML
	private TextField searchBar;

	List<Trail> nameList = FXCollections.observableArrayList();
	List<Trail> difficultyList = FXCollections.observableArrayList();
	List<Trail> typeList = FXCollections.observableArrayList();
	List<Trail> finalList = FXCollections.observableArrayList();

	@FXML
	private CheckComboBox<Difficulty> difficultyChoiceBox;
	@FXML
	private CheckComboBox<Type> typeChoiceBox;

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

	public void trailSearch(KeyEvent event) {
		nameList.clear();
		difficultyList.clear();
		typeList.clear();
		finalList.clear();

		ObservableList<Difficulty> difficultyOption = difficultyChoiceBox.getCheckModel().getCheckedItems();
		ObservableList<Type> typeOption = typeChoiceBox.getCheckModel().getCheckedItems();

		if (searchBar.getText() != "") {

			nameList = (Data.getTrails().stream()
					.filter(map -> map.getName().toLowerCase().startsWith(searchBar.getText().toLowerCase())))
							.collect(Collectors.toList());

			if (difficultyOption.size() == 2) {
				difficultyList = (nameList.stream()
						.filter(trail -> trail.getDifficulty().equals(difficultyOption.get(0))
								|| trail.getDifficulty().equals(difficultyOption.get(1)))
						.collect(Collectors.toList()));
			} else if (difficultyOption.size() == 1) {
				difficultyList = (nameList.stream()
						.filter(trail -> trail.getDifficulty().equals(difficultyOption.get(0)))
						.collect(Collectors.toList()));
			} else if (difficultyOption.size() == 0 || difficultyOption.size() == 3) {
				difficultyList = nameList;
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

			// if(distanceSlider.getValue() != 0 || eGainSlider.getValue())

			finalList = (typeList.stream().filter(trail -> trail.getDistance() >= distanceSlider.getValue())
					.filter(trail -> trail.getElevationGain() >= eGainSlider.getValue()).collect(Collectors.toList()));

			ObservableList<Trail> observableList = FXCollections.observableList(finalList);
//		typeList = (ObservableList<Trail>) (typeList.stream()
//				.filter(map -> map.getDistance()).collect(Collectors.toList()));

			trailTable.setItems(observableList);
		}
	}

	public void changeLabel() {
		distanceSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				distanceLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
			}
		});

		eGainSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				eGainLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
			}
		});
	}

	public void test(ActionEvent event) throws IOException {
		Trail trail = trailTable.getSelectionModel().getSelectedItem();

		Data.setPickedTrail(trail);

		changeSceneRegisterHike(event);
	}

	public void changeSceneRegisterHike(ActionEvent event) throws IOException {
		Parent rootP = FXMLLoader.load(getClass().getResource("/view/TrailView.fxml"));
		Scene scene = new Scene(rootP);
		Stage window = (Stage) root.getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = Data.getUsers();
		trails = Data.getTrails();
		loggedUser = Data.getLoggedUser();

		changeLabel();

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

		System.out.println(loggedUser.getHistoryMap());

	}

}
