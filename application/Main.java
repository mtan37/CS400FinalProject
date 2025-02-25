//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: QuizGenerator
// Files:
// json-simple-1.1.1.jar
// application.css
// warn.png
// noImage.png
// Course: CS 400
//
// Author: Marvin Tan, Nate Sackett, Shao Bin Daniel Shi Hong, Hui Beom Kim, Zhengyi Chen
// Email: marvin.tan@wisc.edu, nsackett@wisc.edu, shong78@wisc.edu, hkim788@wisc.edu,
//////////////////// zchen597@wisc.edu
//
// Due date: May 2nd at 10:00 pm
// People who offered help: N/A
// Online source used:
// https://stackoverflow.com/questions/22166610/how-to-create-a-popup-windows-in-javafx
// https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
// https://stackoverflow.com/questions/28843858/javafx-8-listview-with-checkboxes
// https://stackoverflow.com/questions/20446026/get-value-from-date-picker
// https://stackoverflow.com/questions/26619566/javafx-stage-close-handler
// https://www.java-tips.org/java-se-tips-100019/24-java-lang/480-the-enhanced-for-loop.html
// https://www.geeksforgeeks.org/parse-json-java/
// https://www.youtube.com/watch?v=hNz8Xf4tMI4
// https://www.geeksforgeeks.org/parse-json-java/
// noImage.png: https://en.wikipedia.org/wiki/2016–17_Liga_I#/media/File:No_image_available.svg
// warn.png: http://www.iconarchive.com
// Known bugs: No known bugs
///////////////////////////////////////////////////////////////////////////////

package application;

import java.io.File;
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

/**
 * This class is the main application interface where our quiz generator program launches
 * 
 * @author Marvin Tan, Nate Sackett, Shao Bin Daniel Shi Hong, Hui Beom Kim, Zhengyi Chen
 *
 */
public class Main extends Application implements EventHandler<ActionEvent> {

  private Stage primaryStage;
  private QuizGenerator quizGenerator; // back end quiz generator
  // Scenes for different GUI pages
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
   * 
   * @return the Scene object of a new main menu page
   */
  private Scene setUpMainMenuPage() {

    BorderPane root = new BorderPane();
    VBox centerVBox = new VBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();

    // =========
    // Top
    // =========
    // Quiz Generator Title
    Label title = new Label("Quiz Generator");
    title.setAlignment(Pos.CENTER);
    title.getStyleClass().add("title");
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    // =========
    // Center
    // =========
    // Start Quiz (Button)
    Button startNewBt = new Button();
    startNewBt.setText("Start a New Quiz");
    startNewBt.getStyleClass().add("NormalButton");

    // Set Up (Button)
    Button loadQBt = new Button();
    loadQBt.setText("Add Question(s)");
    loadQBt.getStyleClass().add("NormalButton");

    // View Current Questions (Button)
    Button currDataBaseBt = new Button();
    currDataBaseBt.setText("View/Save Current Questions");
    currDataBaseBt.getStyleClass().add("NormalButton");

    Button exitBt = new Button();
    exitBt.setText("Exit Quiz Generator");
    exitBt.getStyleClass().add("NormalButton");

    // Layout Set Up
    centerVBox.getChildren().addAll(startNewBt, loadQBt, currDataBaseBt, exitBt);
    centerVBox.setAlignment(Pos.CENTER);
    centerVBox.setSpacing(30);

    // =========
    // Bottom
    // =========
    // Filler Area for Blue Line at the Bottom
    Label fill = new Label(" ");
    fill.getStyleClass().add("normalText");
    bottomHBox.getChildren().addAll(fill);
    bottomHBox.getStyleClass().add("bottomHBox");

    // define functions when different buttons on this page is triggered
    // when load question button is clicked
    loadQBt.setOnMouseClicked(event -> primaryStage.setScene(loadQuestion));

    // when start a new quiz button is clicked
    startNewBt.setOnMouseClicked(event -> startNewBt());

    // when check current question bank button is clicked
    currDataBaseBt.setOnMouseClicked(event -> {
      currDataBaseBt();
    });
    exitBt.setOnMouseClicked(event -> exitBt());

    // set up border pane by elements
    root.setTop(topHBox);
    root.setCenter(centerVBox);
    root.setBottom(bottomHBox);

    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return sc;

  }

  /**
   * Define the behavior of open current question bank button in the main menu page
   */
  private void currDataBaseBt() {
    if (quizGenerator.topic.size() == 0) {
      // set up a pop up alert when no question in question bank
      Alert alert = new Alert(AlertType.INFORMATION, "There are no questions in Quiz Generator");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      return;
    }
    // change to current data base page
    primaryStage.setScene(currDataBase);
  }

  /**
   * A private method that will be called when exit button is clicked
   */
  private void exitBt() {

    BorderPane popRt = new BorderPane();
    Scene popSc = new Scene(popRt, 750, 300);
    final Stage dialog = new Stage();

    // exit directly if no quiz to save or quiz already saved
    if (quizGenerator.questionBank.size() == 0 || quizGenerator.userRecord.isCurrQuizSaved()) {
      Platform.exit();
    }


    // set up pop up window for unsaved quiz
    Label prompt = new Label("Do you want to save the quiz before leaving?");
    prompt.getStyleClass().add("normalText");
    prompt.isWrapText();
    Image warn = new Image("application/warn.png");
    ImageView warnV = new ImageView(warn);
    HBox topBox = new HBox(warnV, prompt);
    topBox.setAlignment(Pos.CENTER);
    topBox.setPadding(new Insets(50, 20, 20, 20));
    topBox.getStyleClass().add("topHBox");
    popRt.setTop(topBox);

    Button saveBt = new Button("Save");
    Button leaveBt = new Button("Exit without saving");
    Button cancelBt = new Button("Cancel");
    HBox centerBox = new HBox(leaveBt, saveBt, cancelBt);
    centerBox.setSpacing(30);
    centerBox.setAlignment(Pos.CENTER);
    centerBox.setPadding(new Insets(50, 50, 50, 50));
    popRt.setCenter(centerBox);

    // events

    // feedback whether saving quiz successfully or not
    saveBt.setOnAction(e -> {
      quizSave();
      dialog.close();
    });

    // leave without saving
    leaveBt.setOnAction(e -> {
      Platform.exit();
    });

    // continue remaining in the current page
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
   * Define the behavior of save button on the exit popup
   */
  private void quizSave() {
    boolean saved = quizGenerator.fileHandler.saveFile(quizGenerator.questionBank);
    if (saved) {
      Alert alert = new Alert(AlertType.INFORMATION, "You quiz has been saved");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      quizGenerator.userRecord.setCurrQuizSaved(true);
      Platform.exit();
    } else {
      Alert alert = new Alert(AlertType.INFORMATION, "Your quiz is not saved due to an error");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      quizGenerator.userRecord.setCurrQuizSaved(false);
    }
    
  }

  /**
   * This method defines the behavior of startNew button on the main menu page
   */
  private void startNewBt() {
    if (quizGenerator.topic.size() == 0) {
      Alert alert = new Alert(AlertType.INFORMATION, "Add questions before starting a new quiz");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      return;
    }
    questionFilter = setUpQuestionFilterPage();
    primaryStage.setScene(questionFilter);
  }


  /**
   * A pop up window to inform user that question(s) is(are) added to the bank
   * 
   * @param num: if num=1, prompt add 1 question, other number indicating adding a file
   */
  private void popUpSaveLoadSuccess(int num) {
    BorderPane popRt = new BorderPane();
    Scene popSc = new Scene(popRt, 400, 100);
    final Stage dialog = new Stage();

    String q1 = "Question added. \nYou can continue adding questions or return to the main menu.";
    String f = "File loaded. \nYou can continue adding questions or return to the main menu.";
    Label prompt = new Label();

    if (num == 1) {
      prompt.setText(q1);
    } else {
      prompt.setText(f);
    }
    prompt.getStyleClass().add("smallText");
    prompt.setWrapText(true);


    HBox topBox = new HBox(prompt);
    topBox.setAlignment(Pos.CENTER);
    topBox.setPadding(new Insets(5, 0, 0, 0));


    popRt.setTop(topBox);

    Button closeBt = new Button("Close");
    closeBt.getStyleClass().add("popUpButton");
    HBox centerBox = new HBox(closeBt);
    centerBox.setAlignment(Pos.CENTER_RIGHT);
    centerBox.setPadding(new Insets(1, 10, 0, 10));
    popRt.setCenter(centerBox);

    closeBt.setOnAction(e -> {
      dialog.close();
    });

    popSc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    dialog.initModality(Modality.APPLICATION_MODAL);
    dialog.initOwner(primaryStage);
    dialog.setScene(popSc);
    dialog.show();



  }

  /**
   * Build a load question page
   * 
   * @return the Scene object of a new load question page
   */
  private Scene setUpLoadQuestionPage() {
    // Layout Set Up
    BorderPane root = new BorderPane();
    VBox centerVBox = new VBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();

    // =========
    // Top
    // =========
    // Quiz Generator Title
    Label title = new Label("Quiz Generator");
    title.getStyleClass().add("title");

    // =========
    // Center
    // =========
    // Load Questions From File (Button)
    Button loadButton = new Button();
    loadButton.setText("Load Questions From File");
    loadButton.getStyleClass().add("NormalButton");

    loadButton.setOnAction(event -> {
      boolean loaded = quizGenerator.loadFile();
      quizGenerator.userRecord.setCurrQuizSaved(false);
      currDataBase = setUpCurrDataBasePage();

      if (loaded) {
        popUpSaveLoadSuccess(2);
      }
    });

    // Create Questions (Button)
    Button createButton = new Button();
    createButton.setText("Create a New Question");
    createButton.getStyleClass().add("NormalButton");

    createButton.setOnAction(event -> primaryStage.setScene(addQuestion));

    // =========
    // Bottom
    // =========
    // Go Back Button
    Button backButton = new Button();
    backButton.setText("Return");
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

    return sc;
  }

  /**
   * Build a new add question page
   * 
   * @return the scene object of a new add question page
   */
  private Scene setUpAddQuestionPage() {
    // Layout Set Up
    BorderPane root = new BorderPane();

    HBox topHBox = new HBox();
    VBox centerVBox = new VBox();
    HBox bottomHBox = new HBox();

    VBox topicBox = new VBox();
    VBox descriptionBox = new VBox();
    VBox imageBox = new VBox();

    VBox choicesBox = new VBox();
    HBox buttonBox = new HBox();



    // =========
    // Top
    // =========
    Label title = new Label("Quiz Generator");
    title.getStyleClass().add("title");

    // =========
    // Center
    // =========
    // Topic (Label, TextField)
    Label topicLabel = new Label("Topic");
    TextField topicField = new TextField();
    topicBox.getChildren().addAll(topicLabel, topicField);

    // Description (Label, TextArea)
    Label descriptionLabel = new Label("Question");
    TextArea descriptionArea = new TextArea();
    descriptionBox.setPrefHeight(100);
    descriptionBox.getChildren().addAll(descriptionLabel, descriptionArea);

    // Image (Label, TextField)
    Label imageLabel =
        new Label("Image File Name(Please make sure image is in project source folder)");
    TextField imageField = new TextField();
    imageField.setPromptText("Ex: goodhash2_AK.jpg");
    imageBox.getChildren().addAll(imageLabel, imageField);

    // Save (Button)
    Button saveButton = new Button();
    saveButton.setText("Save question");
    saveButton.setVisible(false);

    // Choices (Label, TextField)
    Label choiceLabel = new Label("Choices");
    Label choicePrompt = new Label("Edit number/content of choices:");
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

    // Array of Choice Fields (Initial list of choice fields)
    TextField[] choiceFields =
        new TextField[] {choiceField1, choiceField2, choiceField3, choiceField4, choiceField5};

    // ArrayList of Choice Fields (List after user clicks finish button)
    ArrayList<Choice> choicesList = new ArrayList<Choice>();

    // Finish/Go Back Choices (Button)
    // Displayed when the user is entering into choices fields
    Button finishAddChoiceBt = new Button("Finish adding choices"); // adding/removing choices
    finishAddChoiceBt.getStyleClass().add("NormalButton");

    // Displayed when the user is choosing correct choice button
    Button goBackToChoiceBt = new Button("Return to add/remove choices");
    goBackToChoiceBt.getStyleClass().add("backButton");

    buttonBox.getChildren().addAll(finishAddChoiceBt);// Initially display the finishAddChoiceBt
    correctAnsSelected = false; // The user starts with not selecting a correct answer

    // Choice TextField Takes Entire Width
    HBox.setHgrow(choiceField1, Priority.ALWAYS);
    HBox.setHgrow(choiceField2, Priority.ALWAYS);
    HBox.setHgrow(choiceField3, Priority.ALWAYS);
    HBox.setHgrow(choiceField4, Priority.ALWAYS);
    HBox.setHgrow(choiceField5, Priority.ALWAYS);

    // Representing each choice column including the text field and true/false
    // button
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

    // List that include all the possible textField
    HBox[] choiceBoxList =
        new HBox[] {choiceBoxList1, choiceBoxList2, choiceBoxList3, choiceBoxList4, choiceBoxList5};

    VBox choicesBox1 = new VBox();// including the label for choices and prompt
    choicesBox1.getChildren().addAll(choiceLabel, choicePrompt);

    VBox choicesBox2 = new VBox();// including the text fields of choices
    choicesBox2.getChildren().addAll(choiceBoxList1, choiceBoxList2, choiceBoxList3, choiceBoxList4,
        choiceBoxList5);
    choicesBox.getChildren().addAll(choicesBox1, choicesBox2);

    finishAddChoiceBt.setOnAction(event -> finishAddChoiceBt(choiceFields, choicesList, buttonBox,
        goBackToChoiceBt, choicePrompt, saveButton, choicesBox2, choiceBoxList));

    goBackToChoiceBt.setOnAction(event -> // if when the goBackToChoiceBt is clicked
    goBackToChoiceBt(choicesBox2, choiceBoxList, buttonBox, finishAddChoiceBt, saveButton,
        choicePrompt));

    saveButton.setOnAction(event -> saveFunction(topicField, imageField, descriptionArea,
        choiceLabel, choicePrompt, choicesList));
    saveButton.getStyleClass().add("NormalButton");

    // =========
    // Bottom
    // =========
    // Go Back (Button)
    Button backButton = new Button();
    backButton.setText("Return");
    backButton.getStyleClass().add("backButton");

    backButton.setOnAction(event -> {
      popUpQuitAddQuestion();
    });

    // Setting up the layout
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    centerVBox.getChildren().addAll(topicBox, descriptionBox, imageBox, choicesBox, buttonBox,
        saveButton);
    centerVBox.setPadding(new Insets(20, 20, 30, 10));
    centerVBox.setSpacing(15);

    bottomHBox.getChildren().addAll(backButton);
    bottomHBox.getStyleClass().add("bottomHBox");
    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);

    root.setTop(topHBox);
    root.setCenter(centerVBox);
    root.setBottom(bottomHBox);

    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return sc;
  }

  /**
   * Define the go back choice button behavior in the add question page
   * 
   * @param choicesBox2 is the box to include choice options
   * @param choiceBoxList is the list for all choices box
   * @param buttonBox is the box that includes all the buttons
   * @param finishAddChoiceBt is the button to click when user finish adding choice
   * @param saveButton is the user to click on when to save stuff
   * @param choicePrompt the the text label to prompt user to add/remove choices
   */
  private void goBackToChoiceBt(VBox choicesBox2, HBox[] choiceBoxList, HBox buttonBox,
      Button finishAddChoiceBt, Button saveButton, Label choicePrompt) {
    // Remove the toggle buttons
    for (int i = 0; i < choicesBox2.getChildren().size(); i++) {
      ((HBox) choicesBox2.getChildren().get(i)).getChildren().remove(1);
    }

    // Reset to five choice fields
    choicesBox2.getChildren().clear();
    choicesBox2.getChildren().addAll(choiceBoxList[0], choiceBoxList[1], choiceBoxList[2],
        choiceBoxList[3], choiceBoxList[4]);

    // Correct answer resets to false
    correctAnsSelected = false;

    // Finish Button is displayed instead of Go Back Button
    buttonBox.getChildren().remove(0);
    buttonBox.getChildren().add(finishAddChoiceBt);
    saveButton.setVisible(false);
    choicePrompt.setText("Please edit your choice content/number");
  }

  /**
   * Define the finish add choice button behavior in the add question page
   * 
   * @param choiceFields is the list of textfields of all choices
   * @param choicesList is the list for all choices
   * @param buttonBox is the box that includes all the buttons
   * @param goBackToChoiceBt is the button to click when user wants to go back to adding/remove
   *        choices
   * @param choicePrompt the text label to prompt user to add/remove choices
   * @param saveButton is the user to click on when to save stuff
   * @param choicesBox2 is the box to include choice options
   * @param choiceBoxList is the list for all choices
   */
  private void finishAddChoiceBt(TextField[] choiceFields, ArrayList<Choice> choicesList,
      HBox buttonBox, Button goBackToChoiceBt, Label choicePrompt, Button saveButton,
      VBox choicesBox2, HBox[] choiceBoxList) {
    // reset choice Description list
    choicesList.clear();
    // Store user's given choice descriptions into arrayList
    for (int i = 0; i < 5; i++) {
      if (choiceFields[i].getText().trim().compareTo("") != 0) {
        Choice currC = new Choice(false, choiceFields[i].getText());
        choicesList.add(currC);
      }
    }
    // If the user typed more than 1 choices
    if (choicesList.size() > 1) {
      // Display Go Back button instead of Finish Add Button
      buttonBox.getChildren().remove(0);
      buttonBox.getChildren().add(goBackToChoiceBt);

      // Change the text in the prompt label
      choicePrompt.setText("Please select the correct answer:");

      // Now user can click save button
      saveButton.setVisible(true);

      // Clear choices box
      choicesBox2.getChildren().clear();

      // Display only the boxes with entry
      for (int i = 0; i < choiceFields.length; i++) {
        if (choiceFields[i].getText().trim().compareTo("") != 0) {// how to detect no entry
          choicesBox2.getChildren().add(choiceBoxList[i]);
        }
      }

      // Add in the toggle buttons for selecting correct choice
      ToggleGroup toggleGroup = new ToggleGroup();
      for (int i = 0; i < choicesBox2.getChildren().size(); i++) {
        final int index = i;
        final ArrayList<Choice> choicesListInner = choicesList;
        // Make a toggle button
        ToggleButton button = new ToggleButton();
        button.setText("This is the correct answer");
        button.setToggleGroup(toggleGroup);
        button.getStyleClass().add("redButton");
        button.setOnMouseClicked(events -> trueBt(index, choicesBox2, choicesListInner, button));
        ((HBox) choicesBox2.getChildren().get(i)).getChildren().add(button);
      }
    } else {// When the user didn't enter more than two choice textFields, display warning
      Alert alert = new Alert(AlertType.INFORMATION, "Please enter at least two choices");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      return;
    }
  }

  private void trueBt(int i, VBox choicesBox2, ArrayList<Choice> choicesList, ToggleButton button) {
    {
      for (int j = 0; j < choicesBox2.getChildren().size(); j++) {
        ((ToggleButton) ((HBox) choicesBox2.getChildren().get(j)).getChildren().get(1))
            .setText("This is the correct answer");
        ((ToggleButton) ((HBox) choicesBox2.getChildren().get(j)).getChildren().get(1))
            .getStyleClass().remove("redButton");
        ((ToggleButton) ((HBox) choicesBox2.getChildren().get(j)).getChildren().get(1))
            .getStyleClass().remove("greenButton");
        ((ToggleButton) ((HBox) choicesBox2.getChildren().get(j)).getChildren().get(1))
            .getStyleClass().add("redButton");
        choicesList.get(j).setIsCorrect(false);
      }
      correctAnsSelected = true;
      choicesList.get(i).setIsCorrect(true);
      button.setText("Correct answer");
      button.getStyleClass().remove("redButton");
      button.getStyleClass().add("greenButton");
    }
  }

  /**
   * Define the save button behavior in the add question page
   * 
   * @param topicField is the textField box for user to enter a topic for a question
   * @param descriptionArea is the text field for user to enter description for a question
   * @param choiceLabel is the label "Choices"
   * @param choicePrompt is the text label to prompt user to add/remove choices
   */
  private void saveFunction(TextField topicField, TextField imageField, TextArea descriptionArea,
      Label choiceLabel, Label choicePrompt, ArrayList<Choice> choicesList) {

    // ===============
    // Alert Messages
    // ===============
    // If topic field is empty
    if (topicField.getText().isEmpty()) {
      Alert alert = new Alert(AlertType.INFORMATION, "Please enter a topic for the question");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    }
    // If the description area is empty
    else if (descriptionArea.getText().isEmpty()) {
      Alert alert = new Alert(AlertType.INFORMATION, "Please enter the question before proceeding");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    }
    // If correct answer is not chosen
    else if (!correctAnsSelected) {
      Alert alert = new Alert(AlertType.INFORMATION, "Please select a true choice");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    }
    // If the user entered all the required field
    else {
      // try if image URL is valid
      try {
        if (imageField.getText().compareTo("") != 0)
          new Image(imageField.getText());
      } catch (IllegalArgumentException e) {
        Alert alert = new Alert(AlertType.INFORMATION, "Image file path not valid");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(primaryStage);
        alert.showAndWait().filter(response -> response == ButtonType.OK);
        return;
      }
      // Make a pop up message asking if user want to save
      final Stage dialog = new Stage();
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(primaryStage);
      VBox dialogVbox = new VBox();
      HBox dialogHbox = new HBox();

      // Prompt Message
      Label prompt = new Label();
      prompt.setText("Do you want to save?");
      prompt.getStyleClass().add("smallText");

      // Save Button
      Button save = new Button();
      save.setText("Yes");
      save.getStyleClass().add("popUpButton");

      // Don't Save Button
      Button notSave = new Button();
      notSave.setText("No");
      notSave.getStyleClass().add("popUpButton");

      // Add question to question bank and reset
      save.setOnMouseClicked(events -> {
        saveBtDialog(topicField, descriptionArea, choicesList, imageField);
        dialog.close();
      });

      // Go back without doing anything
      notSave.setOnMouseClicked(events -> {
        dialog.close();
      });

      // Layout Set Up For the Pop Up
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
    }
  }

  /**
   * Define the behavior of the save button in the pop up dialog in add question page
   * 
   * @param topicField is the textField box for user to enter a topic for a question
   * @param descriptionArea is the text field for user to enter description for a question
   * @param choicesList is the list for all choices
   * @param imageField is the text field for user to enter image address
   */
  private void saveBtDialog(TextField topicField, TextArea descriptionArea,
      ArrayList<Choice> choicesList, TextField imageField) {
    // Creating new question and adding it to data structure
    String topic = topicField.getText();
    String description = descriptionArea.getText();
    Question q = new Question(topic, choicesList, description);
    boolean saved = quizGenerator.addQuestion(q);
    q.saveImage(imageField.getText());

    if (saved) {
      popUpSaveLoadSuccess(1);
    }

    // reset whether current quiz saved
    quizGenerator.userRecord.setCurrQuizSaved(false);

    System.out.print("haha");

    // Refresh question page and current database page
    this.currDataBase = setUpCurrDataBasePage();
    this.addQuestion = setUpAddQuestionPage();
    primaryStage.setScene(loadQuestion);
  }

  /**
   * Build a new question filter page
   * 
   * @return the Scene object of a new question filter page
   */
  private Scene setUpQuestionFilterPage() {
    // Layout Set Up
    BorderPane root = new BorderPane();
    HBox centerHBox = new HBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();

    // =========
    // Top
    // =========
    // Quiz Generator Title (Label)
    Label title = new Label("Quiz Generator");
    title.getStyleClass().add("title");
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    // =========
    // Center
    // =========
    // VBox for center left
    VBox centerVBox1 = new VBox();
    centerVBox1.setAlignment(Pos.TOP_LEFT);

    // VBox for center right
    VBox centerVBox2 = new VBox();
    centerVBox1.setAlignment(Pos.TOP_RIGHT);

    // ============
    // Center Left
    // ============
    // Prompt Message for choosing topics (Label)
    Label topic = new Label("Choose topic(s):");
    topic.setPrefHeight(50);

    ObservableList<String> cmLs = FXCollections.observableArrayList();
    // load from QuizGenerator topic
    cmLs.addAll(quizGenerator.topic);
    FXCollections.sort(cmLs);

    // make a listView object from the observable list
    ListView<String> topicList = new ListView<String>();
    topicList.setItems(cmLs);
    topicList.setCellFactory(
        CheckBoxListCell.forListView(new Callback<String, ObservableValue<Boolean>>() {
          @Override
          public ObservableValue<Boolean> call(String item) {
            BooleanProperty observable = new SimpleBooleanProperty();
            observable.addListener((obs, wasSelected, isNowSelected) -> {
              if (!wasSelected && isNowSelected)
                quizGenerator.userRecord.topicsChosen.add(item);
              else if (!isNowSelected && wasSelected)
                quizGenerator.userRecord.topicsChosen.remove(item);
            });
            return observable;
          }
        }));
    centerVBox1.getChildren().addAll(topic, topicList);
    centerVBox1.setAlignment(Pos.TOP_LEFT);

    // ============
    // Center Right
    // ============
    // Prompt Message for # of questions (Label)
    Label num = new Label("Choose number of questions to answer: ");
    num.setPrefHeight(50);

    // TextField for entering # of questions
    TextField numTxt = new TextField();
    // Force the field to be numeric only
    numTxt.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          numTxt.setText(newValue.replaceAll("[^\\d]", ""));
        }
      }
    });

    // Layout Set Up
    centerVBox2.getChildren().addAll(num, numTxt);
    centerVBox2.setAlignment(Pos.TOP_RIGHT);

    centerHBox.getChildren().addAll(centerVBox1, centerVBox2);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setSpacing(10.0);

    // =========
    // Bottom
    // =========
    // Start Quiz (Button)
    Button add = new Button("Finish selection and start quiz");
    add.getStyleClass().add("NormalButton");

    // Go Back (Button)
    Button backBt = new Button("Return");
    backBt.getStyleClass().add("backButton");

    // Layout Set Up
    bottomHBox.getChildren().addAll(backBt, add);
    bottomHBox.getStyleClass().add("bottomHBox");
    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);

    root.setTop(topHBox);
    root.setCenter(centerHBox);
    root.setBottom(bottomHBox);

    // Button Action Events
    add.setOnMouseClicked(event -> {
      addBtQuestionFilter(numTxt.getText());
    });
    backBt.setOnMouseClicked(event -> {
      primaryStage.setScene(mainMenu);
    });

    // set scene
    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return sc;

  }

  /**
   * Define the add button behavior in the question filter page
   * 
   * @param numTxt
   */
  private void addBtQuestionFilter(String numTxt) {
    // Number of Questions User want to test on
    int numQuestionReq;

    // Change the number string to integer
    try {
      numQuestionReq = Integer.parseInt(numTxt);
    } catch (NumberFormatException e) {
      numQuestionReq = -1;
    }

    // ===============
    // Alert Messages
    // ===============
    // If the user didn't select topic and # of questions is less than 0
    if (quizGenerator.userRecord.topicsChosen.size() == 0 && !(numQuestionReq > 0)) {
      Alert alert = new Alert(AlertType.INFORMATION,
          "Please choose a topic and enter the number of questions you want before proceed");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    }
    // If the user didn't select topic
    else if (quizGenerator.userRecord.topicsChosen.size() == 0 && numQuestionReq > 0) {
      Alert alert = new Alert(AlertType.INFORMATION, "Please choose at least one topic");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    }
    // If the # of questions is less than 0
    else if (quizGenerator.userRecord.topicsChosen.size() > 0 && !(numQuestionReq > 0)) {
      Alert alert = new Alert(AlertType.INFORMATION,
          "Please enter a number greater than 0 for the number of requested questions");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
    }
    // If the user entered correctly
    else if (quizGenerator.userRecord.topicsChosen.size() > 0 && numQuestionReq > 0) {
      // Make a pop up for asking user if he want to proceed
      final Stage dialog = new Stage();
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(primaryStage);
      VBox dialogVbox = new VBox();
      HBox dialogHbox = new HBox();

      // Prompt Message
      Label prompt = new Label("Proceed with your current configuration?");
      prompt.getStyleClass().add("smallText");

      // Proceed Button
      Button save = new Button();
      save.setText("Yes, proceed");
      save.getStyleClass().add("popUpButton");

      // Keep Edit Button
      Button keepEdit = new Button();
      keepEdit.setText("No, keep editing");
      keepEdit.getStyleClass().add("popUpButton");

      // Layout Set Up
      dialogHbox.getChildren().addAll(save, keepEdit);
      dialogHbox.setAlignment(Pos.CENTER);
      dialogHbox.setSpacing(10.0);
      dialogVbox.getChildren().addAll(prompt, dialogHbox);
      dialogVbox.setAlignment(Pos.CENTER);
      dialogVbox.setSpacing(10.0);

      // Save Button Action Events
      save.setOnMouseClicked(event -> {
        quizGenerator.userRecord.setNumRqst(Integer.parseInt(numTxt));
        currIndex = 0;
        Question[] quizQls = quizGenerator.generateQuestionList();
        question = setUpQuestionPage(quizQls);
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
   * 
   * @return the Scene object of a new question page
   */
  private Scene setUpQuestionPage(Question[] quizQls) {

    if (quizQls == null)
      return null;
    BorderPane root = new BorderPane();
    root.getStyleClass().add("root");
    Scene sc = new Scene(root, 1200, 800);

    Question currQ = quizQls[currIndex];
    // =========
    // Top
    // =========
    // Hard coded, can get from data structure's index
    Label l1 = new Label();
    l1.setText("Question " + (currIndex + 1) + " out of " + quizGenerator.userRecord.getNumRqst());

    l1.getStyleClass().add("normalText");
    Label indicator = new Label();
    indicator.setVisible(false);

    VBox topbox = new VBox(l1, indicator);
    topbox.getStyleClass().add("topHBox");
    root.setTop(topbox);

    // =========
    // Center
    // =========
    HBox centerBox = new HBox();
    centerBox.setAlignment(Pos.CENTER);
    VBox centerBoxRight = new VBox();
    VBox centerBoxLeft = new VBox();
    Label l2 = new Label("Question");
    l2.getStyleClass().add("normalText");
    TextArea ta = new TextArea(currQ.getDescription());
    ta.getStyleClass().add("smallText");
    ta.setEditable(false);
    ta.setMaxSize(600, 300);
    ta.setWrapText(true);

    // create an observable list holding choice descriptions
    ObservableList<RadioButton> choices = FXCollections.observableArrayList();

    // iterate through current question's choice list
    // put descriptions into RadioButtons and add the button to the observable list
    ArrayList<Choice> choiceList = currQ.getChoices();

    for (int i = 0; i < choiceList.size(); i++) {
      RadioButton rb = new RadioButton(choiceList.get(i).getDescription());
      rb.setPrefWidth(590);
      rb.setWrapText(true);
      choices.add(rb);
      // choices.add(new RadioButton(choiceList.get(i).getDescription()));
    }

    ListView<RadioButton> choiceLs = new ListView<RadioButton>();
    choiceLs.setItems(choices);
    choiceLs.setMaxSize(600, 250);

    ToggleGroup tgG = new ToggleGroup();
    for (RadioButton c : choices) {
      c.setToggleGroup(tgG);
      c.setWrapText(true);
    }
    centerBoxRight.getChildren().addAll(l2, ta, choiceLs);
    centerBoxRight.setAlignment(Pos.CENTER_RIGHT);

    // Add image center Hbox at the left
    Image img = currQ.loadImage();
    ImageView quizImgV = new ImageView(img);
    quizImgV.setPreserveRatio(true);
    quizImgV.setFitHeight(400.0);
    quizImgV.setFitWidth(400.0);
    VBox leftRg = new VBox();
    leftRg.setAlignment(Pos.CENTER_LEFT);
    leftRg.getChildren().add(quizImgV);
    centerBoxLeft.getChildren().add(leftRg);
    centerBoxLeft.setAlignment(Pos.CENTER_LEFT);

    centerBox.getChildren().addAll(centerBoxLeft, centerBoxRight);
    centerBox.setSpacing(20.0);
    root.setCenter(centerBox);


    // =========
    // Bottom
    // =========
    // Go Back (Button)
    Button backBt = new Button("Return");
    backBt.getStyleClass().add("backButton");

    // Next (Button)
    Button nextBt = new Button("Next");
    nextBt.getStyleClass().add("NormalButton");
    nextBt.setVisible(false);// next button is invisible by default. Not visible until submit the
                             // question
    if (currIndex == (quizGenerator.userRecord.getNumRqst() - 1)) {// if the last question, next Bt
                                                                   // shows different text
      nextBt.setText("Check Score");
      nextBt.getStyleClass().add("checkScoreBt");
    }

    // Submit (Button)
    Button submitBt = new Button("Submit Answer");
    submitBt.getStyleClass().add("NormalButton");

    // Layout Set Up
    HBox bottomBox = new HBox(submitBt, backBt, nextBt);
    bottomBox.setSpacing(20);
    bottomBox.setPadding(new Insets(20, 20, 20, 20));
    bottomBox.getStyleClass().add("bottomHBox");
    root.setBottom(bottomBox);

    // Submit Button Action Events
    submitBt.setOnAction(e -> {
      submitQuestionBt(tgG, quizQls, indicator, choices, nextBt, submitBt, choiceLs);
    });


    // set back button
    backBt.setOnAction(e -> {
      popUpQuitQuestion();
      resetDifferentSetting();
    });

    nextBt.setOnAction(e -> {
      // if there is a next question
      if (currIndex < (quizGenerator.userRecord.getNumRqst() - 1)) {
        // load the next question
        currIndex++;
        question = setUpQuestionPage(quizQls);
        primaryStage.setScene(question);
      } // else if there is no next question
      else if (currIndex == (quizGenerator.userRecord.getNumRqst() - 1)) {
        score = setUpScorePage();
        primaryStage.setScene(score);
      }
    });

    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return sc;
  }

  /**
   * Define the behavior of submit button on question page
   * 
   * @param tgG is the toggle group for choices
   * @param quizQls is the question array for generated list of questions
   * @param indicator is the label to indicate whether the user has answered the question right
   * @param choices is the readioButton obervableList of choices
   * @param nextBt is the button to click on to go to the next question
   * @param submitBt is the button to click to submit the current question
   * @param choiceLs is a list of choices in ListView form
   */
  private void submitQuestionBt(ToggleGroup tgG, Question[] quizQls, Label indicator,
      ObservableList<RadioButton> choices, Button nextBt, Button submitBt,
      ListView<RadioButton> choiceLs) {
    // Set indicator button and next button
    if (tgG.getSelectedToggle() == null)
      return;
    // get correct description
    String correctDscr = quizQls[currIndex].getCorrectChose().getDescription();
    RadioButton s = (RadioButton) tgG.getSelectedToggle(); // get selected button

    if (s.getText().equals(correctDscr)) {
      quizGenerator.userRecord.incrementNumCor();
      indicator.setText("Correct");
      indicator.getStyleClass().add("correctLabel");
    } else {
      indicator.setText("Wrong");
      indicator.getStyleClass().add("wrongLabel");
    }


    // display correct and wrong answers
    for (RadioButton b : choices) {

      if (b.getText().equals(correctDscr)) {
        // set correct answer green
        b.getStyleClass().add("greenButton");
      } else {
        // set wrong answer red
        b.getStyleClass().add("redButton");
      }


    }
    choiceLs.setItems(choices);


    indicator.setVisible(true);
    nextBt.setVisible(true);
    // choiceLs.setDisable(true);
    submitBt.setVisible(false);
  }

  /**
   * A pop up window to warn user when the user quit the quiz before finish
   */
  private void popUpQuitQuestion() {
    BorderPane popRt = new BorderPane();
    Scene popSc = new Scene(popRt, 750, 300);
    final Stage dialog = new Stage();

    Label prompt = new Label("Do you want to exit the quiz?");
    prompt.getStyleClass().add("normalText");
    prompt.isWrapText();
    Image warn = new Image(new File("warn.png").toURI().toString());
    ImageView warnV = new ImageView(warn);
    HBox topBox = new HBox(warnV, prompt);
    topBox.setAlignment(Pos.CENTER);
    topBox.setPadding(new Insets(50, 20, 20, 20));
    topBox.getStyleClass().add("topHBox");
    popRt.setTop(topBox);

    Button leaveBt = new Button("Exit without saving");
    Button cancelBt = new Button("Cancel");
    HBox centerBox = new HBox(leaveBt, cancelBt);
    centerBox.setSpacing(30);
    centerBox.setAlignment(Pos.CENTER);
    centerBox.setPadding(new Insets(50, 50, 50, 50));
    popRt.setCenter(centerBox);


    leaveBt.setOnAction(e -> {
      primaryStage.setScene(setUpMainMenuPage());
      dialog.close();
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
   * 
   * @return the Scene object of a new score page
   */
  private Scene setUpScorePage() {
    BorderPane root = new BorderPane();
    VBox centerVBox = new VBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();

    // define elements appearance
    // =========
    // Top
    // =========
    Label title = new Label("Quiz Generator");
    title.setAlignment(Pos.CENTER);
    title.getStyleClass().add("title");
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    // =========
    // Center
    // =========
    int requested = quizGenerator.userRecord.getNumRqst();
    int correct = quizGenerator.userRecord.getNumCor();
    int correctPercentage = quizGenerator.userRecord.getPercent();
    Label numReqsted = new Label("Number of Questions Answered: " + requested);
    Label numCorrect = new Label("Number of Questions Answered Correctly: " + correct);
    Label numCorrectPercentage = new Label("Percentage Correct: " + correctPercentage + "%");

    Button startNewQuizBt = new Button();
    startNewQuizBt.setText("Start a New Quiz with the same settings");
    startNewQuizBt.getStyleClass().add("NormalButton");
    Button changeSettingBt = new Button();
    changeSettingBt.setText("Start a New Quiz with different settings");
    changeSettingBt.getStyleClass().add("NormalButton");
    Button endBt = new Button();
    endBt.setText("End Quiz Generator");
    endBt.getStyleClass().add("NormalButton");

    centerVBox.getChildren().addAll(numCorrect, numReqsted, numCorrectPercentage, startNewQuizBt,
        changeSettingBt, endBt);
    centerVBox.setAlignment(Pos.CENTER);
    centerVBox.setSpacing(30);

    // =========
    // Bottom
    // =========
    Button backButton = new Button();
    backButton.setText("Return");
    backButton.getStyleClass().add("backButton");
    bottomHBox.getChildren().addAll(backButton);
    bottomHBox.getStyleClass().add("bottomHBox");
    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);

    // define functions when different buttons on this page is triggered
    // Lambda Expression
    startNewQuizBt.setOnMouseClicked(event -> {
      resetSameSetting();
    });
    changeSettingBt.setOnMouseClicked(event -> {
      resetDifferentSetting();
      primaryStage.setScene(questionFilter);
    });
    endBt.setOnMouseClicked(event -> {
      try {
        exitBt();
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    backButton.setOnMouseClicked(event -> {
      resetDifferentSetting();
      primaryStage.setScene(mainMenu);
    });
    // set up border pane by elements
    root.setTop(topHBox);
    root.setCenter(centerVBox);
    root.setBottom(bottomHBox);

    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    // primaryStage.setScene(sc);
    return sc;
  }

  /**
   * Reset the program fields if user want to restart quiz with a different setting
   */
  private void resetDifferentSetting() {
    quizGenerator.userRecord.setNumRqst(0);
    quizGenerator.userRecord.setNumCor(0);
    quizGenerator.userRecord.topicsChosen = new ArrayList<String>();
    questionFilter = setUpQuestionFilterPage();

  }

  /**
   * Reset the program fields if user want to restart quiz with the same setting
   */
  private void resetSameSetting() {
    quizGenerator.userRecord.setNumCor(0);
    currIndex = 0;
    Question[] quizQls = quizGenerator.generateQuestionList();
    question = setUpQuestionPage(quizQls);
    primaryStage.setScene(question);
  }

  /**
   * Build a new current database page
   * 
   * @return the Scene object of a new current database page
   */
  private Scene setUpCurrDataBasePage() {
    BorderPane root = new BorderPane();// the root layout
    HBox centerHBox = new HBox();
    HBox topHBox = new HBox();
    HBox bottomHBox = new HBox();

    // =========
    // Top
    // =========
    Label title = new Label("Quiz Generator");
    title.getStyleClass().add("title");
    topHBox.getChildren().add(title);
    topHBox.setAlignment(Pos.CENTER);
    topHBox.setPrefHeight(50);
    topHBox.getStyleClass().add("topHBox");

    // =========
    // Center
    // =========
    VBox centerVBox1 = new VBox();
    centerVBox1.setAlignment(Pos.TOP_LEFT);
    VBox centerVBox2 = new VBox();
    centerVBox1.setAlignment(Pos.TOP_RIGHT);

    // ============
    // Center Left
    // ============
    Label topic = new Label("Current list of topics:");
    topic.setPrefHeight(50);

    // hard code for topics combo list
    ObservableList<String> topicLs = FXCollections.observableArrayList(quizGenerator.topic);
    FXCollections.sort(topicLs);
    ListView<String> topicList = new ListView<String>();
    topicList.setItems(topicLs);

    centerVBox1.getChildren().addAll(topic, topicList);
    // centerVBox1.getChildren().addAll(topic,topicLs);
    centerVBox1.setAlignment(Pos.TOP_LEFT);

    // ============
    // Center Right
    // ============
    Label num = new Label("Number of questions in each topic:");
    num.setPrefHeight(50);

    ObservableList<Integer> numQLs = FXCollections.observableArrayList();
    for (int i = 0; i < topicLs.size(); i++) {
      String topicName = topicLs.get(i);
      numQLs.add(quizGenerator.questionBank.get(topicName).size());
    }

    ListView<Integer> numQList = new ListView<Integer>();
    numQList.setItems(numQLs);

    centerVBox2.getChildren().addAll(num, numQList);
    // centerVBox2.getChildren().addAll(num,numQList);
    centerVBox2.setAlignment(Pos.TOP_RIGHT);

    centerHBox.getChildren().addAll(centerVBox1, centerVBox2);
    centerHBox.setAlignment(Pos.CENTER);
    centerHBox.setSpacing(10.0);

    // =========
    // Bottom
    // =========
    Button backBt = new Button("Return");
    backBt.getStyleClass().add("backButton");
    Button saveQBt = new Button();
    saveQBt.setText("Write Current Question Database Into File");
    saveQBt.getStyleClass().add("NormalButton");

    bottomHBox.getChildren().addAll(saveQBt, backBt);
    bottomHBox.getStyleClass().add("bottomHBox");
    bottomHBox.setAlignment(Pos.BASELINE_RIGHT);

    root.setTop(topHBox);
    root.setCenter(centerHBox);
    root.setBottom(bottomHBox);

    // events
    backBt.setOnMouseClicked(event -> {
      primaryStage.setScene(mainMenu);
    });

    saveQBt.setOnMouseClicked(event -> {
      saveCurrQuestionBankBt();
    });

    // set scene
    Scene sc = new Scene(root, 1200, 800);
    sc.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    return sc;
  }

  /**
   * Define the save current question bank button behavior in the curr database page
   */
  private void saveCurrQuestionBankBt() {
    boolean saved = quizGenerator.fileHandler.saveFile(quizGenerator.questionBank);
    if (saved) {
      Alert alert = new Alert(AlertType.INFORMATION, "You quiz has been saved");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      quizGenerator.userRecord.setCurrQuizSaved(true);
    } else {
      Alert alert = new Alert(AlertType.INFORMATION, "Your quiz was not saved");
      alert.initModality(Modality.APPLICATION_MODAL);
      alert.initOwner(primaryStage);
      alert.showAndWait().filter(response -> response == ButtonType.OK);
      quizGenerator.userRecord.setCurrQuizSaved(false);
    }

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

    Label prompt = new Label("Are you sure you want to exit?/nQuestion will not be saved");
    prompt.getStyleClass().add("smallText");

    Button exit = new Button();
    exit.setText("Exit");
    exit.getStyleClass().add("popUpButton");
    Button notExit = new Button();
    notExit.setText("Continue adding questions");
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
   * 
   * @param args
   */
  public static void main(String[] args) {
    launch(args);
  }
}
