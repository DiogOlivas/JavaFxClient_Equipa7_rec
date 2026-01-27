package lp.JavaFxClient.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lp.JavaFxClient.model.UserDTO;
import lp.JavaFxClient.services.ApiService;
import lp.JavaFxClient_Equipa07_rec.UserSession;

public class ProfileController {
	private final ApiService api = new ApiService();
	ObjectMapper mapper = new ObjectMapper();
	
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
	public void initialize() {
	    String userId = UserSession.getInstance().getCurrentUser();
	    try {
	        String json = api.get("/users/" + userId);
	        if (!json.startsWith("ERROR:")) {
	            UserDTO user = mapper.readValue(json, UserDTO.class);
	            
	            txt_name.setText(user.getUsername());
	            txt_email.setText(user.getEmail());
	            txt_budget.setText(String.valueOf(user.getBudget()));
	        } else {
	            show("Error while loadin profile: " ,json);
	        }
	    } catch (Exception e) {
	        show("Error: " , e.getMessage());
	    }
	}


	@FXML
	public void changePassword() {

	    String username = UserSession.getInstance().getCurrentUser();
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
	        paneView(null);
	        
	    } catch (Exception e) {
	        show("Warning!", "Error while trying to change password, please try again.");
	        e.printStackTrace();
	    }
	}

	@FXML
	public void paneView(MouseEvent event) {
		if(pane_pass.isVisible() == true){
			pane_pass.setVisible(false);
		}else {
			pane_pass.setVisible(true);	
		}
	}
	
	@FXML
    public void cancelChange() {
		paneView(null);
		txt_oldPass.clear();
        txt_newPass.clear();
    }
}
