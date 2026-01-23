package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import lp.JavaFxClient.services.ApiService;
import java.util.Optional;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class MainController {
	private final ApiService api = new ApiService();
	
	//private String ask(String message) {
	//	TextInputDialog dialog = new TextInputDialog();
	//	dialog.setHeaderText(message);
	//	Optional<String> result = dialog.showAndWait();
		
	//	return result.orElse(null);
	//}
	
 private void show(String title, String text) {
	 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	 alert.setTitle(title);
	 alert.setHeaderText(null);
	 alert.setContentText(text);
	 alert.showAndWait();	
 }
 
 @FXML private Label lbl_username;

 @FXML
 private void initialize() {
     // runs automatically when the FXML loads
     lbl_username.setText("Welcome " +  + "!");
     System.out.println("FXML loaded");
 }
 
 /**
 @FXML
 public void onCreateStudent() {
	 String name = ask("Student name:");
	 String email = ask("Email:");
	 String year = ask("Year:");
	 String json = """
	 		{
	 		"name": "%s",
	 		"email": "%s",
	 		"year": %s
	 		}
	 		""".formatted(name, email, year);
	 show("Created Student", api.post("/students", json));
 }
 
 @FXML
 public void onAssignCourse() {
	 String studentId = ask("Student ID:");
	 String courseId = ask("Course ID:");
	 show("Updated Student", api.post("/students/" + studentId + "/courses/" + courseId,null));
 }
 
 @FXML
 public void onRemoveCourse() {
	 String studentId = ask("Student ID:");
	 String courseId = ask("Course ID:");
	 api.delete("/students/" + studentId + "/courses/" + courseId);
	 show("Success", "Course removed from student.");
 }
 
 @FXML
 public void onListStudents() {
	 show("List of Students", api.get("/students"));
 }
 **/
}