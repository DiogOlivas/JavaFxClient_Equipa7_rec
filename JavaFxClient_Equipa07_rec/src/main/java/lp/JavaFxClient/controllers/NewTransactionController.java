package lp.JavaFxClient.controllers;


import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import lp.JavaFxClient.model.TransactionDTO;
import lp.JavaFxClient.services.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Cursor;


public class NewTransactionController {
    @FXML private Label formTitle;
    @FXML private TextField txt_value;
    @FXML private TextField txt_desc;
    @FXML private TextField txt_pay;
    @FXML private TextField txt_date;

     @FXML private Label lbl_cancel;

    private final ApiService api = new ApiService();
    private Long editingId = null;

    @FXML
    public void initialize() {
        formTitle.setText("New Transaction");
        lbl_cancel.setOnMouseClicked(event -> onCancel());
    }

    public void loadTransaction(TransactionDTO t){
        editingId = t.getId();
        formTitle.setText("Edit transaction");
        txt_value.setText(String.valueOf(t.getValue()));
        txt_desc.setText(t.getDescription());
        txt_pay.setText(t.getPaymentMethod());
        txt_date.setText(t.getDate());
    }

    @FXML
    public void onSave(){
        try{
            TransactionDTO dto = new TransactionDTO();
            dto.setValue(Double.parseDouble(txt_value.getText()));
            dto.setDescription(txt_desc.getText());
            dto.setPaymentMethod(txt_pay.getText());
            dto.setDate(txt_date.getText());

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
            txt_value.getScene().getWindow().hide();

        } catch (Exception e ){
            new Alert(AlertType.ERROR, "Error: "+ e.getMessage()).showAndWait();
        }
    }
 
    @FXML
    public void onCancel(){
        txt_value.getScene().getWindow().hide();
    }
}