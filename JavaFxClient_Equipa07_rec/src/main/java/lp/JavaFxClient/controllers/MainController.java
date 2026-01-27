package lp.JavaFxClient.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import lp.JavaFxClient.model.TransactionDTO;
import lp.JavaFxClient.services.ApiService;
import lp.JavaFxClient_Equipa07_rec.UserSession;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
 
 @FXML private TableView<TransactionDTO> tbl_transactions;
 @FXML private TableColumn<TransactionDTO, Double> tbl_value;
 @FXML private TableColumn<TransactionDTO, String> tbl_desc;
 @FXML private TableColumn<TransactionDTO, String> tbl_pay;
 @FXML private TableColumn<TransactionDTO, String> tbl_date;

 @FXML private TableView<CategoryDTO> tbl_category;
 
 @FXML private TableColumn<CategoryDTO, String> tbl_name;

    @FXML private Button add_tran; 
    @FXML private Button add_cat;

	@FXML private Label lbl_tranEdit;
    @FXML private Label lbl_tranDelete;
    @FXML private Label lbl_catEdit;
    @FXML private Label lbl_catDelete;
    @FXML private Label lbl_account;

 private final ObjectMapper mapper = new ObjectMapper();

 

 @FXML
 public void initialize(){
	 try {
		 mapper.registerModule(new JavaTimeModule());
		 mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		 mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		 lbl_account.setText(UserSession.getInstance().getCurrentUser() + "👤");

		 tbl_value.setCellValueFactory(new PropertyValueFactory<>("value"));
		 tbl_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
		 tbl_pay.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
		 tbl_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		 tbl_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		 
		 tbl_category.getSelectionModel().selectedItemProperty().addListener(
			        (obs, oldSelection, newSelection) -> {
			            if (newSelection != null) {
			                filterTransactionsByCategory(newSelection.getId());
			            } else {
			                loadTransactions();
			            }
			        }
				 );
		 
		 loadTransactions();
		 loadCategories();
	 }catch(Exception e) {
		 show("Error.", "An unexpected error has occured.");
	 }
 }
 
 private void filterTransactionsByCategory(Long categoryId) {
	    try {
	        String json = api.get("/transactions");
	        TransactionDTO[] transactions = mapper.readValue(json, TransactionDTO[].class);

	        List<TransactionDTO> filtered = Arrays.stream(transactions)
	            .filter(tx -> tx.getCategoryId() != null && tx.getCategoryId().equals(categoryId))
	            .toList();

	        tbl_transactions.getItems().setAll(filtered);

	    } catch (Exception e) {
	        e.printStackTrace();
	        show("Error", "Failed to filter transactions:\n" + e.getMessage());
	    }
 } 
 
 private void loadTransactions(){
	try{
		String json = api.get("/transactions");
		if(json.startsWith("ERROR")){
			show("Error", json);
			return;
		}
		TransactionDTO[] transactions = mapper.readValue(json, TransactionDTO[].class); // Desserializa o JSON em um array de TransactionDTO
		tbl_transactions.getItems().setAll(transactions); // Atualiza a tabela com as transações
	} catch (Exception e){
	    e.printStackTrace();
	    show("Error", "Failed to load data:\n" + e.getMessage());
	}
 }
 
private void loadCategories(){
	try{
		String json = api.get("/categories");
		if(json.startsWith("ERROR")){
			show("Error", json);
			return;
		}
		CategoryDTO[] category = mapper.readValue(json, CategoryDTO[].class); // Desserializa o JSON em um array de CategoryDTO
		tbl_category.getItems().setAll(category); // Atualiza a tabela com as categorias
	} catch (Exception e){
	    e.printStackTrace();
	    show("Error", "Failed to load data:\n" + e.getMessage());
	}
		///show("Error", "Failed to load categories: " + e.getMessage());
		///SE O UTILIZADOR NÃO TIVER NENHUMA CATEGORIA, ISTO VAI APARECER, E NÃO PODE SER ASSIM
	
}

 @FXML 
 public void onAddTransaction(){
	openTransactionForm(null);
 }
 
 private void openTransactionForm(TransactionDTO transaction){
	try{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/newTransaction.fxml"));
		URL fxmlPath = getClass().getResource("/newTransaction.fxml");
		System.out.println(fxmlPath);
		
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
    public void onEditTransaction(MouseEvent event) {
        TransactionDTO selectedTransaction = tbl_transactions.getSelectionModel().getSelectedItem();
		if (selectedTransaction != null) {
			openTransactionForm(selectedTransaction);
		} else {
			show("Warning", "Please select a transaction to edit.");
		}
	}
	@FXML
	public void onDeleteTransaction(MouseEvent event) {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newCategory.fxml"));
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
	public void onAddCategory() {
		openCategoryForm(null);
	}
	/**@FXML
	public void onAddCategory(){
		try{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("NewCategory.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage();
			stage.setTitle("New Category");
			stage.setScene(new Scene(root));
			stage.showAndWait();
		} catch (Exception e){
			show("Error", "Failed to open category form: " + e.getMessage());
		}
	}**/
	
	@FXML
	public void onEditCategory(MouseEvent event) {
        CategoryDTO selectedCategory = tbl_category.getSelectionModel().getSelectedItem();
		if (selectedCategory != null) {
			openCategoryForm(selectedCategory);
		} else {
			show("Warning", "Please select a category to edit.");
		}
	}
	@FXML
	public void onDeleteCategory(MouseEvent event) {
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

    @FXML
    public void openAccountSettings(MouseEvent event) {
    	try {   		 
            FXMLLoader loader = new
    	    FXMLLoader(getClass().getResource("/profile.fxml"));
    		Parent root = loader.load();
    		
    		Stage stage = new Stage();
    		stage.setTitle("Profile");
    		stage.setScene(new Scene(root));
    		stage.show();

    		Stage loginWindow = (Stage) lbl_account.getScene().getWindow();
    		loginWindow.close(); 
    	} catch (Exception e) {
    		 e.printStackTrace();
    		 show("Error", "An unexpected error has occured, please try again.");
    	}	
    }
}