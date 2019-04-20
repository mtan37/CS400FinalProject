package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class Main extends Application implements EventHandler<ActionEvent>{
	private Stage primaryStage;
	
	private Scene getEnterQuestionPage() {
		
		return null;
	}
	
	private Scene getQuestionFilterPage() {
			
		return null;
	}
	
	private Scene getMainMenuPage() {
		// Anonymous class
		/*setUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Button Clicked2");
			}
		});*/
		
		BorderPane root = new BorderPane();
		
		Label title = new Label("Quiz Generator");
		
		Button setUpButton = new Button();
		setUpButton.setText("Set Up");
		
		// Lambda Expression
		setUpButton.setOnAction(event -> getSetUpPage());
		
		root.setTop(title);
		root.setCenter(setUpButton);
		
		BorderPane.setAlignment(title, Pos.CENTER);

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
			
			mainMenuPage.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(mainMenuPage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		/*if(event.getSource()==setUpButton) {
			System.out.println("Button Pressed");
			
			//primaryStage.setScene();
		}*/
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
