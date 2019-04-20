package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application implements EventHandler<ActionEvent>{
	Stage primaryStage;
	
	private Scene getEnterQuestionPage() {
		
		return null;
	}
	
	private Scene getQuestionFilterPage() {
			
		return null;
	}
	
	private Scene getMainMenuPage() {
		
		return null;
	}
	
	private Scene getQuestionPage() {
		
		return null;
	}
	
	private Scene getResultPage() {
		
		return null;
	}
	
	private Scene getSetUpPage() {
			
		return null;
	}
	
	private Scene getLoadPage() {
		
		return null;
	}
	
	private Scene getSavePage() {
		
		return null;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			
			System.out.println("Hello Just testing");
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void switchTo(Scene scene) {
		primaryStage.setScene(scene);
	}
	
	@Override
	public void handle(ActionEvent event) {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
