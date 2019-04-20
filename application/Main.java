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
	
	//Marvin Tan
	private void setUpMainMenuPage() {
      BorderPane root = new BorderPane();
      
      //define elements appearance
      Button goToOtherPageButton = new Button();
      goToOtherPageButton.setText("Set Up");
      
      //define functions when different buttons on this page is triggered
      // Lambda Expression
      //goToOtherPageButton.setOnMouseClicked(event -> );
      
      //set up page
      root.setTop(goToOtherPageButton);
      
      Scene sc = new Scene(root, 800, 800);
      sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      primaryStage.setScene(sc);
      
    }
	
	//Hui Beom
	private void setUpLoadQuestionPage() {
		
	}
	
	//Hui Beom
	private void setUpFileImportPage() {
      BorderPane root = new BorderPane();
    }
	
	//Hui Beom
    private void setUpAddQuestionPage() {
        
    }
    
    //Lucy
	private void setUpQuestionFilterPage() {
			

	}
	
	//Lucy
	private void setUpQuestionPage() {
		
	}
	
	//Marvin
	private void setUpScorePage() {

	}
	
	//Marvin
	private void setUpPopUpPage() {
			
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
