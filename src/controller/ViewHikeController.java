package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Data;
import model.Hike;
import model.Trail;
import model.User;

public class ViewHikeController implements Initializable {

	private static Trail pickedTrail;
	private User loggedUser;
	private LinkedList<String> imagePaths;

	@FXML
	private Label trailName, trailAdress, trailDistance, trailEGain, trailType, trailDifficulty;

	@FXML
	private Button registerHike,pictureButton;;

	@FXML
	private TextField hikeDuration,distanceHiked,averagePace,hourStart, minuteStart,hourEnd,minuteEnd;

	@FXML
	private DatePicker dateStart, dateEnd;


	@FXML
	private ComboBox<String> amPmStart,amPmEnd;



	public void setLabels() {
		trailName.setText(pickedTrail.getName());
		trailAdress.setText(pickedTrail.getAddress());
		trailDistance.setText(pickedTrail.getDistance() + "");
		trailEGain.setText(pickedTrail.getElevationGain() + "");
		trailDifficulty.setText(pickedTrail.getDifficulty());
		trailType.setText(pickedTrail.getType());
		amPmStart.getItems().addAll("AM","PM");
		amPmEnd.getItems().addAll("AM","PM");
		
	}
	
	public void registerHike(ActionEvent e) throws IOException {
		String name = pickedTrail.getName();
		String dateTimeStart = dateStart.toString() + " " + hourStart.getText() + ":" + minuteStart.getText() + " " + amPmStart.getSelectionModel().getSelectedItem();
		String dateTimeEnd = dateEnd.toString() + " " + hourEnd.getText() + ":" + minuteEnd.getText() + " " + amPmEnd.getSelectionModel().getSelectedItem();

		double hikingDistance = Double.parseDouble(distanceHiked.getText());
		double duration = Double.parseDouble(hikeDuration.getText());
		double hikeAveragePace = Double.parseDouble(averagePace.getText());
		
		Hike hike = new Hike(name, dateTimeStart,dateTimeEnd, hikingDistance + "", duration + "",hikeAveragePace + "", imagePaths);
		loggedUser.getHistoryMap().add(hike);
		
		
		
		changeSceneTrailRegister(e);
	}
	
	
	public void selectPicture(ActionEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().addAll(new ExtensionFilter("JPG Files", "*.jpg"));

		File selectedFile = fc.showOpenDialog(stage);

		if (selectedFile != null) {

			
			imagePaths.add(selectedFile.getAbsolutePath());
		}
	}
	public void changeSceneTrailRegister(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/TrailRegister.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pickedTrail = Data.getPickedTrail();
		loggedUser = Data.getLoggedUser();
		imagePaths = new LinkedList<String>();
		setLabels();
	}

}
