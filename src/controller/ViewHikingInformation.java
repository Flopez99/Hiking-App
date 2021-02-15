package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.TreeMap;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Data;
import model.Hike;
import model.User;

public class ViewHikingInformation implements Initializable {
	private TreeMap<String, User> users;
	private Hike pickedHike;
	private Stage stage = ViewAccountController.getStage();

	@FXML
	private TilePane tile;

	@FXML
	private TextField trailName, hikeDuration,distanceHiked,averagePace,hourStart, minuteStart,hourEnd,minuteEnd;

	@FXML
	private DatePicker dateStart, dateEnd;
	
	
	private ImageView createImageView(final File imageFile) {
		// DEFAULT_THUMBNAIL_WIDTH is a constant you need to define
		// The last two arguments are: preserveRatio, and use smooth (slower)
		// resizing

		ImageView imageView = null;
		try {
			final Image image = new Image(new FileInputStream(imageFile), 150, 0, true, true);
			imageView = new ImageView(image);
			imageView.setFitWidth(150);
			imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {

					if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

						if (mouseEvent.getClickCount() == 2) {
							try {
								BorderPane borderPane = new BorderPane();
								ImageView imageView = new ImageView();
								Image image = new Image(new FileInputStream(imageFile));
								imageView.setImage(image);
								imageView.setStyle("-fx-background-color: BLACK");
								imageView.setFitHeight(stage.getHeight() - 10);
								imageView.setPreserveRatio(true);
								imageView.setSmooth(true);
								imageView.setCache(true);
								borderPane.setCenter(imageView);
								borderPane.setStyle("-fx-background-color: BLACK");
								Stage newStage = new Stage();
								newStage.setWidth(stage.getWidth());
								newStage.setHeight(stage.getHeight());
								newStage.setTitle(imageFile.getName());
								Scene scene = new Scene(borderPane, Color.BLACK);
								newStage.setScene(scene);
								newStage.show();
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}

						}
					}
				}
			});
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		return imageView;
	}

	public void fillInfo() {
		trailName.setText(pickedHike.getTrailName());
		hikeDuration.setText(pickedHike.getHikeDuration());
		distanceHiked.setText(pickedHike.getDistanceHiked());
		averagePace.setText(pickedHike.getAveragePace());
		
		dateStart.setPromptText(pickedHike.getDateAndTimeStarted());
		dateEnd.setPromptText(pickedHike.getDateAndTimeEnded())
		;
	}
	
	public void changeSceneAccountInfo(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/ViewAccount.fxml"));
		Scene scene = new Scene(root);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		users = Data.getUsers();
		pickedHike = Data.getPickedHike();
		fillInfo();
		//stage = Main.getStage();
		File[] listOfFiles = new File[pickedHike.getPicturesTaken().size()];

		for (int i = 0; i < pickedHike.getPicturesTaken().size(); i++) {
			listOfFiles[i] = new File(pickedHike.getPicturesTaken().get(i));
		}

		for (final File file : listOfFiles) {
			ImageView imageView;
			imageView = createImageView(file);
			tile.getChildren().addAll(imageView);
		}

	}

}
