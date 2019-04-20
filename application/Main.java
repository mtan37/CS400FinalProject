package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;


public class Main extends Application implements EventHandler<ActionEvent>{
	private Stage primaryStage;
	private Button setUpButton;
	
	private Scene getEnterQuestionPage() {
		
		return null;
	}
	
	private Scene getQuestionFilterPage() {
			
		return null;
	}
	
	private Scene getMainMenuPage() {
		BorderPane root = new BorderPane();
		
		this.setUpButton = new Button();
		setUpButton.setText("Set Up");
		setUpButton.setOnAction(this);
		
		root.setTop(setUpButton);

		Scene mainPage = new Scene(root, 400, 400);
		
		return mainPage;
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
			Scene mainMenuPage = getMainMenuPage();
			
			primaryStage.setScene(mainMenuPage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		if(event.getSource()==setUpButton) {
			System.out.println("Button Pressed");
			
			//primaryStage.setScene();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
