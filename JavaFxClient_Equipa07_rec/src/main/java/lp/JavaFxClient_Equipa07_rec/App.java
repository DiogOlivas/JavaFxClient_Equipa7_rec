package lp.JavaFxClient_Equipa07_rec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 */
public class App extends Application{
	@Override
	 public void start(Stage stage) throws Exception {
	 FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
	 Scene scene = new Scene(loader.load());
	 
	 stage.setTitle("School API Client");
	 stage.setScene(scene);

	 stage.setWidth(500); 
	 stage.setHeight(400); 
	 
	 stage.show();
	 }
	 public static void main(String[] args) {
	 launch();
	 }
	}