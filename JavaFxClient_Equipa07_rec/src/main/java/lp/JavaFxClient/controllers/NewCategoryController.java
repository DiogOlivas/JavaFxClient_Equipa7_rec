package lp.JavaFxClient.controllers;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import lp.JavaFxClient.model.CategoryDTO;
import lp.JavaFxClient.services.ApiService;
import lp.JavaFxClient_Equipa07_rec.UserSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NewCategoryController {  

    @FXML private TextField txt_name;
    @FXML private TextField txt_budget;
    @FXML private TextField txt_description;

    @FXML private Label lbl_cancel;

    @FXML private Button btn_save;


    private final ApiService api = new ApiService();
    private Long editingId = null;

    @FXML
    public void initialize() {

        lbl_cancel.setCursor(Cursor.HAND);
        lbl_cancel.setOnMouseClicked(event -> onCancel(null));

        btn_save.setOnAction(e -> onSave());
        
    }
    public void loadCategory(CategoryDTO c){
        editingId = c.getId();
        txt_name.setText(c.getName());
        txt_budget.setText(String.valueOf(c.getBudget()));
        txt_description.setText(c.getDesc());
    }
    @FXML
    public void onSave(){
        try{
            if (txt_name.getText().isBlank() || txt_budget.getText().isBlank()) {
            new Alert(AlertType.WARNING, "Name and Budget are required.").showAndWait();
            return;
        }

        double budget;
        try {
            budget = Double.parseDouble(txt_budget.getText());
        } catch (NumberFormatException ex) {
            new Alert(AlertType.ERROR, "Budget has to be a valid number.").showAndWait();
            return;
        }

            CategoryDTO dto = new CategoryDTO();
            dto.setName(txt_name.getText());
            dto.setUserId(UserSession.getInstance().getCurrentUserId());
            dto.setDesc(txt_description.getText());
            dto.setBudget(budget);


            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(dto);
            String result;

            if(editingId == null){
                result = api.post("/categories",json);
            }else{
                dto.setId(editingId);
                result = api.put("/categories/" + editingId, json);
            }

            //new Alert(Alert.AlertType.INFORMATION, result).showAndWait();
            txt_name.getScene().getWindow().hide();

        } catch (Exception e ){
            new Alert(AlertType.ERROR, "Error: "+ e.getMessage()).showAndWait();
        }
    }
    
    @FXML
    public void onCancel(MouseEvent event){
        txt_name.getScene().getWindow().hide();
    }

}