package kasse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		String fxml = "kassegui.fxml";
		Parent root = FXMLLoader.load(Main.class.getResource(fxml));
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(true);
		primaryStage.show();
	}

}
