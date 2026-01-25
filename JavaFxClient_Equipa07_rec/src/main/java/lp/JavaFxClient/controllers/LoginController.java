package lp.JavaFxClient.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient_Equipa07_rec.AuthClient;
import lp.JavaFxClient_Equipa07_rec.UserSession;

//
public class LoginController {

    @FXML
    private TextField txt_user;

    @FXML
    private PasswordField txt_pass;
    
    @FXML
    private Button btn_login;
    
    @FXML
    private Label lbl_sign;
    
    private void message(String title) {
		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
		 alert.setTitle(title);
		 alert.setHeaderText(null);
		 alert.showAndWait();	
   }
    
    private void show(String title, String text) {
   	 Alert alert = new Alert(Alert.AlertType.INFORMATION);
   	 alert.setTitle(title);
   	 alert.setHeaderText(null);
   	 alert.setContentText(text);
   	 alert.showAndWait();	
    }
    
    @FXML
    private void initialize() {
    	    txt_user.clear();
        txt_pass.clear();
    
        lbl_sign.setOnMouseClicked(event -> openSignUp());
    }
    
    @FXML
    private void initializeMenu() {
    		btn_login.setOnMouseClicked(event -> onLogin());
    }
    
    @FXML
    private void onLogin() {
        String username = txt_user.getText();	
        String password = txt_pass.getText();

        boolean success = AuthClient.login(username, password);
        if (success) {
            UserSession.getInstance().setCurrentUser(username);

            message("Welcome to BudgetBuddy! 😊");
            openMain();
        } else {
        	Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid login.");
            alert.setContentText("Username or password is incorrect.");
            alert.show();
        }
    }
    
    private void openMain() {
    	try {   		 
            FXMLLoader loader = new
    	    FXMLLoader(getClass().getResource("/main.fxml"));
    		Parent root = loader.load();
    		
    		Stage stage = new Stage();
    		stage.setTitle("Main");
    		stage.setScene(new Scene(root));
    		stage.show();

    		Stage loginWindow = (Stage) txt_user.getScene().getWindow();
    		loginWindow.close(); 
    	} catch (Exception e) {
    		 e.printStackTrace();
    		 show("Error", "An unexpected error has occured, please try again.");
    	}	
    }
    
	private void openSignUp() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/SignUp.fxml"));
	        Parent root = loader.load();

	        Stage stage = (Stage) txt_user.getScene().getWindow();
	        stage.setScene(new Scene(root));
	  
	        stage.show();
	    } catch (IOException e) {
	        e.printStackTrace();
   		 	show("Error", "An unexpected error has occured, please try again.");
	    }
	}

}

