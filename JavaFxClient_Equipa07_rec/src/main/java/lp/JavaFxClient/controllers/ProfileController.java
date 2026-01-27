package lp.JavaFxClient.controllers;




import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
	private Button bt_email;
	
	@FXML
	private Button bt_budget;
	
	@FXML
	private Label lbl_changePass;
	
	@FXML
	private TextField txt_name;
	
	@FXML
	private TextField txt_email;
	
	@FXML
	private TextField txt_budget;
	
	private UserDTO currentUser;
	
	private void show(String title, String text) {
		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
		 alert.setTitle(title);
		 alert.setHeaderText(null);
		 alert.setContentText(text);
		 alert.showAndWait();	
	 }
	
	@FXML
	public void initialize() {
	    try {
	        Long userId = UserSession.getInstance().getCurrentUserId();
	        String json = api.get("/users/" + userId);

	        if (json.startsWith("ERROR")) {
	            show("Error while loading profile", json);
	            return;
	        }

	        currentUser = mapper.readValue(json, UserDTO.class);

	        txt_name.setText(currentUser.getUsername());
	        txt_email.setText(currentUser.getEmail());
	        txt_budget.setText(String.valueOf(currentUser.getBudget()));

	    } catch (Exception e) {
	        e.printStackTrace();
	        show("Error", e.getMessage());
	    }

	    bt_budget.setOnMouseClicked(e -> editBudget());
	    bt_email.setOnMouseClicked(e -> editEmail());
	}
	
	@FXML
	public void editEmail() {
	    if (currentUser == null) {
	        show("Error", "User not loaded.");
	        return;
	    }

	    String newEmail = txt_email.getText().trim();

	    if (newEmail.isBlank()) {
	        show("Attention!", "Email cannot be empty.");
	        return;
	    }

	    if (newEmail.equals(currentUser.getEmail())) {
	        show("Attention!", "Email must be different to save.");
	        return;
	    }

	    try {
	        String body = """
	            { "newEmail": "%s" }
	            """.formatted(newEmail);

	        String response = api.post("/users/change-email", body);

	        if (response.startsWith("ERROR")) {
	            show("Error", response);
	            return;
	        }

	        currentUser.setEmail(newEmail);
	        show("Success", "Email updated successfully.");

	    } catch (Exception e) {
	        e.printStackTrace();
	        show("Error", "Failed to update email.");
	    }
	}

	
	@FXML
	public void editBudget() {
	    if (currentUser == null) {
	        show("Error", "User not loaded.");
	        return;
	    }

	    double newBudget;
	    try {
	        newBudget = Double.parseDouble(txt_budget.getText());
	    } catch (NumberFormatException e) {
	        show("Error", "Invalid budget value.");
	        return;
	    }

	    if (Double.compare(newBudget, currentUser.getBudget()) == 0) {
	        show("Attention!", "Budget must be different to save.");
	        return;
	    }

	    try {
	        String body = """
	            { "newBudget": %.2f }
	            """.formatted(newBudget);

	        String response = api.post("/users/change-budget", body);

	        if (response.startsWith("ERROR")) {
	            show("Error", response);
	            return;
	        }

	        currentUser.setBudget(newBudget);
	        show("Success", "Budget updated successfully.");

	    } catch (Exception e) {
	        e.printStackTrace();
	        show("Error", "Failed to update budget.");
	    }
	}


	@FXML
	public void changePassword() {
	    Long user = UserSession.getInstance().getCurrentUserId();
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
	        		  "userId": "%d",	
	              "oldPassword": "%s",
	              "newPassword": "%s"
	            }
	            """.formatted(user, oldPass, newPass);

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
    private void backToLogin(MouseEvent event) {
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
               Parent root = loader.load();

               Stage stage = (Stage) lbl_logout.getScene().getWindow();
               stage.setScene(new Scene(root));

               stage.show();
           } catch (IOException e) {
               e.printStackTrace();
                   show("Error", "An unexpected error has occured, please try again.");
           }
    }
	@FXML
    public void cancelChange() {
		paneView(null);
		txt_oldPass.clear();
        txt_newPass.clear();
    }
}
