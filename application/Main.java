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
	
	private void setUpEnterQuestionPage() {
		
	}
	
	private void setUpQuestionFilterPage() {
			

	}
	
	private void setUpMainMenuPage() {
		BorderPane root = new BorderPane();
		
		//define elements appearance
		Button goToOtherPageButton = new Button();
		goToOtherPageButton.setText("Set Up");
		
		//define functions when different buttons on this page is triggered
		// Lambda Expression
		goToOtherPageButton.setOnMouseClicked(event -> setUpEnterQuestionPage());
		
		//set up page
		root.setTop(goToOtherPageButton);

		primaryStage.setScene(new Scene(root, 400, 400));
		
	}
	
	private void setUpQuestionPage() {
		
	}
	
	private void setUpResultPage() {

	}
	
	private void setUpSetUpPage() {
			
	}
	
	private void setUpLoadPage() {
		
	}
	
	private void setUpSavePage() {
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			setUpMainMenuPage();
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void handle(ActionEvent event) {
		//code specified using lambda expression
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
