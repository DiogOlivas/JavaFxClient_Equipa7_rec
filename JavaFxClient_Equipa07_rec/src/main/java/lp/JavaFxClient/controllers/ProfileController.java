package lp.JavaFxClient.controllers;


import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import lp.JavaFxClient.services.ApiService;

public class ProfileController {
	private final ApiService api = new ApiService();

	@FXML
	private Pane pane_pass;
	
	@FXML
	private TextField txt_oldPass;
	
	@FXML
	private TextField txt_newPass;
	
	@FXML
	private Button bt_changePass;
	
	@FXML
	private Button bt_cancel;
	
	@FXML
	private Label lbl_changePass;
	
	@FXML
	private TextField txt_name;
	
	@FXML
	private TextField txt_email;
	
	@FXML
	private TextField txt_budget;
	
	private void show(String title, String text) {
		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
		 alert.setTitle(title);
		 alert.setHeaderText(null);
		 alert.setContentText(text);
		 alert.showAndWait();	
	 }
	
	@FXML
	public void changePassword() {

	    String oldPass = txt_oldPass.getText();
	    String newPass = txt_newPass.getText();

	    if (oldPass.isBlank() || newPass.isBlank()) {
	        show("Attention!", "All Fields Are Required!");
	        return;
	    }

	    if (oldPass.equals(newPass)) {
	        show("Attention!", "The new password must be different from the old one!");
	        return;
	    }

	    try {
	        String json = """
	            {
	              "oldPassword": "%s",
	              "newPassword": "%s"
	            }
	            """.formatted(oldPass, newPass);

	        api.post("/users/change-password", json);

	        txt_oldPass.clear();
	        txt_newPass.clear();
	        paneView();
	        
	    } catch (Exception e) {
	        show("Warning!", "Error while trying to change password, please try again.");
	        e.printStackTrace();
	    }
	}

	@FXML
	public void paneView() {
		if(pane_pass.isVisible() == true){
			pane_pass.setVisible(false);
		}else {
			pane_pass.setVisible(true);	
		}
	}
	
	@FXML
    public void cancelChange() {
		paneView();
		txt_oldPass.clear();
        txt_newPass.clear();
    }
}
