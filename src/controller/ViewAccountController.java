package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Data;
import model.Hike;
import model.Role;
import model.User;

public class ViewAccountController implements Initializable{

    @FXML
    private TextField firstName1 ,lastName1, phoneNumber1, address1, userName1;
    @FXML
    private PasswordField password1;
    @FXML
    private ImageView profilePicture1;

    @FXML
    private TableView<Hike> historyTable;

    @FXML
    private TableColumn<Hike,String> trailName, dateStarted, dateEnded;
    
   
    
    private User loggedUser;
    private ObservableList<Hike> hikingHistory;
    
    
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

    @FXML
    void selectPicture(ActionEvent event) {

    }

    @FXML
    void updateAccount(ActionEvent event) {

    }
    
    
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loggedUser = Data.getLoggedUser();
		hikingHistory = FXCollections.observableArrayList(loggedUser.getHistoryMap());
		
		trailName.setCellValueFactory(new PropertyValueFactory<Hike, String>("trailName"));
		dateStarted.setCellValueFactory(new PropertyValueFactory<Hike, String>("dateAndTimeStarted"));
		dateEnded.setCellValueFactory(new PropertyValueFactory<Hike, String>("dateAndTimeEnded"));

		historyTable.setItems(hikingHistory);
		
		
	}

	
}
