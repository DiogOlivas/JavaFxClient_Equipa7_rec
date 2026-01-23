package lp.JavaFxClient.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lp.JavaFxClient.services.ApiService;

public class SignupController {
	private final ApiService api = new ApiService();

	 private void show(String title, String text) {
		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
		 alert.setTitle(title);
		 alert.setHeaderText(null);
		 alert.setContentText(text);
		 alert.showAndWait();	
	 }
	 
	 @FXML
	 private TextField txt_user;

	 @FXML
	 private TextField txt_email;

	 @FXML
	 private PasswordField txt_pass;
	 
	 @FXML
	 private PasswordField txt_passConf;
	 
	 @FXML
	 private Label lbl_logIn;
	 
	 @FXML
	 public void initialize() {
	        lbl_logIn.setOnMouseClicked(event -> openLogin());
	 }
	 
	 @FXML
	 public void CreateUser() {
		   String username = txt_user.getText();
	       String email = txt_email.getText();
	       String password = txt_pass.getText();
	       String passwordConf = txt_passConf.getText();

	       if (username.isBlank() || email.isBlank() || password.isBlank() || passwordConf.isBlank()) {
	           show("Attention!", "All Fields Are Required!");
	           return;
	       }
	       
	       if(!password.equals(passwordConf)) {
	    	   show("Attention!", "Both The Password And The Confirmation Have To Be Equal!");
	    	   return;
	       }
	       try {
	    	   String json = """
	  	   	 		{
	  	   	 		"name": "%s",
	  	   	 		"email": "%s",
	  	   	 		"password": "%s"
	  	   	 		}
	  	   	 		""".formatted(username, email, password);
	  	   	 	show("Welcome to BudgetBuddy! 😊", api.post("/users", json));
	  	   	
	  	   	 	try {
	  	   	 		FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
	  	   	 		Parent root = loader.load();

	  	   	 		Stage stage = (Stage) txt_user.getScene().getWindow();
	  	   	 		stage.setScene(new Scene(root));
	  	   	 		stage.setTitle("Login");

	  	   	 	} catch (Exception e) {
	  	   	 		show("Error!", "An unknown error has ocurred, please try again.");
	  	   	 		e.printStackTrace();
	  	   	 	}	
	       }catch (Exception e) {
	    	   show("Warning!", "Error while trying to create account, please try again.");
	    	   e.printStackTrace();
	      }
	 }
	 
	 private void openLogin() {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
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
