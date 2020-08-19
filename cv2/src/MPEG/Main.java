package MPEG;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		//ImagePlus originalImagePlus = new ImagePlus("lena_std.jpg");
		//new Process(originalImagePlus);
		
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("MojeGUI.fxml"));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
