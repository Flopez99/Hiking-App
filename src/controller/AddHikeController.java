package controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Data;
import model.Trail;
import model.User;

public class AddHikeController implements Initializable{
	private static User loggedUser;
	private TreeMap<String, User> users;
	private HashMap<String, Trail> trails;
	
	
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

	ObservableList<Trail> trailList = FXCollections.observableArrayList();
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = Data.getUsers();
		trails = Data.getTrails();
		loggedUser = Data.getLoggedUser();
		
		trailNameColumn.setCellValueFactory(new PropertyValueFactory<Trail, String>("name"));
		trailAddressColumn.setCellValueFactory(new PropertyValueFactory<Trail, String>("address"));
		distanceColumn.setCellValueFactory(new PropertyValueFactory<Trail, Double>("distance"));
		elevationGainColumn.setCellValueFactory(new PropertyValueFactory<Trail, Double>("elevationGain"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Trail, String>("type"));
		difficultyColumn.setCellValueFactory(new PropertyValueFactory<Trail, String>("difficulty"));
		
		Map<String, Trail> result1 = null;
		result1 = (Data.getTrails().entrySet().stream()
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue())));

		for (java.util.Map.Entry<String, Trail> entry : result1.entrySet()) {
			trailList.add(entry.getValue());

		}
		
		trailTable.setItems(trailList);
		
	}

}
