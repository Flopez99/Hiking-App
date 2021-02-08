package application;


import java.util.TreeMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Data;
import model.User;

public class Main extends Application {
	public static Data data;
	private static TreeMap<String, User> users;

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		data = new Data();
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/TrailRegister.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("World of Hiking");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static Data getData() {
		return data;
	}
}
