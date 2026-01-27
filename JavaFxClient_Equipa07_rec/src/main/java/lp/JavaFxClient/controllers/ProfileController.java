package lp.JavaFxClient.controllers;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	
	Long userId = UserSession.getInstance().getCurrentUserId();
	String json = api.get("/users/" + userId);
	
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
	    
	    bt_budget.setOnMouseClicked(event -> editBudget());
	    bt_email.setOnMouseClicked(event -> editEmail());
	}
	
	@FXML
	public void editEmail() {
		UserDTO user;
		try {
			user = mapper.readValue(json, UserDTO.class);
			if (user == null) {
		        show("Error", "User not loaded.");
		        return;
		    }

		    String newEmail;
		    newEmail = txt_email.getText();

		    if (newEmail.equals(user.getEmail())) {
		        show("Attention!", "Email must be different to save.");
		        return;
		    }

		    user.setEmail(newEmail);
		    show("Success", "Email updated locally.");
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void editBudget() {
		UserDTO user;
		try {
			user = mapper.readValue(json, UserDTO.class);
			if (user == null) {
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

		    if (Double.compare(newBudget,user.getBudget()) == 0) {
		        show("Attention!", "Budget must be different to save.");
		        return;
		    }

		    user.setBudget(newBudget);
		    show("Success", "Budget updated locally.");
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
    public void cancelChange() {
		paneView(null);
		txt_oldPass.clear();
        txt_newPass.clear();
    }
}
