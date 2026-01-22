package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient_Equipa07_rec.AuthClient;


public class LoginController {

    @FXML
    private TextField txt_user;

    @FXML
    private PasswordField txt_pass;

    @FXML
    private void onLogin() {
    	 String username = txt_user.getText();
    	    String password = txt_pass.getText();

    	    boolean success = AuthClient.login(username, password);

    	    if (success) {
    	        openMenu();
    	    } else {
    	        Alert alert = new Alert(Alert.AlertType.ERROR);
    	        alert.setHeaderText("Invalid login");
    	        alert.setContentText("Username or password is incorrect");
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
    }
