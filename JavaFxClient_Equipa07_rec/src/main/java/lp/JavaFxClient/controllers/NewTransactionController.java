package lp.JavaFxClient.controllers;


import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.DatePicker;
import lp.JavaFxClient.model.CategoryDTO;
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
     
     @FXML private ComboBox<CategoryDTO> cb_category; 
     
     private Long editingTransactionUserId = null;

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
        loadCategoriesToComboBox();
    }

    public void loadTransaction(TransactionDTO t){
        editingId = t.getId();
        editingTransactionUserId = t.getUserId();
        txt_value.setText(String.valueOf(t.getValue()));
        txt_desc.setText(t.getDescription());
        txt_pay.setText(t.getPaymentMethod());
        dt_date.setValue(t.getDate());

        if (t.getCategoryId() != null) {
            for (CategoryDTO c : cb_category.getItems()) {
                if (c.getId().equals(t.getCategoryId())) {
                    cb_category.getSelectionModel().select(c);
                    break;
                }
            }
        }
    }

    @FXML
    public void onSave() {
        try {
            if (txt_value.getText().isBlank() || txt_desc.getText().isBlank() || dt_date.getValue() == null) {
                show("Warning", "Value, Description, and Date are required.");
                return;
            }

            CategoryDTO selectedCategory = cb_category.getSelectionModel().getSelectedItem();
            if (selectedCategory == null) {
                show("Warning", "Please select a category.");
                return;
            }
    	    
    	    if(Double.parseDouble(txt_value.getText()) < 0) {
                show("Attention!", "Budget must be a positive value.");
            return;
    	    }

            TransactionDTO dto = new TransactionDTO();
            dto.setValue(Double.parseDouble(txt_value.getText()));
            dto.setDescription(txt_desc.getText());
            dto.setPaymentMethod(txt_pay.getText());
            dto.setDate(dt_date.getValue());
            dto.setUserId(UserSession.getInstance().getCurrentUserId());
            dto.setCategoryId(selectedCategory.getId());

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            String json = mapper.writeValueAsString(dto);
            String result;

            if (editingId == null) {
                dto.setUserId(UserSession.getInstance().getCurrentUserId());
                result = api.post("/transactions", json);
            } else {
                dto.setId(editingId);
                result = api.put("/transactions/" + editingId, json);
            }

            if (result.startsWith("ERROR")) {
                show("Error", "Failed to save transaction:\n" + result);
                return;
            }
            
            show("Success", editingId == null ? "Transaction added!" : "Transaction updated!");
            txt_value.getScene().getWindow().hide();
            
        } catch (NumberFormatException nfe) {
            show("Error", "Value must be a number.");
        } catch (Exception e) {
            show("Error", "Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

 
    private void loadCategoriesToComboBox() {
	    try {
	        String json = api.get("/categories");
            ObjectMapper mapper = new ObjectMapper();
	        CategoryDTO[] categories = mapper.readValue(json, CategoryDTO[].class);

	        ObservableList<CategoryDTO> categoryList = FXCollections.observableArrayList(categories);
	        cb_category.setItems(categoryList);

	        cb_category.setCellFactory(c -> new ListCell<>() {
	            @Override
	            protected void updateItem(CategoryDTO item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty || item == null ? null : item.getName());
	            }
	        });

	        cb_category.setButtonCell(new ListCell<>() {
	            @Override
	            protected void updateItem(CategoryDTO item, boolean empty) {
	                super.updateItem(item, empty);
	                setText(empty || item == null ? null : item.getName());
	            }
	        });

	    } catch (Exception e) {
	        e.printStackTrace();
	        show("Error", "Failed to load categories: " + e.getMessage());
	    }
	}

    @FXML
    public void onCancel(MouseEvent event){
        txt_value.getScene().getWindow().hide();
    }
}