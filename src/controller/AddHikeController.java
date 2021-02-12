package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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
import model.Trail;
import model.Type;
import model.User;
import model.Utilities;

public class AddHikeController implements Initializable {
	private static User loggedUser;
	private TreeMap<String, User> users;
	private HashMap<String, Trail> trails;
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
	private Slider distanceSlider, elevationGainSlider;
	@FXML
	private Label distanceLabel, eGainLabel;
	@FXML
	private TextField searchBar;

	ObservableList<Trail> trailList = FXCollections.observableArrayList();

	@FXML
	private CheckComboBox<Difficulty> difficultyChoiceBox;
	@FXML
	private CheckComboBox<Type> typeChoiceBox;

	@FXML
	void changeSceneUserLoggedIn(ActionEvent event) {

	}

	public void trailSearch(KeyEvent event) {
		trailList.clear();
		Map<String, Trail> result = null;

		ObservableList<Difficulty> difficultyList = difficultyChoiceBox.getCheckModel().getCheckedItems();
		ObservableList<Type> typeList = typeChoiceBox.getCheckModel().getCheckedItems();

		if (difficultyList.contains(Difficulty.EASY)) {

			result = (Data.getTrails().entrySet().stream()
					.filter(map -> map.getValue().getDifficulty().equals(Difficulty.EASY.toString()))
					.filter(map -> map.getKey().toLowerCase().startsWith(searchBar.getText().toLowerCase()))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));
			for (java.util.Map.Entry<String, Trail> entry : result.entrySet()) {
				trailList.add(entry.getValue());
			}
		}
		if (difficultyList.contains(Difficulty.MODERATE)) {
			result = (Data.getTrails().entrySet().stream()
					.filter(map -> map.getValue().getDifficulty().equals(Difficulty.MODERATE.toString()))
					.filter(map -> map.getKey().toLowerCase().startsWith(searchBar.getText().toLowerCase()))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));
			for (java.util.Map.Entry<String, Trail> entry : result.entrySet()) {
				trailList.add(entry.getValue());
			}

		}
		if (difficultyList.contains(Difficulty.HARD)) {
			result = (Data.getTrails().entrySet().stream()
					.filter(map -> map.getValue().getDifficulty().equals(Difficulty.HARD.toString()))
					.filter(map -> map.getKey().toLowerCase().startsWith(searchBar.getText().toLowerCase()))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));
			for (java.util.Map.Entry<String, Trail> entry : result.entrySet()) {
				trailList.add(entry.getValue());
			}

		}
		if (difficultyList.isEmpty()) {
			result = (Data.getTrails().entrySet().stream()
					.filter(map -> map.getKey().toLowerCase().startsWith(searchBar.getText().toLowerCase()))
					.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));
			for (java.util.Map.Entry<String, Trail> entry : result.entrySet()) {
				trailList.add(entry.getValue());
			}
		}

		trailTable.setItems(trailList);

	}

	public void changeLabel() {
		distanceSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				distanceLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
			}
		});

		elevationGainSlider.valueProperty().addListener(new ChangeListener<Number>() {

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

		Utilities.fillTrailMap(trails);

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

	}

}
