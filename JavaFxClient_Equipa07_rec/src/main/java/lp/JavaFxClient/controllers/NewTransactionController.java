package lp.JavaFxClient.controllers;


import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import lp.JavaFxClient.model.TransactionDTO;
import lp.JavaFxClient.services.ApiService;
import lp.JavaFxClient_Equipa07_rec.UserSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class NewTransactionController {
    @FXML private TextField txt_value;
    @FXML private TextField txt_desc;
    @FXML private TextField txt_pay;
    @FXML private DatePicker dt_date;

     @FXML private Label lbl_cancel;
     
     @FXML private Button btn_save;

    private final ApiService api = new ApiService();
    private Long editingId = null;
    
    private void show(String title, String text) {
      	 Alert alert = new Alert(Alert.AlertType.INFORMATION);
      	 alert.setTitle(title);
      	 alert.setHeaderText(null);
      	 alert.setContentText(text);
      	 alert.showAndWait();	
       }

    @FXML
    public void initialize() {
        lbl_cancel.setOnMouseClicked(event -> onCancel(null));
    }

    public void loadTransaction(TransactionDTO t){
        editingId = t.getId();
        txt_value.setText(String.valueOf(t.getValue()));
        txt_desc.setText(t.getDescription());
        txt_pay.setText(t.getPaymentMethod());
    }

    @FXML
    public void onSave(){
        try{
            TransactionDTO dto = new TransactionDTO();
            dto.setValue(Double.parseDouble(txt_value.getText()));
            dto.setDescription(txt_desc.getText());
            dto.setPaymentMethod(txt_pay.getText());
            dto.setDate(dt_date.getValue());
            dto.setUserId(UserSession.getInstance().getCurrentUserId());

            String result;

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            
            String json = mapper.writeValueAsString(dto);
            
            if(editingId == null){
                result = api.post("/transactions",json);
            }else{
                dto.setId(editingId);
                result = api.put("/transactions/" + editingId, json);
            }

            show("Success!","The new transaction has been added!");
            ///new Alert(Alert.AlertType.INFORMATION, result).showAndWait();
            /// ACHO QUE NAO APARECE NADA NA MENSAGEM
            txt_value.getScene().getWindow().hide();

        } catch (Exception e ){
            new Alert(AlertType.ERROR, "Error: "+ e.getMessage()).showAndWait();
        }
    }
 
    @FXML
    public void onCancel(MouseEvent event){
        txt_value.getScene().getWindow().hide();
    }
}