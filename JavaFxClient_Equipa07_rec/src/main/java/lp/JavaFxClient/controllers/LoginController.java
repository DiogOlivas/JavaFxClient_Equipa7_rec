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

public class LoginController {
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
	 private TextField txt_pass;
		 
	 @FXML
	 private void onLogin() {
		 String user = txt_user.getText();
		 String pass = txt_pass.getText();
		 
		 
		 //in case no account exists
		 //if(){
		 show("Login inválido", "");
		 //else{
	 }
		 
	 private void abrirMenu() {
		 try {
			 FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
			 Parent root = loader.load();

			 Stage stage = (Stage) txt_user.getScene().getWindow();
			 stage.setScene(new Scene(root));
			 stage.setTitle("Main");

		 } catch (Exception e) {
			 e.printStackTrace();
		 }	 
	 }
}
