package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;


public class Main extends Application implements EventHandler<ActionEvent>{
	private Stage primaryStage;
	private QuizGenerator quizGenerator;
	private Scene mainMenu;
	private Scene loadQuestion;
	private Scene addQuestion;
	private Scene questionFilter;
	private Scene question;
	private Scene score;
	private Scene currDataBase;
	 //field for setUpQuestionPage
   private  int currIndex=1; //current index in the quiz generator data structure
    private Scene nextSc;
	
	//Marvin Tan
	private Scene setUpMainMenuPage() {
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
      Button startNewBt = new Button();
      startNewBt.setText("Start Quiz");
      startNewBt.getStyleClass().add("NormalButton");
      
	  Button setUpBt = new Button();
	  setUpBt.setText("Add question");
	  setUpBt.getStyleClass().add("NormalButton");
	  
	  Button currDataBaseBt = new Button();
	  currDataBaseBt.setText("Check Loaded Question Database");
	  currDataBaseBt.getStyleClass().add("NormalButton");
	  
	  centerVBox.getChildren().addAll(startNewBt, setUpBt,currDataBaseBt);
      centerVBox.setAlignment(Pos.CENTER);
      centerVBox.setSpacing(30);
	  
	  //bottom
      Label fill = new Label(" ");
      fill.getStyleClass().add("normalText");
	  bottomHBox.getChildren().addAll(fill);
      bottomHBox.getStyleClass().add("bottomHBox");
	  
	  //define functions when different buttons on this page is triggered
	  // Lambda Expression
	  setUpBt.setOnMouseClicked(event -> primaryStage.setScene(loadQuestion));
	  startNewBt.setOnMouseClicked(event -> startNewBt());
	  currDataBaseBt.setOnMouseClicked(event -> primaryStage.setScene(currDataBase));
	  
	  //set up border pane by elements
	  root.setTop(topHBox);
	  root.setCenter(centerVBox);
	  root.setBottom(bottomHBox);
	  
	  Scene sc = new Scene(root, 1200, 700);
	  sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	  //primaryStage.setScene(sc);
	  return sc;
      
    }
	/**
	 * Define the behavior of startNew button on the main menu page
	 */
	private void startNewBt() {
	  if(quizGenerator.numQuestion == 0) {
	    Alert alert = new Alert(AlertType.INFORMATION, "Please load at least one question before start a quiz");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(primaryStage);
        alert.showAndWait().filter(response -> response == ButtonType.OK);
        return;
	  }
	  primaryStage.setScene(questionFilter);
	}
	//Hui Beom
	private Scene setUpLoadQuestionPage() {

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
      
      createButton.setOnAction(event -> primaryStage.setScene(addQuestion));
      
      // =========
      // Bottom
      // =========
      Button backButton = new Button();
      backButton.setText("Back");
      backButton.getStyleClass().add("backButton");

      backButton.setOnAction(event -> primaryStage.setScene(mainMenu));

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
        
      Scene sc = new Scene(root, 1200, 700);
      sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      //primaryStage.setScene(sc);
      return sc;
	}
	
	//Hui Beom
    private Scene setUpAddQuestionPage() {
    	BorderPane root = new BorderPane();
		VBox centerVBox = new VBox();
		
		VBox topicBox = new VBox();
		VBox descriptionBox = new VBox();
		
		VBox choicesBox = new VBox();
		HBox choiceBox = new HBox();
		HBox addRemoveDoneBox = new HBox();
		
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
		
		// Title
		Label topicLabel = new Label("Topic");
		TextField topicField = new TextField();
		topicBox.getChildren().addAll(topicLabel, topicField);

		// Description
		Label descriptionLabel = new Label("Description");
		TextArea descriptionArea = new TextArea();
		descriptionBox.setPrefHeight(100);
		descriptionBox.getChildren().addAll(descriptionLabel, descriptionArea);

		
		// Choice
		Label choiceLabel = new Label("Choices");
		TextField choiceField = new TextField();
		
		
		Button addButton = new Button();
		addButton.setText("Add Choice");
		addButton.setOnAction(event -> {
			if(choicesBox.getChildren().size() < 6) {
				HBox newChoiceBox = new HBox();
				TextField newChoiceField = new TextField();
				
				newChoiceBox.getChildren().addAll(newChoiceField);
				HBox.setHgrow(newChoiceField, Priority.ALWAYS);
				choicesBox.getChildren().add(newChoiceBox);
			}
		});
		
		Button removeButton = new Button();
		removeButton.setText("Remove Choice");
		removeButton.setOnAction(event -> {
			if(choicesBox.getChildren().size() > 2) {
				choicesBox.getChildren().remove(choicesBox.getChildren().size()-1);
			}
		});
		
		Button doneButton = new Button();
		doneButton.setText("Done");
		doneButton.setOnAction(event -> {
			if(addRemoveDoneBox.getChildren().size() == 3) {
				addRemoveDoneBox.getChildren().remove(0);
				addRemoveDoneBox.getChildren().remove(0);
				
				ToggleGroup toggleGroup = new ToggleGroup();
				for(int i=1; i<choicesBox.getChildren().size(); i++) {
					ToggleButton button = new ToggleButton();
					button.setText("F");
					button.setToggleGroup(toggleGroup);
					button.setOnMouseClicked(events -> {
						for(int j=1; j<choicesBox.getChildren().size(); j++){
							((ToggleButton)((HBox)choicesBox.getChildren().get(j)).getChildren().get(1)).setText("F");
						}
						button.setText("T");
					});
					((HBox)choicesBox.getChildren().get(i)).getChildren().add(button);
				}
				
				doneButton.setText("Undo");
			} else if(addRemoveDoneBox.getChildren().size() == 1) {
				for(int i=1; i<choicesBox.getChildren().size(); i++) {
					((HBox)choicesBox.getChildren().get(i)).getChildren().remove(1);
				}
				
				addRemoveDoneBox.getChildren().remove(0);
				addRemoveDoneBox.getChildren().addAll(addButton, removeButton, doneButton);
				doneButton.setText("Done");
			}
		});
		
		addRemoveDoneBox.getChildren().addAll(addButton, removeButton, doneButton);
		
		HBox.setHgrow(choiceField, Priority.ALWAYS);
		choiceBox.getChildren().addAll(choiceField);
		choicesBox.getChildren().addAll(choiceLabel, choiceBox);

		Button saveButton = new Button();
		saveButton.setText("Save");
		saveButton.setOnAction(event -> {
			if(doneButton.getText() == "Undo") {
				quitPopUp(1);
			} else if(doneButton.getText() == "Done") {
				quitPopUp(1);
			}
		});
		saveButton.getStyleClass().add("NormalButton");

		//saveButton.setOnMouseClicked(event -> quizGenerator.loadFile()); TODO

		// =========
		// Bottom
		// =========
		Button backButton = new Button();
		backButton.setText("Back");
		backButton.getStyleClass().add("backButton");
		
		backButton.setOnAction(event -> {
			quitPopUp(3);
		});

		// Setting up the layout
		topHBox.getChildren().add(title);
		topHBox.setAlignment(Pos.CENTER);
		topHBox.setPrefHeight(50);
		topHBox.getStyleClass().add("topHBox");

		centerVBox.getChildren().addAll(topicBox, descriptionBox, choicesBox, addRemoveDoneBox, saveButton);
		centerVBox.setPadding(new Insets(30, 20, 30, 20));
		centerVBox.setSpacing(30);

		bottomHBox.getChildren().addAll(backButton);
		bottomHBox.getStyleClass().add("bottomHBox");

		root.setTop(topHBox);
		root.setCenter(centerVBox);
		root.setBottom(bottomHBox);

		Scene sc = new Scene(root, 1200, 700);
		sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		//primaryStage.setScene(sc);
		return sc;
    }
    
    //Lucy
	private Scene setUpQuestionFilterPage() {
	//count number of rows in the grid
	    BorderPane root= new BorderPane();// the root layout
	    HBox centerHBox = new HBox();
	    HBox topHBox = new HBox();
	    HBox bottomHBox = new HBox();
	    //top, title
	    Label title = new Label("Quiz Generator");
	    title.getStyleClass().add("title");
	      topHBox.getChildren().add(title);
	      topHBox.setAlignment(Pos.CENTER);
	      topHBox.setPrefHeight(50);
	      topHBox.getStyleClass().add("topHBox");
	    
	    //center
	    VBox centerVBox1= new VBox();
	    centerVBox1.setAlignment(Pos.TOP_LEFT);
	    VBox centerVBox2= new VBox();
        centerVBox1.setAlignment(Pos.TOP_RIGHT);
	    
	    
	    //center left 
        //__________\
	    Label topic = new Label("Choose Topic(s):");
	    topic.setPrefHeight(50);
	    
	    //hard code for topics combo list
	    //load from QuizGenerator topic TODO
	    ObservableList<String> cmLs= FXCollections.observableArrayList("BST", "HashTable", "AVL-Tree", "Graph", "Linux", "Set");
	    FXCollections.sort(cmLs);
	    ListView<String> topicList = new ListView<String>();
	    topicList.setItems(cmLs);
	    topicList.setCellFactory(CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
	        @Override
	        public ObservableValue<Boolean> call(String item) {
	            BooleanProperty observable = new SimpleBooleanProperty();
	            observable.addListener((obs, wasSelected, isNowSelected) -> {
	                System.out.println("Check box for "+item+" changed from "+wasSelected+" to "+isNowSelected);
	                if(!wasSelected && isNowSelected)
	                  quizGenerator.currChosenTopics.add(item);
	                else if(!isNowSelected && wasSelected)
	                  quizGenerator.currChosenTopics.remove(item);//TODO make this a separate method
	                }
	            );
	            return observable ;
	        }
	    }));
	    centerVBox1.getChildren().addAll(topic,topicList);
	    centerVBox1.setAlignment(Pos.TOP_LEFT);
	    
	    //center 
	    Label num= new Label("Enter number of questions in quiz: ");
	    num.setPrefHeight(50);
	    
	    TextField numTxt= new TextField();
        // force the field to be numeric only
        numTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numTxt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        
        centerVBox2.getChildren().addAll(num,numTxt);
        centerVBox2.setAlignment(Pos.TOP_RIGHT);
        
        centerHBox.getChildren().addAll(centerVBox1,centerVBox2);
        centerHBox.setAlignment(Pos.CENTER);
        centerHBox.setSpacing(10.0);
	    

	        
	    //bottom, back button
        Button add= new Button("Finisehd selection");
	    Button backBt = new Button("Back to Main Page");
	    backBt.getStyleClass().add("backButton");
	    add.getStyleClass().add("NormalButton");
	    bottomHBox.getChildren().addAll(backBt,add);
	    bottomHBox.getStyleClass().add("bottomHBox");
	    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);
	      
	    root.setTop(topHBox);
	    root.setCenter(centerHBox);
	    root.setBottom(bottomHBox);
	    
	    //events
	    add.setOnMouseClicked(event -> {
	      addBtQuestionFilter(numTxt.getText());
	    });
	    backBt.setOnMouseClicked(event -> {
	      primaryStage.setScene(mainMenu);
	    });
	    
	    //set scene
	    Scene sc= new Scene(root, 1200,700);
	    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return sc;

	}

  private void addBtQuestionFilter(String numTxt) {
    int numQuestion;
    try {
    numQuestion = Integer.parseInt(numTxt);
    }
    catch (NumberFormatException e) {
    numQuestion = -1;
    }
      if(quizGenerator.currChosenTopics.size() == 0 && !(numQuestion > 0)) {
        Alert alert = new Alert(AlertType.INFORMATION, "Please choose a topic and enter the number of questions you want before proceed");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(primaryStage);
        alert.showAndWait().filter(response -> response == ButtonType.OK);
      }
      else if(quizGenerator.currChosenTopics.size() == 0 && numQuestion > 0) {
        Alert alert = new Alert(AlertType.INFORMATION, "Please choose at least one topic");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(primaryStage);
        alert.showAndWait().filter(response -> response == ButtonType.OK);
      }
      else if(quizGenerator.currChosenTopics.size() > 0 && !(numQuestion > 0)) {
        Alert alert = new Alert(AlertType.INFORMATION, "Please enter a number greater than 0 for the number of requested questions");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(primaryStage);
        alert.showAndWait().filter(response -> response == ButtonType.OK);
      }
      else if(quizGenerator.currChosenTopics.size() > 0 && numQuestion > 0) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox();
        HBox dialogHbox = new HBox();
        
        Label prompt = new Label("Proceed with your current configuration?");
        prompt.getStyleClass().add("smallText");
        
        Button save = new Button();
        save.setText("Yes, proceed");
        save.getStyleClass().add("popUpButton");
        Button keepEdit = new Button();
        keepEdit.setText("No, keep editing");
        keepEdit.getStyleClass().add("popUpButton");
        
        dialogHbox.getChildren().addAll(save, keepEdit);
        dialogHbox.setAlignment(Pos.CENTER);
        dialogHbox.setSpacing(10.0);
        dialogVbox.getChildren().addAll(prompt, dialogHbox);
        dialogVbox.setAlignment(Pos.CENTER);
        
        save.setOnMouseClicked(event -> {
          primaryStage.setScene(question);
          dialog.close();
          });
        keepEdit.setOnMouseClicked(event -> {
          dialog.close();
          });
        Scene dialogScene = new Scene(dialogVbox, 400, 100);
        dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        dialog.setScene(dialogScene);
        dialog.show();
      }
      
    }

    //Lucy
	private Scene setUpQuestionPage() {
	     BorderPane root = new BorderPane();
	      root.getStyleClass().add("root");
	     nextSc = new Scene(root, 1200, 700);
	     
	      //top
	     
	      Label l1= new Label(); //hard code can get from data structure's index
	      l1.setText("Question "+ currIndex);
	      l1.getStyleClass().add("normalText");
	      Label indicator= new Label();
	      indicator.setVisible(false);
	      
	      
	      
	      VBox topbox=new VBox(l1, indicator);
	      topbox.getStyleClass().add("topHBox");
	      root.setTop(topbox);
	      
	    // center
	    VBox centerBox = new VBox();
	    centerBox.setAlignment(Pos.CENTER);
	    Label l2 = new Label("Description");
	    l2.getStyleClass().add("normalText");
	    TextArea ta = new TextArea(
	        "If a good hash function is found and a reasonable table size "
	        + "is used for a hash table, then the operations of put, remove, "
	        + "and get should achieve an average time complexity of _____ "
	        + "where $N$ is the number of items and $TS$ is the size of the table.");
	    ta.getStyleClass().add("smallText");
	   ta.setEditable(false);
	    ta.setMaxSize(500,300);
	    ta.setWrapText(true);
	    // get from quiz generator data structure with index  currIndex
	    
	    
	   
	    RadioButton c1= new RadioButton("$O(1)$");//isCorrect=true
	    RadioButton c2=new RadioButton("$O(log_{TS} N)$");
	    RadioButton c3=new RadioButton("$O(N^{TS})$");
	    RadioButton c4=new RadioButton("$O(N)$");
	    ObservableList<RadioButton> choices=FXCollections.observableArrayList(c1,c2,c3,c4);
	    ListView choiceLs=new ListView();
	    choiceLs.setItems(choices);
	    choiceLs.setMaxSize(500, 300);
	    
	    ToggleGroup tgG= new ToggleGroup();
	    for(RadioButton c: choices) {
	      c.setToggleGroup(tgG);
	      c.setWrapText(true);
	    }
	    
	    centerBox.getChildren().addAll(l2,ta,choiceLs);
	    root.setCenter(centerBox);
	    


	      //left
	   
	      Image img= new Image("wallPaper-icon.png");
	      
	      ImageView quizImgV=new ImageView(img);
	      VBox leftRg= new VBox();
	      leftRg.setAlignment(Pos.CENTER);
	      
	      leftRg.getChildren().add(quizImgV);
	      leftRg.setMargin(quizImgV, new Insets(50,50,50,50));
	     
	      root.setLeft(leftRg);
	      
	      
	      
	      
	      
	      
	      //bottom
	      Button backBt=new Button("Back");
	      backBt.getStyleClass().add("backButton");
	      Button nextBt= new Button("Next");
	      nextBt.getStyleClass().add("NormalButton");
	      Button submitBt= new Button("Submit");
	      submitBt.getStyleClass().add("NormalButton");
	      nextBt.setVisible(false); //visible after click submit
	      HBox bottomBox=new HBox(submitBt,backBt,nextBt);
	      bottomBox.getStyleClass().add("bottomHBox");
	      root.setBottom(bottomBox);
	     
	      //events
	 
	    
	    submitBt.setOnAction(e->{
	      //set indicator button and next button
	      if(tgG.getSelectedToggle().equals(c1)) {
	        indicator.setText("Correct");
	        indicator.getStyleClass().add("correctLabel");
	      }else {
	        indicator.setText("Wrong");
	        indicator.getStyleClass().add("wrongLabel");
	      }
	      indicator.setVisible(true);
	      nextBt.setVisible(true);
	      choiceLs.setDisable(true);
	      submitBt.setDisable(true);
	      
	      
	    });
	   
	    //set bsck button
	   backBt.setOnAction(e->{
	     setUpMainMenuPage();
	   }
	   );
	   
	  
	   nextBt.setOnAction(e->{
	     currIndex++;
	    
	     setUpQuestionPage();
	     
	     
	   });
	    
	    
	    
	      
	    nextSc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	      return nextSc;
	    }
	
	
	//Marvin
	private Scene setUpScorePage() {
	  BorderPane root = new BorderPane();
      VBox centerVBox = new VBox();
      //VBox buttonVBox = new VBox();
      HBox topHBox = new HBox();
      //HBox dataHBox = new HBox();
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
      int answered = quizGenerator.userRecord.getNumAns();
      int correct = quizGenerator.userRecord.getNumCor();
      int correctPercentage = quizGenerator.userRecord.getPercent();
      Label numAnswered = new Label("#Answered question: " + answered);//stub data TODO
      Label numCorrect = new Label("#Correct question: " + correct);//stub data TODO
      Label numCorrectPercentage = new Label("%Correct: " + correctPercentage + "%");
      
      Button startNewQuizBt = new Button();
      startNewQuizBt.setText("Start a New Quiz With the Same Setting");
      startNewQuizBt.getStyleClass().add("NormalButton");
      Button changeSettingBt = new Button();
      changeSettingBt.setText("Start a New Quiz With a Different Setting");
      changeSettingBt.getStyleClass().add("NormalButton");
      Button endBt = new Button();
      endBt.setText("End the program");
      endBt.getStyleClass().add("NormalButton");
      
      
      centerVBox.getChildren().addAll(numCorrect, numAnswered, numCorrectPercentage,startNewQuizBt, changeSettingBt, endBt);
      centerVBox.setAlignment(Pos.CENTER);
      centerVBox.setSpacing(30);
      
      //bottom
      Button backButton = new Button();
      backButton.setText("Back to Main Page");
      backButton.getStyleClass().add("backButton");
      bottomHBox.getChildren().addAll(backButton);
      bottomHBox.getStyleClass().add("bottomHBox");
      bottomHBox.setAlignment(Pos.BASELINE_RIGHT);
      
      //define functions when different buttons on this page is triggered
      // Lambda Expression
      startNewQuizBt.setOnMouseClicked(event -> {
        primaryStage.setScene(question);
        quizGenerator.userRecord.setNumRqst(0);
        quizGenerator.userRecord.setNumAns(0);
        quizGenerator.userRecord.setNumCor(0);
        /*reset 1.numAnswered
         *      2.numCorrect
         *      3.numCorrectPercentage
         */
        });
      changeSettingBt.setOnMouseClicked(event -> {
        primaryStage.setScene(questionFilter);
        quizGenerator.userRecord.setNumAns(0);
        quizGenerator.userRecord.setNumCor(0);
        /*reset 1.numAnswered
         *      2.numCorrect
         *      3.numCorrectPercentage
         */
        });
      endBt.setOnMouseClicked(event -> {
        try {
          Platform.exit();
        } catch (Exception e) {
          e.printStackTrace();
        }
      });
      backButton.setOnMouseClicked(event -> primaryStage.setScene(mainMenu));
      //set up border pane by elements
      root.setTop(topHBox);
      root.setCenter(centerVBox);
      root.setBottom(bottomHBox);
      
      Scene sc = new Scene(root, 1200, 700);
      sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      //primaryStage.setScene(sc);
      return sc;
	}
	
	private Scene setUpCurrDataBasePage() {
	    // TODO Auto-generated method stub
	    return null;
	  }
	//Marvin
		/*
		 * Flag:
		 * 1 - Save button in add question page, text filled in
		 * 2 - Save button in add question page, text not filled in
		 * 3 - Back button in add question page, not finished
		 */
		private void quitPopUp(int flag) {
		  final Stage dialog = new Stage();
	      dialog.initModality(Modality.APPLICATION_MODAL);
	      dialog.initOwner(primaryStage);
	      VBox dialogVbox = new VBox();
	      HBox dialogHbox = new HBox();
	      
	      Label prompt = new Label();
	      prompt.getStyleClass().add("smallText");
	      
	      Button exit = new Button();
	      exit.getStyleClass().add("popUpButton");
	      
	      Button notExit = new Button();
	      notExit.getStyleClass().add("popUpButton");
	      
	      // Save button in add question page, text filled in
	      if(flag == 1) {
	    	  // Someone help me with the prompt text...
	    	  prompt.setText("Do you want to save?");
	    	  exit.setText("Yes");
	    	  notExit.setText("No");
	    	  
	    	  exit.setOnMouseClicked(event -> {
	    		  primaryStage.setScene(loadQuestion);
	    		  dialog.close();
	    	  });
			  notExit.setOnMouseClicked(event -> {
				  primaryStage.setScene(addQuestion);
				  dialog.close();
			  });
	    	  
			  dialogHbox.getChildren().addAll(exit, notExit);
		      dialogHbox.setAlignment(Pos.CENTER);
		      dialogHbox.setSpacing(10.0);
		      dialogVbox.getChildren().addAll(prompt, dialogHbox);
		      dialogVbox.setAlignment(Pos.CENTER);
	      }
	      
	      // Save button in add question page, text not filled in
	      else if(flag == 2) {
	    	  prompt.setText("You need to fill in the \ninformation before saving");
			  
		      dialogVbox.getChildren().add(prompt);
		      dialogVbox.setAlignment(Pos.CENTER);
	      }
	      
	      // Back button in add question page, not finished
	      else if(flag == 3) {
	    	  prompt.setText("Are you sure you want to exit without finishing editing?");
	    	  exit.setText("Yes,exit");
	    	  notExit.setText("Keep editing");
	    	  
	    	  exit.setOnMouseClicked(event -> {
	    		  primaryStage.setScene(loadQuestion);
	    		  dialog.close();
	    	  });
			  notExit.setOnMouseClicked(event -> {
				  primaryStage.setScene(addQuestion);
				  dialog.close();
			  });
			  
			  dialogHbox.getChildren().addAll(exit, notExit);
		      dialogHbox.setAlignment(Pos.CENTER);
		      dialogHbox.setSpacing(10.0);
		      dialogVbox.getChildren().addAll(prompt, dialogHbox);
		      dialogVbox.setAlignment(Pos.CENTER);
	      }
	      
	      Scene dialogScene = new Scene(dialogVbox, 400, 100);
	      dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	      dialog.setScene(dialogScene);
	      dialog.show();
		}
	
	@Override
	public void start(Stage primaryStage) {
		quizGenerator = new QuizGenerator();
		
		try {
			this.primaryStage = primaryStage;
			mainMenu = setUpMainMenuPage();
		    loadQuestion = setUpLoadQuestionPage();
		    addQuestion = setUpAddQuestionPage();
		    questionFilter = setUpQuestionFilterPage();
		    question = setUpQuestionPage();
		    score = setUpScorePage();
		    currDataBase = setUpCurrDataBasePage();
		    
			primaryStage.setScene(mainMenu);
		    //primaryStage.setScene(score);
			
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
