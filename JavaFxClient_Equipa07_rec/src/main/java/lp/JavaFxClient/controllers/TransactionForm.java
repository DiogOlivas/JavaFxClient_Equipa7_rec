package lp.JavaFxClient.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import lp.JavaFxClient.model.TransactionDTO;
import lp.JavaFxClient.services.ApiService;
 
public class TransactionForm {
    @FXML private Label formTitle;
    @FXML private TextField txtValue;
    @FXML private TextField txtDescription;
    @FXML private TextField txtPayementMethod;
    @FXML private TextField txtDate;

    private final ApiService api = new ApiService();
    private Long editingId = null;

    public void loadTransaction(TransactionDTO t){
        editingId = t.getId();
        formTitle.setText("Edit transaction");
        txtValue.setText(String.valueOf(t.getValue()));
        txtDescription.setText(t.getDescription());
        txtPayementMethod.setText(t.getPaymentMethod());
        txtDate.setText(t.getDate());


    }

    @FXML
    public void onSave(){
        try{
            TransactionDTO dto = new TransactionDTO();
            dto.setValue(Double.parseDouble(txtValue.getText()));
            dto.setDescription(txtDescription.getText());
            dto.setPaymentMethod(txtPayementMethod.getText());
            dto.setDate(txtDate.getText());

            String result;

            if(editingId == null){
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(dto);
                result = api.post("/transaction",json);
            }else{
                dto.setId(editingId);
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(dto);
                result = api.put("/transaction/" + editingId, json);
            }

            new Alert(Alert.AlertType.INFORMATION, result).showAndWait();
            txtValue.getScene().getWindow().hide();

        } catch (Exception e ){
            new Alert(AlertType.ERROR, "Error: "+ e.getMessage()).showAndWait();
        }


    }
    @FXML
    public void onCancel(){
        txtValue.getScene().getWindow().hide();
    }
}


