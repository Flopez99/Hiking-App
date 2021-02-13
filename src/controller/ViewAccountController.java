package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import model.Data;
import model.Hike;
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
    void changeSceneUserLoggedIn(ActionEvent event) {

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
