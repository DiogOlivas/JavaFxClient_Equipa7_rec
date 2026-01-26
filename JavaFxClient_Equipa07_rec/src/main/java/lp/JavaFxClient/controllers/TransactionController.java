package lp.JavaFxClient.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lp.JavaFxClient.model.TransactionDTO;
import lp.JavaFxClient.services.ApiService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransactionController {

    @FXML private TableView<TransactionDTO> transactionTable;

    @FXML private TableColumn<TransactionDTO, Long> idCol;
    @FXML private TableColumn<TransactionDTO, String> destinationCol;
    @FXML private TableColumn<TransactionDTO, String> dateCol;
    @FXML private TableColumn<TransactionDTO, Double> valueCol;
    @FXML private TableColumn<TransactionDTO, String> payemntMethodCol;
    @FXML private TableColumn<TransactionDTO, String> categoryCol;
    @FXML private TableColumn<TransactionDTO, String> descriptionCol;

    private final ApiService api = new ApiService();
    private final ObjectMapper mapper = new ObjectMapper();

    private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erro");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    }

    private void showInfo(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Informação");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
    }

    @FXML
    public void initialize(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        destinationCol.setCellValueFactory(new PropertyValueFactory<>("Destination"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("Date"));
        valueCol.setCellValueFactory(new PropertyValueFactory<>("Value"));
        payemntMethodCol.setCellValueFactory(new PropertyValueFactory<>("PaymentMethod"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("Category"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Description"));

        loadTransaction();
    }
    @FXML
    public void onRefresh(){
        loadTransaction();
    }

    private void loadTransaction(){
        try{
            String json = api.get("/transaction");
            if (json.startsWith("ERROR:")){
                showError(json);
                return;
            }
            var transactionList = mapper.readValue(json, new TypeReference<java.util.List<TransactionDTO>>(){});
            transactionTable.getItems().setAll(transactionList);
        } catch (Exception e) {
            showError("Error loading transactions: " + e.getMessage());
        }
    }
    @FXML
    public void onAddTransaction(){
        openTransactionForm(null);
    }

    @FXML
    public void onEditTransaction(){
        TransactionDTO selected = transactionTable.getSelectionModel().getSelectedItem();

        if(selected == null){
            showError("Select a student first");
            return;
        }
        openTransactionForm(selected);
    }

    @FXML
    public void onDeleteTransaction(){
        TransactionDTO selected = transactionTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            showError("Select a transaction first.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,"Delete transaction " + selected.getId() + "?",ButtonType.YES,ButtonType.NO);
        confirm.showAndWait();
        if(confirm.getResult() != ButtonType.YES) return;
        String result = api.delete("/transactions/" + selected.getId());
        showInfo("Delete result", result);
        loadTransaction();

    }
    private void openTransactionForm(TransactionDTO transaction){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transaction-form.fxml"));
            Parent root = loader.load();

            TransactionFormController controller = loader.getController();
            controller.setTransaction(transaction);
            //Edit mode
            if(transaction != null){
                controller.loadTransactionData();
            }
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setTitle(transaction == null ? "Create Transaction": "Edit Transaction");
            stage.showAndWait();

            loadTransaction(); //reload after creating/editing
        } catch (Exception e){
            showError("Cannot open form: " + e.getMessage());
        }
    }
}
