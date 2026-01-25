package lp.JavaFxClient.controllers;

import javafx.fxml.FXML;
import lp.JavaFxClient.model.TransactionDTO;
import lp.JavaFxClient.services.ApiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lp.JavaFxClient.model.CategoryDTO;


public class MainController {

	private final ApiService api = new ApiService();
    private void show(String title, String text) {

	 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	 alert.setTitle(title);
	 alert.setHeaderText(null);
	 alert.setContentText(text);
	 alert.showAndWait();	

 }
 
 //Transaction Table
 @FXML private TableView<TransactionDTO> tbl_transactions;
 @FXML private TableColumn<TransactionDTO, Double> tbl_value;
 @FXML private TableColumn<TransactionDTO, String> tbl_desc;
 @FXML private TableColumn<TransactionDTO, String> tbl_pay;
 @FXML private TableColumn<TransactionDTO, String> tbl_date;

 //Category Table
 @FXML private TableView<CategoryDTO> tbl_category;
 @FXML private TableColumn<CategoryDTO, String> tbl_name;

 //butões
    @FXML private Button add_tran; 
    @FXML private Button btnEditTransaction;
    @FXML private Button btnDeleteTransaction;

    @FXML private Button add_cat;
    @FXML private Button btnEditCategory;
    @FXML private Button btnDeleteCategory;

 private final ObjectMapper mapper = new ObjectMapper();

 @FXML
 public void initialize(){
	 tbl_value.setCellValueFactory(new PropertyValueFactory<>("value"));
	 tbl_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
	 tbl_pay.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
	 tbl_date.setCellValueFactory(new PropertyValueFactory<>("date"));

	 tbl_name.setCellValueFactory(new PropertyValueFactory<>("name"));
	 loadTransactions();
	 loadCategories();

 }
 private void loadTransactions(){
	try{
		String json = api.get("/transaction");
		if(json.startsWith("ERROR")){
			show("Error", json);
			return;
		}
		TransactionDTO[] transactions = mapper.readValue(json, TransactionDTO[].class); // Desserializa o JSON em um array de TransactionDTO
		tbl_transactions.getItems().setAll(transactions); // Atualiza a tabela com as transações
	} catch (Exception e){
		show("Error", "Failed to load transactions: " + e.getMessage());
	}
 }
 
private void loadCategories(){
	try{
		String json = api.get("/category");
		if(json.startsWith("ERROR")){
			show("Error", json);
			return;
		}
		CategoryDTO[] category = mapper.readValue(json, CategoryDTO[].class); // Desserializa o JSON em um array de CategoryDTO
		tbl_category.getItems().setAll(category); // Atualiza a tabela com as categorias
	} catch (Exception e){
		show("Error", "Failed to load categories: " + e.getMessage());
	}
}

 @FXML 
 public void onAddTransaction(){
	openTransactionForm(null);
 }
 
 private void openTransactionForm(TransactionDTO transaction){
	try{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/views/NewTransaction.fxml"));
		Parent root = loader.load();

		NewTransactionController controller = loader.getController(); 
		if(transaction != null){
			controller.loadTransaction(transaction);
		}

		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela principal até que esta seja fechada
		stage.setScene(new Scene(root));
		stage.setTitle(transaction == null? "New Transaction" : "Edit Transaction");
		stage.showAndWait();

		loadTransactions();
	} catch (Exception e){
		show("Error", "Failed to open transaction form: " + e.getMessage());
	}
  }
   @FXML
    public void onEditTransaction() {
        TransactionDTO selectedTransaction = tbl_transactions.getSelectionModel().getSelectedItem();
		if (selectedTransaction != null) {
			openTransactionForm(selectedTransaction);
		} else {
			show("Warning", "Please select a transaction to edit.");
		}
	}
	@FXML
	public void onDeleteTransaction() {
		TransactionDTO selectedTransaction = tbl_transactions.getSelectionModel().getSelectedItem();
		if (selectedTransaction != null) {
			try {
				String result = api.delete("/transaction/" + selectedTransaction.getId());
				if (result.startsWith("ERROR")) {
					show("Error", result);
				} else {
					show("Success", "Transaction deleted successfully.");
					loadTransactions();
				}
			} catch (Exception e) {
				show("Error", "Failed to delete transaction: " + e.getMessage());
			}
		} else {
			show("Warning", "Please select a transaction to delete.");
		}
	}

	private void openCategoryForm(CategoryDTO c) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/views/NewCategory.fxml"));
        Parent root = loader.load();

        // Passa os dados para o controller do formulário
        NewCategoryController controller = loader.getController();
        if (c != null) {
            controller.loadCategory(c); // preencher campos existentes
        }

        Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL); // Bloqueia a janela principal até que esta seja fechada
        stage.setScene(new Scene(root));
		stage.setTitle(c == null ? "New Category" : "Edit Category");
        stage.showAndWait();

        // Recarrega categorias depois de salvar
        loadCategories();

    } catch (Exception e) {
        new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
    }
}

	@FXML
	public void onAddCategory(){
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/lp/JavaFxClient/views/NewCategory.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("New Category");
			stage.setScene(new Scene(root));
			stage.showAndWait();
		} catch (Exception e){
			show("Error", "Failed to open category form: " + e.getMessage());
		}
	}
	@FXML
	public void onEditCategory() {
        CategoryDTO selectedCategory = tbl_category.getSelectionModel().getSelectedItem();
		if (selectedCategory != null) {
			openCategoryForm(selectedCategory);
		} else {
			show("Warning", "Please select a category to edit.");
		}
	}
	@FXML
	public void onDeleteCategory() {
		CategoryDTO selectedCategory = tbl_category.getSelectionModel().getSelectedItem();
		if (selectedCategory != null) {
			try {
				String result = api.delete("/category/" + selectedCategory.getId());
				if (result.startsWith("ERROR")) {
					show("Error", result);
				} else {
					show("Success", "Category deleted successfully.");
					loadCategories();
				}
			} catch (Exception e) {
				show("Error", "Failed to delete category: " + e.getMessage());
			}
		} else {
			show("Warning", "Please select a category to delete.");
		}
	}
	
}