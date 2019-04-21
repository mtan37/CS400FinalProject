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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class Main extends Application implements EventHandler<ActionEvent>{
	private Stage primaryStage;
	private QuizGenerator quizGenerator;
	
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
		loadButton.getStyleClass().add("loadButton");
		
		loadButton.setOnAction(event -> setUpFileImportPage());
		
		Button createButton = new Button();
		createButton.setText("Create New Questions");
		createButton.getStyleClass().add("createButton");
		
		createButton.setOnAction(event -> setUpAddQuestionPage());
		
		// =========
		// Bottom
		// =========
		Label bottomLabel = new Label("Set Up");
		bottomLabel.setMaxHeight(Double.MAX_VALUE);
		bottomLabel.getStyleClass().add("bottomLabel");
	
		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
		
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
		
		bottomHBox.getChildren().addAll(region1, bottomLabel, region2, backButton);
		bottomHBox.getStyleClass().add("bottomHBox");
		
		root.setTop(topHBox);
		root.setCenter(centerVBox);
		root.setBottom(bottomHBox);
		  
		Scene sc = new Scene(root, 400, 400);
		sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(sc);
	}
	
	//Hui Beom
	private void setUpFileImportPage() {
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
		loadButton.setText("Load");
		loadButton.getStyleClass().add("loadButton");
		
		loadButton.setOnMouseClicked(event -> quizGenerator.loadFile());

		// =========
		// Bottom
		// =========
		Label bottomLabel = new Label("Load Questions");
		bottomLabel.setMaxHeight(Double.MAX_VALUE);
		bottomLabel.getStyleClass().add("bottomLabel");

		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

		Button backButton = new Button();
		backButton.setText("Back");
		backButton.getStyleClass().add("backButton");
		
		backButton.setOnAction(event -> setUpLoadQuestionPage());

		// Setting up the layout
		topHBox.getChildren().add(title);
		topHBox.setAlignment(Pos.CENTER);
		topHBox.setPrefHeight(50);
		topHBox.getStyleClass().add("topHBox");

		centerVBox.getChildren().addAll(loadButton);
		centerVBox.setAlignment(Pos.CENTER);
		centerVBox.setSpacing(30);

		bottomHBox.getChildren().addAll(region1, bottomLabel, region2, backButton);
		bottomHBox.getStyleClass().add("bottomHBox");

		root.setTop(topHBox);
		root.setCenter(centerVBox);
		root.setBottom(bottomHBox);

		Scene sc = new Scene(root, 400, 400);
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
		
		Button trueButton = new Button();
		trueButton.setText("T");
		Button falseButton = new Button();
		falseButton.setText("F");
		
		TFBox.getChildren().addAll(trueButton, falseButton);
		
		choiceBox.getChildren().addAll(choiceLabel, choiceField, TFBox);

		Button saveButton = new Button();
		saveButton.setText("Save");
		saveButton.getStyleClass().add("loadButton");

		saveButton.setOnMouseClicked(event -> quizGenerator.loadFile());

		// =========
		// Bottom
		// =========
		Label bottomLabel = new Label("Create New Questions");
		bottomLabel.setMaxHeight(Double.MAX_VALUE);
		bottomLabel.getStyleClass().add("bottomLabel");

		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

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

		bottomHBox.getChildren().addAll(region1, bottomLabel, region2, backButton);
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
		try {
			this.primaryStage = primaryStage;
			setUpLoadQuestionPage();
			
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
