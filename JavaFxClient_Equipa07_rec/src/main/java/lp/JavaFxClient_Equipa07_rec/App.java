package lp.JavaFxClient_Equipa07_rec;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{
	
	private static ConfigurableApplicationContext context;

	
	@Override
    public void init() {
        context = new SpringApplicationBuilder(SpringBootApp.class)
        		.run();
    }
	
	@Override
	 public void start(Stage stage) throws Exception {
	 FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
	 loader.setControllerFactory(context::getBean); 
	 
	 Scene scene = new Scene(loader.load());
	 stage.setTitle("Login");
	 stage.setScene(scene);
	 stage.show();
	 }
	 public static void main(String[] args) {
	 launch();
	 }
	}