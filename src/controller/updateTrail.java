package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Data;
import model.Difficulty;
import model.Trail;
import model.Type;

public class updateTrail implements Initializable {
	@FXML
	private TextField eGain;
	@FXML
	private TextField trailName;

	@FXML
	private TextField trailAddress;

	@FXML
	private TextField distance;

	public Trail pickedTrail;
	@FXML
	private ComboBox<Type> trailTypeBox;

	@FXML
	private ComboBox<Difficulty> difficultyBox;
	
	
	public boolean updating = false;

	public void update(ActionEvent event) throws IOException {

		updating = false;
		trailName.setDisable(updating);
		trailAddress.setDisable(updating);
		distance.setDisable(updating);
		difficultyBox.setDisable(updating);
		trailTypeBox.setDisable(updating);
		eGain.setDisable(updating);

		pickedTrail.setName(trailName.getText());
		pickedTrail.setAddress(trailAddress.getText());
		pickedTrail.setDistance(Double.parseDouble(distance.getText()));
		pickedTrail.setElevationGain(Double.parseDouble(eGain.getText()));
		pickedTrail.setDifficulty(difficultyBox.getValue());
		pickedTrail.setType(trailTypeBox.getValue());
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Information");
		alert.setContentText("Update Succesful");
		alert.show();
	}

	public void fillInfo() {
		updating = true;
		trailName.setText(pickedTrail.getName());
		trailAddress.setText(pickedTrail.getAddress());
		distance.setText(pickedTrail.getDistance() + "");
		difficultyBox.setValue(pickedTrail.getDifficulty());
		trailTypeBox.setValue(pickedTrail.getType());
		eGain.setText(pickedTrail.getElevationGain() + "");
		
		trailName.setDisable(updating);
		trailAddress.setDisable(updating);
		distance.setDisable(updating);
		difficultyBox.setDisable(updating);
		trailTypeBox.setDisable(updating);
		eGain.setDisable(updating);

	}
	public void deleteTrail(ActionEvent event) throws IOException {
		
		Data.getTrails().remove(pickedTrail);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Trail Deleted");
		alert.setContentText("Trail Deleted Succesfully");
		alert.show();
		changeSceneManagement(event);
	}
	
	public void changeSceneManagement(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/Management.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pickedTrail = Data.getPickedTrail();
		trailTypeBox.getItems().removeAll(trailTypeBox.getItems());
		trailTypeBox.getItems().addAll(Type.LOOP, Type.POINT_TO_POINT, Type.OUT_AND_BACK);
		difficultyBox.getItems().removeAll(difficultyBox.getItems());
		difficultyBox.getItems().addAll(Difficulty.EASY, Difficulty.MODERATE, Difficulty.HARD);
		fillInfo();

	}

}
