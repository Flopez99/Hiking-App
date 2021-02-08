package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Data;
import model.Difficulty;
import model.Trail;
import model.Type;

public class HikeRegisterController implements Initializable {

	private HashMap<String, Trail> trails;

	@FXML
	private TextField trailName, trailAddress, trailDistance, trailEGain;

	public void createNewTrail(ActionEvent event) {
		String name = trailName.getText();
		String address = trailAddress.getText();
		double distance = Double.parseDouble(trailDistance.getText());
		double eGain = Double.parseDouble(trailEGain.getText());

		Trail newTrail = new Trail(name, address, distance, eGain, Type.LOOP, Difficulty.HARD);

		trails.put(name, newTrail);
		
		System.out.println(trails);
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
		trails = Data.getTrails();
	}

}
