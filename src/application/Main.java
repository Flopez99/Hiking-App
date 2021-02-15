package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.TreeMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Bag;
import model.Data;
import model.User;
import model.Utilities;

public class Main extends Application {
	public static Data data;
	public static Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		data = new Data();
		
		FileInputStream fis = new FileInputStream("RawData/data.dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Bag b = (Bag) (ois.readObject());
		Data.setUsers(b.getUsers());
		Data.setTrails(b.getTrails());
		ois.close();
		
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("World of Hiking");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		stage = primaryStage;
		primaryStage.show();
	}

	public static Stage getStage() {
		return stage;
	}

	@Override
	public void stop() throws IOException {

		Bag bag = new Bag(Data.getUsers(), Data.getTrails());
		Utilities.writeBinary(bag);
		System.out.println("Program ended");

	}

	public static Data getData() {
		return data;
	}
}
