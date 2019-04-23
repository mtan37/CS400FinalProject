package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class Main extends Application implements EventHandler<ActionEvent>{
	private Stage primaryStage;
	private QuizGenerator quizGenerator;
	
	//Marvin Tan
	private void setUpMainMenuPage() {
	  BorderPane root = new BorderPane();
      VBox centerVBox = new VBox();
      HBox topHBox = new HBox();
      HBox bottomHBox = new HBox();
	  
	  //define elements appearance
	  //Top
	  Label title = new Label("Quiz Generator");
	  title.setAlignment(Pos.CENTER);
      title.getStyleClass().add("title");
      topHBox.getChildren().add(title);
      topHBox.setAlignment(Pos.CENTER);
      topHBox.setPrefHeight(50);
      topHBox.getStyleClass().add("topHBox");
      
      //center
	  Button setUpBt = new Button();
	  setUpBt.setText("Set Up");
	  setUpBt.getStyleClass().add("NormalButton");
	  
	  Button createNewBt = new Button();
	  createNewBt.setText("Create New Quiz");
	  createNewBt.getStyleClass().add("NormalButton");
	  
	  centerVBox.getChildren().addAll(setUpBt, createNewBt);
      centerVBox.setAlignment(Pos.CENTER);
      centerVBox.setSpacing(30);
	  
	  //bottom
      Label fill = new Label(" ");
      fill.getStyleClass().add("normalText");
	  bottomHBox.getChildren().addAll(fill);
      bottomHBox.getStyleClass().add("bottomHBox");
	  
	  //define functions when different buttons on this page is triggered
	  // Lambda Expression
	  setUpBt.setOnMouseClicked(event -> setUpLoadQuestionPage());
	  createNewBt.setOnMouseClicked(event -> setUpQuestionFilterPage());
	  
	  //set up border pane by elements
	  root.setTop(topHBox);
	  root.setCenter(centerVBox);
	  root.setBottom(bottomHBox);
	  
	  Scene sc = new Scene(root, 500, 700);
	  sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	  primaryStage.setScene(sc);
      
    }
	
	//Hui Beom
	private void setUpLoadQuestionPage() {
		BorderPane root = new BorderPane();
		VBox centerVBox = new VBox();
		HBox topHBox = new HBox();
		HBox bottomHBox = new HBox();
		
		// =========
		// Top
		// =========
		Label title = new Label("Quiz Generator");
		title.getStyleClass().add("title");
	
		// =========
		// Center
		// =========
		Button loadButton = new Button();
		loadButton.setText("Load Questions From File");
		loadButton.getStyleClass().add("NormalButton");
		
		loadButton.setOnAction(event -> quizGenerator.loadFile());
		
		Button createButton = new Button();
		createButton.setText("Create New Questions");
		createButton.getStyleClass().add("NormalButton");
		
		createButton.setOnAction(event -> setUpAddQuestionPage());
		
		// =========
		// Bottom
		// =========
		Button backButton = new Button();
		backButton.setText("Back");
		backButton.getStyleClass().add("backButton");

		backButton.setOnAction(event -> setUpMainMenuPage());

		// Setting up the layout
		topHBox.getChildren().add(title);
		topHBox.setAlignment(Pos.CENTER);
		topHBox.setPrefHeight(50);
		topHBox.getStyleClass().add("topHBox");
		
		centerVBox.getChildren().addAll(loadButton, createButton);
		centerVBox.setAlignment(Pos.CENTER);
		centerVBox.setSpacing(30);
		
		bottomHBox.getChildren().addAll(backButton);
		bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);
		bottomHBox.getStyleClass().add("bottomHBox");
		
		root.setTop(topHBox);
		root.setCenter(centerVBox);
		root.setBottom(bottomHBox);
		  
		Scene sc = new Scene(root, 500, 700);
		sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(sc);
	}
	
	//Hui Beom
    private void setUpAddQuestionPage() {
    	BorderPane root = new BorderPane();
		VBox centerVBox = new VBox();
		
		VBox topicBox = new VBox();
		VBox descriptionBox = new VBox();
		VBox choiceBox = new VBox();
		HBox TFBox = new HBox();
		
		HBox topHBox = new HBox();
		HBox bottomHBox = new HBox();

		// =========
		// Top
		// =========
		Label title = new Label("Quiz Generator");
		title.getStyleClass().add("title");

		// =========
		// Center
		// =========
		Label topicLabel = new Label("Topic");
		TextField topicField = new TextField();
		topicBox.getChildren().addAll(topicLabel, topicField);

		Label descriptionLabel = new Label("Description");
		TextArea descriptionArea = new TextArea();
		descriptionBox.getChildren().addAll(descriptionLabel, descriptionArea);

		Label choiceLabel = new Label("Choices");
		TextField choiceField = new TextField();
		
		ToggleButton trueButton = new ToggleButton();
		trueButton.setText("T");
		ToggleButton falseButton = new ToggleButton();
		falseButton.setText("F");
		
		TFBox.getChildren().addAll(trueButton, falseButton);
		
		choiceBox.getChildren().addAll(choiceLabel, choiceField, TFBox);

		Button saveButton = new Button();
		saveButton.setText("Save");
		saveButton.getStyleClass().add("NormalButton");

		saveButton.setOnMouseClicked(event -> quizGenerator.loadFile());

		// =========
		// Bottom
		// =========
		Button backButton = new Button();
		backButton.setText("Back");
		backButton.getStyleClass().add("backButton");
		
		backButton.setOnAction(event -> setUpLoadQuestionPage());

		// Setting up the layout
		topHBox.getChildren().add(title);
		topHBox.setAlignment(Pos.CENTER);
		topHBox.setPrefHeight(50);
		topHBox.getStyleClass().add("topHBox");

		centerVBox.getChildren().addAll(topicBox, descriptionBox, choiceBox, saveButton);
		centerVBox.setAlignment(Pos.CENTER);
		centerVBox.setSpacing(30);

		bottomHBox.getChildren().addAll(backButton);
		bottomHBox.getStyleClass().add("bottomHBox");

		root.setTop(topHBox);
		root.setCenter(centerVBox);
		root.setBottom(bottomHBox);

		Scene sc = new Scene(root, 500, 700);
		sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(sc);
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
		quizGenerator = new QuizGenerator();
		
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
