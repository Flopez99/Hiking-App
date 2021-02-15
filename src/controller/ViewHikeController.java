package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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
	private Label error, errorDStart, errorDEnd, errorDH, errorHD, errorAP, errorTStart, errorTEnd;

	@FXML
	private Button registerHike, pictureButton;;

	@FXML
	private TextField hikeDuration, distanceHiked, averagePace, hourStart, minuteStart, hourEnd, minuteEnd;

	@FXML
	private DatePicker dateStart, dateEnd;

	@FXML
	private ComboBox<String> amPmStart, amPmEnd;

	public void setLabels() {
		trailName.setText(pickedTrail.getName());
		trailAdress.setText(pickedTrail.getAddress());
		trailDistance.setText(pickedTrail.getDistance() + "");
		trailEGain.setText(pickedTrail.getElevationGain() + "");
		trailDifficulty.setText(pickedTrail.getDifficulty() + "");
		trailType.setText(pickedTrail.getType() + "");
		amPmStart.getItems().addAll("AM", "PM");
		amPmEnd.getItems().addAll("AM", "PM");

	}

	public void registerHike(ActionEvent e) throws IOException {
		refreshErrors();
		boolean canRegister = true;

		String startDate = dateStart.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String endDate = "";
		String name = pickedTrail.getName();
		int hourStart1 = 0;
		int minuteStart1 = 0;
		int hourEnd1 = 0;
		int minuteEnd1 = 0;
		double hikingDistance = 0;
		double duration = 0;
		double hikeAveragePace = 0;

		if (dateEnd.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).compareTo(startDate) >= 0) {
			endDate = dateEnd.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} else {
			errorDEnd.setText("Earlier than Start");
			canRegister = false;
		}

		try {
			if (hourStart.getText().length() == 2 && minuteStart.getText().length() == 2) {
				hourStart1 = Integer.parseInt(hourStart.getText());
				minuteStart1 = Integer.parseInt(minuteStart.getText());
				if(hourStart1 > 12 || minuteStart1 >=60) {
					errorTStart.setText("Invalid Time");
					canRegister = false;
				}
			} else {
				errorTStart.setText("Invalid Time");
				canRegister = false;
			}
		} catch (NumberFormatException e1) {
			errorTStart.setText("Invalid Time");
			canRegister = false;

		}
		try {
			if (hourEnd.getText().length() == 2 && minuteEnd.getText().length() == 2) {
				hourEnd1 = Integer.parseInt(hourEnd.getText());
				minuteEnd1 = Integer.parseInt(minuteEnd.getText());
				if(hourEnd1 > 12 || minuteEnd1 >=60) {
					errorTEnd.setText("Invalid Time");
					canRegister = false;
				}
			} else {
				errorTEnd.setText("Invalid Time");
				canRegister = false;
			}
		} catch (NumberFormatException e1) {
			errorTEnd.setText("Invalid Time");
			canRegister = false;

		}

		String dateTimeStart = startDate + " " + hourStart1 + ":" + minuteStart1 + " "
				+ amPmStart.getSelectionModel().getSelectedItem();
		String dateTimeEnd = endDate + " " + hourEnd1 + ":" + minuteEnd1 + " "
				+ amPmEnd.getSelectionModel().getSelectedItem();

		try {
			hikingDistance = Double.parseDouble(distanceHiked.getText());
		} catch (NumberFormatException e1) {
			errorDH.setText("Invalid Number");
			canRegister = false;
		}
		try {
			duration = Double.parseDouble(hikeDuration.getText());
		} catch (NumberFormatException e1) {
			errorHD.setText("Invalid Number");
			canRegister = false;
		}
		try {
			hikeAveragePace = Double.parseDouble(averagePace.getText());
		} catch (NumberFormatException e1) {
			errorAP.setText("Invalid Number");
			canRegister = false;
		}

		if (canRegister) {
			Hike hike = new Hike(name, dateTimeStart, dateTimeEnd, hikingDistance + "", duration + "",
					hikeAveragePace + "", imagePaths);
			loggedUser.getHistoryMap().add(hike);
			changeSceneTrailRegister(e);

		} else {
			error.setText("ERROR");
		}

	}

	public void refreshErrors() {
		error.setText("");
		errorDStart.setText("");
		errorDEnd.setText("");
		errorDH.setText("");
		errorHD.setText("");
		errorAP.setText("");
		errorTStart.setText("");
		errorTEnd.setText("");
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
