package lp.JavaFxClient.controllers;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import lp.JavaFxClient.model.CategoryDTO;
import lp.JavaFxClient.services.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class NewCategoryController {  

    @FXML private Label formTitle;
    @FXML private TextField txtName;
    @FXML private TextField txtBudget;
    @FXML private TextField txtDescription;
    @FXML private Label lbl_cancel;

    private final ApiService api = new ApiService();
    private Long editingId = null;

    @FXML
    public void initialize() {
        formTitle.setText("New Category");

        lbl_cancel.setCursor(Cursor.HAND);
        lbl_cancel.setOnMouseClicked(event -> onCancel());
        
    }
    public void loadCategory(CategoryDTO c){
        editingId = c.getId();
        formTitle.setText("Edit Category");
        txtName.setText(c.getName());
        txtBudget.setText(String.valueOf(c.getBudget()));
        txtDescription.setText(c.getDescription());
    }
    @FXML
    public void onSave(){
        try{
            CategoryDTO dto = new CategoryDTO();
            dto.setName(txtName.getText());
            dto.setBudget(Double.parseDouble(txtBudget.getText()));
            dto.setDescription(txtDescription.getText());

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(dto);
            String result;

            if(editingId == null){
                result = api.post("/category",json);
            }else{
                dto.setId(editingId);
                result = api.put("/category/" + editingId, json);
            }

            new Alert(Alert.AlertType.INFORMATION, result).showAndWait();
            txtName.getScene().getWindow().hide();

        } catch (Exception e ){
            new Alert(AlertType.ERROR, "Error: "+ e.getMessage()).showAndWait();
        }
    }
    
    @FXML
    public void onCancel(){
        txtName.getScene().getWindow().hide();
    }
}