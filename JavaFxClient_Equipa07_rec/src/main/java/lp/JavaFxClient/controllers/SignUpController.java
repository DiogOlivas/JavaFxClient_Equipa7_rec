package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient.services.ApiService;

public class SignUpController {
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
	 public void onCreateUser() {
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
	  	   	 	show("Welcome to BudgetBuddy! 😊", api.post("/User", json));
	  	   	
	  	   	 	try {
	  	   	 		FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
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
}
