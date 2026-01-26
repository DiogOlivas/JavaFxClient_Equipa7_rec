package lp.JavaFxClient.controllers;

import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import lp.JavaFxClient.model.CategoryDTO;
import lp.JavaFxClient.services.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;


public class EditCategoryController {
    @FXML private Label formTitle;
    @FXML private TextField txtName;
    @FXML private TextField txtBudget;
    @FXML private TextField txtDescription;

    private final ApiService api = new ApiService();
    private Long editingId = null;

    public void loadCategory(CategoryDTO c){
        editingId = c.getId();
        formTitle.setText("New Category");
        txtName.setText(c.getNome());
        txtBudget.setText(String.valueOf(c.getBudget()));
        txtDescription.setText(c.getDescricao());
    }
    
    @FXML
    public void onSave(){
        try{
            CategoryDTO dto = new CategoryDTO();
            dto.setNome(txtName.getText());
            dto.setBudget(Double.parseDouble(txtBudget.getText()));
            dto.setDescricao(txtDescription.getText());

            String result;

            if(editingId == null){
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(dto);
                result = api.post("/category",json);
            }else{
                dto.setId(editingId);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(dto);
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
