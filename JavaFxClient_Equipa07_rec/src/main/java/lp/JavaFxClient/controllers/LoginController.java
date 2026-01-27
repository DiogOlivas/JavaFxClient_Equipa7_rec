package lp.JavaFxClient.controllers;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lp.JavaFxClient_Equipa07_rec.AuthClient;
import lp.JavaFxClient_Equipa07_rec.UserSession;

public class LoginController {

    @FXML
    private TextField txt_user;

    @FXML
    private PasswordField txt_pass;
    
    @FXML
    private Button btn_login;
    
    @FXML
    private Label lbl_sign;
    
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
    
        //lbl_sign.setOnMouseClicked(event -> openSignUp(MouseEvent));
    }
    /**
    @FXML
    private void initializeMenu() {
    		btn_login.setOnMouseClicked(event -> onLogin());
    }
    **/
    @FXML
    private void onLogin() {
        String username = txt_user.getText();	
        String password = txt_pass.getText();

        String response = AuthClient.login(username, password);
        if (response != "Error") {

        	try {

	        	ObjectMapper mapper = new ObjectMapper();
    	    		JsonNode node = mapper.readTree(response);
	            
    	    		long id = node.get("id").asLong();
        		String user = node.get("username").asText();
        	
       	 	UserSession.getInstance().setCurrentUser(user);
            	UserSession.getInstance().setCurrentUserId(id);	
            	
            	show("Login successful!", "Welcome to BudgetBuddy! 😊");
                //String user = UserSession.getInstance().getCurrentUser();
                openMain();
        	}catch(IOException e) {
        		e.printStackTrace();
        		
        	}
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
    
    @FXML
	private void openSignUp(MouseEvent event) {
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

