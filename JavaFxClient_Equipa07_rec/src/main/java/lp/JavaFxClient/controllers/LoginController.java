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

//
public class LoginController {

    @FXML
    private TextField txt_user;

    @FXML
    private PasswordField txt_pass;
    
    @FXML
    private Button btn_login;
    
    @FXML
    private Label label_sign;
    
    @FXML
    private void initializeSignUp() {
        label_sign.setOnMouseClicked(event -> openSignUp());
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
        		show("Welcome to BudgetBuddy! 😊");
            openMenu();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid login.");
            alert.setContentText("Username or password is incorrect.");
            alert.show();
        }
    }
    
    private void openMenu() {
    	try {
    		 FXMLLoader loader = new
    	     FXMLLoader(getClass().getResource("/menu.fxml"));
    		 Parent root = loader.load();
    		 
    		 Stage stage = new Stage();
    		 stage.setTitle("Main menu");
    		 stage.setScene(new Scene(root));
    		 stage.show();

    		 Stage loginWindow = (Stage) txt_user.getScene().getWindow();
    		 loginWindow.close();
    		 
    		 } catch (Exception e) {
    		 e.printStackTrace();
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
	    }
	}
	
	private void show(String title) {
		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
		 alert.setTitle(title);
		 alert.setHeaderText(null);
		 alert.showAndWait();	
    }
}

