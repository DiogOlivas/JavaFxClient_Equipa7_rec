package lp.JavaFxClient.controllers;

<<<<<<< HEAD
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
=======
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lp.JavaFxClient_Equipa07_rec.SpringContext;

@Component
public class LoginController {

    @FXML
    private TextField txtUser;

    @FXML
    private PasswordField txtPass;

    private final AuthenticationManager authenticationManager;

    public LoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    
    @FXML
    private void onLogin() {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    txtUser.getText(),
                    txtPass.getText()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(auth);
            abrirMenu();

        } catch (AuthenticationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Invalid login");
            alert.setContentText("Incorrect user or passsword.");
            alert.show();
        }
    }
    
    private void abrirMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu.fxml"));
            loader.setControllerFactory(SpringContext.getContext()::getBean);

            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Main menu");
            stage.setScene(new Scene(root));
            stage.show();

            Stage loginStage = (Stage) txtUser.getScene().getWindow();
            loginStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
>>>>>>> branch 'main' of https://github.com/DiogOlivas/JavaFxClient_Equipa7_rec
}
