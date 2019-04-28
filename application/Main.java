//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: QuizGenerator
// Files:
// json-simple-1.1.1.jar
// application.css
// wallpaper-icon.png
// warn.png
// Course: CS 400
//
// Author: Marvin Tan, Nate Sackett, Shao Bin Daniel Shi Hong, Hui Beom Kim, Zhengyi Chen
// Email: marvin.tan@wisc.edu, nsackett@wisc.edu, shong78@wisc.edu, hkim788@wisc.edu, zchen597@wisc.edu
//
// Due date: May 2nd at 10:00 pm
// People who offered help: N/A
// Online source used:
// https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx
// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
// https://stackoverflow.com/questions/28843858/javafx-8-listview-with-checkboxes
// https://stackoverflow.com/questions/20446026/get-value-from-date-picker
// Known bugs: No known bugs
///////////////////////////////////////////////////////////////////////////////
package application;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
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
import javafx.scene.layout.VBox;

public class Main extends Application implements EventHandler<ActionEvent> {
  private Stage primaryStage;
  private QuizGenerator quizGenerator;
  private Scene mainMenu;
  private Scene loadQuestion;
  private Scene addQuestion;
  private Scene questionFilter;
  private Scene question;
  private Scene score;
  private Scene currDataBase;
  private int currIndex;
  
  boolean correctAnsSelected = false;// if the user has selected a correct answer for an added
                                     // question

  /**
   * Build a new main menu page
   * @return the Scene object of a new main menu page
   */
  private Scene setUpMainMenuPage() {
    BorderPane root = new BorderPane();
    VBox centerVBox = new VBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();

    // define elements appearance
    // Top
    Label title = new Label("Quiz Generator");
    title.setAlignment(Pos.CENTER);
    title.getStyleClass().add("title");
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    // center
    Button startNewBt = new Button();
    startNewBt.setText("Generate a New Quiz");
    startNewBt.getStyleClass().add("NormalButton");

    Button setUpBt = new Button();
    setUpBt.setText("Add question");
    setUpBt.getStyleClass().add("NormalButton");

    Button currDataBaseBt = new Button();
    currDataBaseBt.setText("Check Loaded Question Database");
    currDataBaseBt.getStyleClass().add("NormalButton");
    
    Button exitBt = new Button();
    exitBt.setText("Exit the Program");
    exitBt.getStyleClass().add("NormalButton");

    centerVBox.getChildren().addAll(startNewBt, setUpBt, currDataBaseBt,exitBt);
    centerVBox.setAlignment(Pos.CENTER);
    centerVBox.setSpacing(30);

    // bottom
    Label fill = new Label(" ");
    fill.getStyleClass().add("normalText");
    bottomHBox.getChildren().addAll(fill);
    bottomHBox.getStyleClass().add("bottomHBox");

    // define functions when different buttons on this page is triggered
    // Lambda Expression
    setUpBt.setOnMouseClicked(event -> primaryStage.setScene(loadQuestion));
    startNewBt.setOnMouseClicked(event -> startNewBt());
    currDataBaseBt.setOnMouseClicked(event -> primaryStage.setScene(currDataBase));
    exitBt.setOnMouseClicked(event -> exitBt());

    // set up border pane by elements
    root.setTop(topHBox);
    root.setCenter(centerVBox);
    root.setBottom(bottomHBox);

    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    // primaryStage.setScene(sc);
    return sc;

  }

  private void exitBt() {
    BorderPane popRt = new BorderPane();
    Scene popSc = new Scene(popRt, 750, 300);
    final Stage dialog = new Stage();

    if(quizGenerator.questionBank.size() == 0 || quizGenerator.userRecord.isCurrQuizSaved()) {
      Platform.exit();
    }
      
    Label prompt = new Label("Do you want to save the quiz before leaving?");
    prompt.getStyleClass().add("normalText");
    prompt.isWrapText();
    Image warn = new Image(new File("warn.png").toURI().toString());
    ImageView warnV = new ImageView(warn);
    HBox topBox = new HBox(warnV, prompt);
    topBox.setAlignment(Pos.CENTER);
    topBox.setPadding(new Insets(50, 20, 20, 20));
    topBox.getStyleClass().add("topHBox");
    popRt.setTop(topBox);

    Button saveBt = new Button("Save");
    Button leaveBt = new Button("Leave without saving");
    Button cancelBt = new Button("Go back to the Program");
    HBox centerBox = new HBox(leaveBt, saveBt, cancelBt);
    centerBox.setSpacing(30);
    centerBox.setAlignment(Pos.CENTER);
    centerBox.setPadding(new Insets(50, 50, 50, 50));
    popRt.setCenter(centerBox);

    // event
    saveBt.setOnAction(e -> {
      String saveAddress = generateSavingAddress();
      quizGenerator.fileHandler.saveFile(saveAddress);
      Alert alert = new Alert(AlertType.INFORMATION, "You quiz has been saved under " + saveAddress);
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      quizGenerator.userRecord.setCurrQuizSaved(true);
      Platform.exit();
    });

    leaveBt.setOnAction(e -> {
      Platform.exit();
    });

    cancelBt.setOnAction(e -> {
      dialog.close();
    });

    popSc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(primaryStage);
    dialog.setScene(popSc);
    dialog.show();}

  /**
   * Generate a saving address under application/savedQuiz for the current unsaved quiz
   * @return a saving address generated base on the local machine time
   */
  private String generateSavingAddress() {
    DatePicker datePicker = new DatePicker(LocalDate.now());
    LocalDate localDate = datePicker.getValue();
    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
    String saveAddress = "application/savedQuiz" + instant + ".json";
    return saveAddress;
  }
  
  /**
   * Define the behavior of startNew button on the main menu page
   */
  private void startNewBt() {
    if (quizGenerator.numQuestion == 0) {
      Alert alert =
          new Alert(AlertType.INFORMATION, "Please load at least one question before start a quiz");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      return;
    }
    questionFilter = setUpQuestionFilterPage();
    primaryStage.setScene(questionFilter);
  }

  /**
   * Build a load question page
   * @return the Scene object of a new load question page
   */
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

    loadButton.setOnAction(event -> {quizGenerator.loadFile();
    quizGenerator.userRecord.setCurrQuizSaved(false);
    });

    Button createButton = new Button();
    createButton.setText("Create New Questions");
    createButton.getStyleClass().add("NormalButton");

    createButton.setOnAction(event -> primaryStage.setScene(addQuestion));

    // =========
    // Bottom
    // =========
    Button backButton = new Button();
    backButton.setText("Back to Main Menu");
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

    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    // primaryStage.setScene(sc);
    return sc;
  }

  /**
   * Build a new add question page
   * @return the scene object of a new add question page
   */
  private Scene setUpAddQuestionPage() {
    BorderPane root = new BorderPane();
    VBox centerVBox = new VBox();

    VBox topicBox = new VBox();
    VBox descriptionBox = new VBox();

    VBox choicesBox = new VBox();
    HBox buttonBox = new HBox();

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

    // save button
    Button saveButton = new Button();
    saveButton.setText("Save question");
    saveButton.setVisible(false);

    // Description
    Label descriptionLabel = new Label("Description");
    TextArea descriptionArea = new TextArea();
    descriptionBox.setPrefHeight(100);
    descriptionBox.getChildren().addAll(descriptionLabel, descriptionArea);

    // Choices fields
    Label choiceLabel = new Label("Choices");
    Label choicePrompt = new Label("Please edit your choice content/number");
    choicePrompt.getStyleClass().add("smallText");
    TextField choiceField1 = new TextField();
    choiceField1.setPromptText("Please enter at least two choices");
    TextField choiceField2 = new TextField();
    choiceField2.setPromptText("Please enter at least two choices");
    TextField choiceField3 = new TextField();
    choiceField3.setPromptText("Please enter at least two choices");
    TextField choiceField4 = new TextField();
    choiceField4.setPromptText("Please enter at least two choices");
    TextField choiceField5 = new TextField();
    choiceField5.setPromptText("Please enter at least two choices");
    TextField[] choiceFields =
        new TextField[] {choiceField1, choiceField2, choiceField3, choiceField4, choiceField5};
    ArrayList<String> choicesDescripList = new ArrayList<String>();

    // The two button displayed(not at the same time)
    Button finishAddChoiceBt = new Button("Finish adding choices");// display when the user is
                                                                   // adding/removing choices
    finishAddChoiceBt.getStyleClass().add("NormalButton");
    Button goBackToChoiceBt = new Button("Go back to choice addition/remove");// display when the
                                                                              // user is choosing
    goBackToChoiceBt.getStyleClass().add("backButton");

    buttonBox.getChildren().addAll(finishAddChoiceBt);// initially display the finishAddChoiceBt
    correctAnsSelected = false;// The user starts with not selecting a correct answer

    // representing the each choice column including the text field and true/false button
    HBox choiceBoxList1 = new HBox();
    HBox choiceBoxList2 = new HBox();
    HBox choiceBoxList3 = new HBox();
    HBox choiceBoxList4 = new HBox();
    HBox choiceBoxList5 = new HBox();
    choiceBoxList1.getChildren().add(choiceField1);
    choiceBoxList2.getChildren().add(choiceField2);
    choiceBoxList3.getChildren().add(choiceField3);
    choiceBoxList4.getChildren().add(choiceField4);
    choiceBoxList5.getChildren().add(choiceField5);
    // a list that include all the possible textField
    HBox[] choiceBoxList =
        new HBox[] {choiceBoxList1, choiceBoxList2, choiceBoxList3, choiceBoxList4, choiceBoxList5};

    VBox choicesBox1 = new VBox();// including the label for choices and prompt
    choicesBox1.getChildren().addAll(choiceLabel, choicePrompt);

    VBox choicesBox2 = new VBox();// including the text fields of choices
    choicesBox2.getChildren().addAll(choiceBoxList1, choiceBoxList2, choiceBoxList3, choiceBoxList4,
        choiceBoxList5);
    choicesBox.getChildren().addAll(choicesBox1, choicesBox2);

    finishAddChoiceBt.setOnAction(event -> finishAddChoiceBt(choiceFields, choicesDescripList,
        buttonBox, goBackToChoiceBt, choicePrompt, saveButton, choicesBox2, choiceBoxList));

    goBackToChoiceBt.setOnAction(event -> // if when the goBackToChoiceBt is clicked
    goBackToChoiceBt(choicesBox2, choiceBoxList, buttonBox, finishAddChoiceBt, saveButton,
        choicePrompt));

    saveButton.setOnAction(event -> {
      saveFunction(choicesBox, topicField, descriptionArea, choiceLabel, choicePrompt);
    });
    saveButton.getStyleClass().add("NormalButton");

    // =========
    // Bottom
    // =========
    Button backButton = new Button();
    backButton.setText("Back to Last Page");
    backButton.getStyleClass().add("backButton");

    backButton.setOnAction(event -> {
      popUpQuitAddQuestion();
    });

    // Setting up the layout
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    centerVBox.getChildren().addAll(topicBox, descriptionBox, choicesBox, buttonBox, saveButton);
    centerVBox.setPadding(new Insets(30, 20, 30, 20));
    centerVBox.setSpacing(30);

    bottomHBox.getChildren().addAll(backButton);
    bottomHBox.getStyleClass().add("bottomHBox");
    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);

    root.setTop(topHBox);
    root.setCenter(centerVBox);
    root.setBottom(bottomHBox);

    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    // primaryStage.setScene(sc);
    return sc;
  }

  /**
   * Define the go back choice button behavior in the add question page
   * @param choicesBox2
   * @param choiceBoxList
   * @param buttonBox
   * @param finishAddChoiceBt
   * @param saveButton
   * @param choicePrompt
   */
  private void goBackToChoiceBt(VBox choicesBox2, HBox[] choiceBoxList, HBox buttonBox,
      Button finishAddChoiceBt, Button saveButton, Label choicePrompt) {
    for (int i = 0; i < choicesBox2.getChildren().size(); i++) {
      ((HBox) choicesBox2.getChildren().get(i)).getChildren().remove(1);
    }
    choicesBox2.getChildren().clear();
    choicesBox2.getChildren().addAll(choiceBoxList[0], choiceBoxList[1], choiceBoxList[2],
        choiceBoxList[3], choiceBoxList[4]);
    correctAnsSelected = true;
    buttonBox.getChildren().remove(0);
    buttonBox.getChildren().add(finishAddChoiceBt);
    saveButton.setVisible(false);
    choicePrompt.setText("Please edit your choice content/number");
  }

  /**
   * Define the finish add choice button behavior in the add question page
   * @param choiceFields
   * @param choicesDescripList
   * @param buttonBox
   * @param goBackToChoiceBt
   * @param choicePrompt
   * @param saveButton
   * @param choicesBox2
   * @param choiceBoxList
   */
  private void finishAddChoiceBt(TextField[] choiceFields, ArrayList<String> choicesDescripList,
      HBox buttonBox, Button goBackToChoiceBt, Label choicePrompt, Button saveButton,
      VBox choicesBox2, HBox[] choiceBoxList) {
    //reset choice Description list
    choicesDescripList = new ArrayList<String>();
    // enter user's given choice descriptions
    for (int i = 0; i < 5; i++) {
      if (choiceFields[i].getText().trim().compareTo("") != 0) {
        System.out.println(i);
        choicesDescripList.add(choiceFields[i].getText());
      }
    }
    if (choicesDescripList.size() > 1) {
      buttonBox.getChildren().remove(0);
      buttonBox.getChildren().add(goBackToChoiceBt);
      choicePrompt.setText("Please select which choice to be the correct answer");
      saveButton.setVisible(true);
      choicesBox2.getChildren().clear();// clear choices box
      // only add boxes with entry
      for (int i = 0; i < choiceFields.length; i++) {
        if (choiceFields[i].getText().trim().compareTo("") != 0) {// how to detect no entry TODO
          choicesBox2.getChildren().add(choiceBoxList[i]);
        }
      }
      ToggleGroup toggleGroup = new ToggleGroup();
      for (int i = 0; i < choicesBox2.getChildren().size(); i++) {
        ToggleButton button = new ToggleButton();
        button.setText("Select this to be the right answer");
        button.setToggleGroup(toggleGroup);
        button.getStyleClass().add("redButton");
        button.setOnMouseClicked(events -> {
          for (int j = 0; j < choicesBox2.getChildren().size(); j++) {
            ((ToggleButton) ((HBox) choicesBox2.getChildren().get(j)).getChildren().get(1))
                .setText("Select this to be the right answer");
            ((ToggleButton) ((HBox) choicesBox2.getChildren().get(j)).getChildren().get(1))
                .getStyleClass().remove("redButton");
            ((ToggleButton) ((HBox) choicesBox2.getChildren().get(j)).getChildren().get(1))
                .getStyleClass().remove("greenButton");
            ((ToggleButton) ((HBox) choicesBox2.getChildren().get(j)).getChildren().get(1))
                .getStyleClass().add("redButton");
          }
          correctAnsSelected = true;
          button.setText("The right answer");
          button.getStyleClass().remove("redButton");
          button.getStyleClass().add("greenButton");
        });
        ((HBox) choicesBox2.getChildren().get(i)).getChildren().add(button);
      }
      // finishAddChoiceBt.setText("Go back to choice addition/remove");
    } else {// give Warning
      Alert alert = new Alert(AlertType.INFORMATION, "Please enter at least two choices");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      return;
    }
  }
  
  /**
   * Define the save button behavior in the add question page
   * @param choicesBox
   * @param topicField
   * @param descriptionArea
   * @param choiceLabel
   * @param choicePrompt
   */
  private void saveFunction(VBox choicesBox, TextField topicField, TextArea descriptionArea,
      Label choiceLabel, Label choicePrompt) {
    if (topicField.getText().isEmpty()) {
      Alert alert =
          new Alert(AlertType.INFORMATION, "Please enter your question topic before proceeding");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    } else if (descriptionArea.getText().isEmpty()) {
      Alert alert = new Alert(AlertType.INFORMATION,
          "Please enter your question description before proceeding");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    } else if (!correctAnsSelected) {
      Alert alert = new Alert(AlertType.INFORMATION, "Please select a true choice");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    } else {
      final Stage dialog = new Stage();
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(primaryStage);
      VBox dialogVbox = new VBox();
      HBox dialogHbox = new HBox();

      Label prompt = new Label();
      prompt.setText("Do you want to save?");
      prompt.getStyleClass().add("smallText");

      Button save = new Button();
      save.setText("Yes");
      save.getStyleClass().add("popUpButton");

      Button notSave = new Button();
      notSave.setText("No");
      notSave.getStyleClass().add("popUpButton");

      // Add question to question bank and reset
      save.setOnMouseClicked(events -> {
        // create a new question
        // refresh question page
        this.addQuestion = setUpAddQuestionPage();
        primaryStage.setScene(addQuestion);
        dialog.close();
      });

      // Go back without doing anything
      notSave.setOnMouseClicked(events -> {
        // primaryStage.setScene(addQuestion);
        dialog.close();
      });

      dialogHbox.getChildren().addAll(save, notSave);
      dialogHbox.setAlignment(Pos.CENTER);
      dialogHbox.setSpacing(10.0);
      dialogVbox.getChildren().addAll(prompt, dialogHbox);
      dialogVbox.setSpacing(10.0);
      dialogVbox.setAlignment(Pos.CENTER);

      Scene dialogScene = new Scene(dialogVbox, 400, 100);
      dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
      dialog.setScene(dialogScene);
      dialog.show();
      // quitPopUp(1);
    }
  }
  
  /**
   * Build a new question filter page
   * @return the Scene object of a new question filter page
   */
  private Scene setUpQuestionFilterPage() {
    BorderPane root = new BorderPane();// the root layout
    HBox centerHBox = new HBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();
    // top, title
    Label title = new Label("Quiz Generator");
    title.getStyleClass().add("title");
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    // center
    VBox centerVBox1 = new VBox();
    centerVBox1.setAlignment(Pos.TOP_LEFT);
    VBox centerVBox2 = new VBox();
    centerVBox1.setAlignment(Pos.TOP_RIGHT);

    // center left
    // __________\
    Label topic = new Label("Choose Topic(s):");
    topic.setPrefHeight(50);

    ObservableList<String> cmLs = FXCollections.observableArrayList();
    // load from QuizGenerator topic 
      cmLs.addAll(quizGenerator.topic);
    FXCollections.sort(cmLs);
    
    //make a listView object from the observable list
    ListView<String> topicList = new ListView<String>();
    topicList.setItems(cmLs);
    topicList.setCellFactory(
        CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
          @Override
          public ObservableValue<Boolean> call(String item) {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
              if (!wasSelected && isNowSelected)
                quizGenerator.currChosenTopics.add(item);
              else if (!isNowSelected && wasSelected)
                quizGenerator.currChosenTopics.remove(item);
            });
            return observable;
          }
        }));
    centerVBox1.getChildren().addAll(topic, topicList);
    centerVBox1.setAlignment(Pos.TOP_LEFT);

    // center right
    Label num = new Label("Enter number of questions in quiz: ");
    num.setPrefHeight(50);

    TextField numTxt = new TextField();
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

    centerVBox2.getChildren().addAll(num, numTxt);
    centerVBox2.setAlignment(Pos.TOP_RIGHT);

    centerHBox.getChildren().addAll(centerVBox1, centerVBox2);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setSpacing(10.0);

    // bottom, back button
    Button add = new Button("Finish selection, start quiz");
    Button backBt = new Button("Back to Main Menu");
    backBt.getStyleClass().add("backButton");
    add.getStyleClass().add("NormalButton");
    bottomHBox.getChildren().addAll(backBt, add);
    bottomHBox.getStyleClass().add("bottomHBox");
    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);

    root.setTop(topHBox);
    root.setCenter(centerHBox);
    root.setBottom(bottomHBox);

    // events
    add.setOnMouseClicked(event -> {
      addBtQuestionFilter(numTxt.getText());
    });
    backBt.setOnMouseClicked(event -> {
      popUpQuitQuestion();
    });

    // set scene
    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return sc;

  }

  /**
   * Define the add button behavior in the question filter page
   * @param numTxt
   */
  private void addBtQuestionFilter(String numTxt) {
    int numQuestion;
    try {
      numQuestion = Integer.parseInt(numTxt);
    } catch (NumberFormatException e) {
      numQuestion = -1;
    }
    if (quizGenerator.currChosenTopics.size() == 0 && !(numQuestion > 0)) {
      Alert alert = new Alert(AlertType.INFORMATION,
          "Please choose a topic and enter the number of questions you want before proceed");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    } else if (quizGenerator.currChosenTopics.size() == 0 && numQuestion > 0) {
      Alert alert = new Alert(AlertType.INFORMATION, "Please choose at least one topic");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    } else if (quizGenerator.currChosenTopics.size() > 0 && !(numQuestion > 0)) {
      Alert alert = new Alert(AlertType.INFORMATION,
          "Please enter a number greater than 0 for the number of requested questions");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    } else if (quizGenerator.currChosenTopics.size() > 0 && numQuestion > 0) {
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
      dialogVbox.setSpacing(10.0);

      save.setOnMouseClicked(event -> {
          quizGenerator.numQuestion = Integer.parseInt(numTxt);
          dialog.close();
          primaryStage.setScene(question);
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

  /**
   * Build a new question page
   * @return the Scene object of a new question page
   */
    private Scene setUpQuestionPage(Question[] quizQls) {
	    if (quizQls==null) return null;
	    
	    BorderPane root = new BorderPane();
	    root.getStyleClass().add("root");
	    question = new Scene(root, 1200, 800);

	    // top

	    Label l1 = new Label(); // hard code can get from data structure's index
	    l1.setText("Question " + (currIndex + 1));
	    
	    l1.getStyleClass().add("normalText");
	    Label indicator = new Label();
	    indicator.setVisible(false);

	    VBox topbox = new VBox(l1, indicator);
	    topbox.getStyleClass().add("topHBox");
	    root.setTop(topbox);

	    // center
	    VBox centerBox = new VBox();
	    centerBox.setAlignment(Pos.CENTER);
	    Label l2 = new Label("Description");
	    l2.getStyleClass().add("normalText");
	    TextArea ta = new TextArea(quizQls[currIndex].getDescription());
	    ta.getStyleClass().add("smallText");
	    ta.setEditable(false);
	    ta.setMaxSize(500, 300);
	    ta.setWrapText(true);
	    
	  
	    //create an observable list holding choice descriptions
	    ObservableList<RadioButton> choices = FXCollections.observableArrayList();
	    
	//iterate through current question's choice list
	    //put descriptions into RadioButtons and add the button to the observable list
	    for (int i=0; i<quizQls[currIndex].getChoices().size(); i++) {
	      choices.add(new RadioButton(
	          quizQls[currIndex].getChoices().get(i).getDescription()));
	    }
	  
	 
	    ListView choiceLs = new ListView ();
	    choiceLs.setItems(choices);
	    choiceLs.setMaxSize(500, 300);

	    ToggleGroup tgG = new ToggleGroup();
	    for (RadioButton c : choices) {
	      c.setToggleGroup(tgG);
	      c.setWrapText(true);
	    }

	    centerBox.getChildren().addAll(l2, ta, choiceLs);
	    root.setCenter(centerBox);

	    // left

	    Image img = new Image(new File("wallPaper-icon.png").toURI().toString());

	    ImageView quizImgV = new ImageView(img);
	    VBox leftRg = new VBox();
	    leftRg.setAlignment(Pos.CENTER);

	    leftRg.getChildren().add(quizImgV);
	    leftRg.setMargin(quizImgV, new Insets(50, 50, 50, 50));

	    root.setLeft(leftRg);

	    // bottom
	    Button backBt = new Button("Back to Main Menu");
	    backBt.getStyleClass().add("backButton");
	    Button nextBt = new Button("Next");
	    nextBt.getStyleClass().add("NormalButton");
	    Button submitBt = new Button("Submit");
	    submitBt.getStyleClass().add("NormalButton");
	    nextBt.setVisible(false); // visible after click submit
	    HBox bottomBox = new HBox(submitBt, backBt, nextBt);
	    bottomBox.setSpacing(20);
	    bottomBox.setPadding(new Insets(20, 20, 20, 20));
	    bottomBox.getStyleClass().add("bottomHBox");
	    root.setBottom(bottomBox);

	    // events

	    submitBt.setOnAction(e -> {
	      // set indicator button and next button
	      if (tgG.getSelectedToggle()==null)
	        return;
	      //get correct description
	      String correctDscr= quizQls[currIndex].getCorrectChose().getDescription();
	      RadioButton s= (RadioButton) tgG.getSelectedToggle(); //get selected button
	      
	      if (s.getText().equals(correctDscr)) {
	        indicator.setText("Correct");
	        indicator.getStyleClass().add("correctLabel");
	      } else {
	        indicator.setText("Wrong");
	        indicator.getStyleClass().add("wrongLabel");
	      }
	      indicator.setVisible(true);
	      nextBt.setVisible(true);
	      choiceLs.setDisable(true);
	      submitBt.setDisable(true);

	    });

	    // set bsck button
	    backBt.setOnAction(e -> {
	      // popUpQuitQuestion();
	      // primaryStage.setScene(setUpMainMenuPage());
	    });

	    nextBt.setOnAction(e -> {
	      currIndex++;
	      if(currIndex==quizGenerator.numQuestion) {
	        popUpQuitQuestion();
	      }

	      primaryStage.setScene(setUpQuestionPage( quizQls));

	    });

	    question.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    return question;
	  }

  /**
   * A pop up window to warn user when the user quit the quiz before finish
   */
  private void popUpQuitQuestion() {
    BorderPane popRt = new BorderPane();
    Scene popSc = new Scene(popRt, 750, 300);
    final Stage dialog = new Stage();

    Label prompt = new Label("Do you want to save the quiz before leaving?");
    prompt.getStyleClass().add("normalText");
    prompt.isWrapText();
    Image warn = new Image(new File("warn.png").toURI().toString());
    ImageView warnV = new ImageView(warn);
    HBox topBox = new HBox(warnV, prompt);
    topBox.setAlignment(Pos.CENTER);
    topBox.setPadding(new Insets(50, 20, 20, 20));
    topBox.getStyleClass().add("topHBox");
    popRt.setTop(topBox);

    Button saveBt = new Button("Save");
    Button leaveBt = new Button("Leave without saving");
    Button cancelBt = new Button("Cancel");
    HBox centerBox = new HBox(leaveBt, saveBt, cancelBt);
    centerBox.setSpacing(30);
    centerBox.setAlignment(Pos.CENTER);
    centerBox.setPadding(new Insets(50, 50, 50, 50));
    popRt.setCenter(centerBox);

    // event
    saveBt.setOnAction(e -> {
      quizGenerator.fileHandler.pickFile();
      primaryStage.setScene(setUpMainMenuPage());

    });

    leaveBt.setOnAction(e -> {
      primaryStage.setScene(setUpMainMenuPage());
    });

    cancelBt.setOnAction(e -> {
      dialog.close();
    });

    popSc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(primaryStage);
    dialog.setScene(popSc);
    dialog.show();

  }

  /**
   * Build a new score page
   * @return the Scene object of a new score page
   */
  private Scene setUpScorePage() {
    BorderPane root = new BorderPane();
    VBox centerVBox = new VBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();

    // define elements appearance
    // Top
    Label title = new Label("Quiz Generator");
    title.setAlignment(Pos.CENTER);
    title.getStyleClass().add("title");
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    // center
    int answered = quizGenerator.userRecord.getNumAns();
    int correct = quizGenerator.userRecord.getNumCor();
    int correctPercentage = quizGenerator.userRecord.getPercent();
    Label numAnswered = new Label("#Answered question: " + answered);
    Label numCorrect = new Label("#Correct question: " + correct);
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

    centerVBox.getChildren().addAll(numCorrect, numAnswered, numCorrectPercentage, startNewQuizBt,
        changeSettingBt, endBt);
    centerVBox.setAlignment(Pos.CENTER);
    centerVBox.setSpacing(30);

    // bottom
    Button backButton = new Button();
    backButton.setText("Back to Main Menu");
    backButton.getStyleClass().add("backButton");
    bottomHBox.getChildren().addAll(backButton);
    bottomHBox.getStyleClass().add("bottomHBox");
    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);

    // define functions when different buttons on this page is triggered
    // Lambda Expression
    startNewQuizBt.setOnMouseClicked(event -> {
      primaryStage.setScene(question);
      quizGenerator.userRecord.setNumRqst(0);
      quizGenerator.userRecord.setNumAns(0);
      quizGenerator.userRecord.setNumCor(0);
    });
    changeSettingBt.setOnMouseClicked(event -> {
      primaryStage.setScene(questionFilter);
      quizGenerator.userRecord.setNumAns(0);
      quizGenerator.userRecord.setNumCor(0);
    });
    endBt.setOnMouseClicked(event -> {
      try {
        exitBt();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    backButton.setOnMouseClicked(event -> primaryStage.setScene(mainMenu));
    // set up border pane by elements
    root.setTop(topHBox);
    root.setCenter(centerVBox);
    root.setBottom(bottomHBox);

    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return sc;
  }

  /**
   * Build a new current database page
   * @return the Scene object of a new current database page
   */
  private Scene setUpCurrDataBasePage() {
    BorderPane root = new BorderPane();// the root layout
    HBox centerHBox = new HBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();
    // top, title
    Label title = new Label("Quiz Generator");
    title.getStyleClass().add("title");
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    // center
    VBox centerVBox1 = new VBox();
    centerVBox1.setAlignment(Pos.TOP_LEFT);
    VBox centerVBox2 = new VBox();
    centerVBox1.setAlignment(Pos.TOP_RIGHT);

    // center left
    // __________\
    Label topic = new Label("Current list of topics:");
    topic.setPrefHeight(50);

    // hard code for topics combo list
    ObservableList<String> topicLs =
        FXCollections.observableArrayList(quizGenerator.topic);
    FXCollections.sort(topicLs);
    ListView<String> topicList = new ListView<String>();
    topicList.setItems(topicLs);

    centerVBox1.getChildren().addAll(topic, topicList);
    // centerVBox1.getChildren().addAll(topic,topicLs);
    centerVBox1.setAlignment(Pos.TOP_LEFT);

    // center right
    Label num = new Label("Number of questions in each topic:");
    num.setPrefHeight(50);
    
    ObservableList<Integer> numQLs =FXCollections.observableArrayList();
    for(int i = 0; i < topicLs.size();i++) {
      String topicName = topicLs.get(i);
      numQLs.add(quizGenerator.questionBank.get(topicName).size());
    }
    
    ListView<Integer> numQList = new ListView<Integer>();
    numQList.setItems(numQLs);


    centerVBox2.getChildren().addAll(num, numQList);
    centerVBox2.setAlignment(Pos.TOP_RIGHT);

    centerHBox.getChildren().addAll(centerVBox1, centerVBox2);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setSpacing(10.0);

    // bottom, back button
    Button backBt = new Button("Back to Main Menu");
    backBt.getStyleClass().add("backButton");
    bottomHBox.getChildren().addAll(backBt);
    bottomHBox.getStyleClass().add("bottomHBox");
    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);

    root.setTop(topHBox);
    root.setCenter(centerHBox);
    root.setBottom(bottomHBox);

    // events
    backBt.setOnMouseClicked(event -> {
      primaryStage.setScene(mainMenu);
    });

    // set scene
    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return sc;
  }

  /**
   * A pop up window to warn user when the user quit adding question
   */
  private void popUpQuitAddQuestion() {
    final Stage dialog = new Stage();
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(primaryStage);
    VBox dialogVbox = new VBox();
    HBox dialogHbox = new HBox();

    Label prompt = new Label("Are you sure you want to exit without finishing editing?");
    prompt.getStyleClass().add("smallText");

    Button exit = new Button();
    exit.setText("Yes,exit");
    exit.getStyleClass().add("popUpButton");
    Button notExit = new Button();
    notExit.setText("Keep editing");
    notExit.getStyleClass().add("popUpButton");

    dialogHbox.getChildren().addAll(exit, notExit);
    dialogHbox.setAlignment(Pos.CENTER);
    dialogHbox.setSpacing(10.0);
    dialogVbox.getChildren().addAll(prompt, dialogHbox);
    dialogVbox.setSpacing(10.0);
    dialogVbox.setAlignment(Pos.CENTER);

    exit.setOnMouseClicked(event -> {
      this.addQuestion = setUpAddQuestionPage();
      primaryStage.setScene(loadQuestion);
      dialog.close();
    });
    notExit.setOnMouseClicked(event -> {
      dialog.close();
    });

    Scene dialogScene = new Scene(dialogVbox, 400, 100);
    dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    dialog.setScene(dialogScene);
    dialog.show();
  }
  
  /**
   * Start the program and set things up
   */
  @Override
  public void start(Stage primaryStage) {
    quizGenerator = new QuizGenerator();
    try {
      this.primaryStage = primaryStage;
      mainMenu = setUpMainMenuPage();
      loadQuestion = setUpLoadQuestionPage();
      addQuestion = setUpAddQuestionPage();
      questionFilter = setUpQuestionFilterPage();
      score = setUpScorePage();
      currDataBase = setUpCurrDataBasePage();

      primaryStage.setScene(mainMenu);
      // primaryStage.setScene(score);
      // primaryStage.setScene(question);

      primaryStage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * The generic handle method for event handling
   */
  @Override
  public void handle(ActionEvent event) {
    // code specified using lambda expression
  }

  /**
   * The main method that launch start(primaryStage)
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
