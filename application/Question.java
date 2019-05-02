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

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * This class represents the Question of the Quiz
 * 
 * @author Shao Bin Daniel Shi Hong, Nate Sackett, Zhengyi Chen
 *
 */
public class Question {
  public String topic; // Question topic
  private String description; // description of the question
  private ArrayList<Choice> choices; // choices of the current Question
  private Image image; // Question image
  private String imageAddress; // Question image
  protected boolean isFetched; // if this Question has been used
  private int userAnswer; // this indicates which answer has been chosen by the user
  private String metadata; // question meta-data

  /**
   * Default no-argument constructor (used in FileHandler)
   */
  public Question() {
    topic = null; // No initial topic
    description = null; // No initial description
    choices = new ArrayList<Choice>(); // Empty initial array list
    image = null; // No default image
    isFetched = false; // Initialize question not already having been used
    userAnswer = -1; // Default value indicates no answer chosen
    metadata = null; // Default to empty
    imageAddress = ""; // Empty image location
  }

  /**
   * 3 argument constructor
   * 
   * @param topic:       topic String for the Question
   * @param choices:     ArrayList of Choices for the question
   * @param description: the description/text for the question
   */
  public Question(String topic, ArrayList<Choice> choices, String description) {
    this.topic = topic.trim().toLowerCase();
    this.description = description;
    this.choices = choices;
    image = null; // No default image
    isFetched = false; // Initialize question not already having been used
    userAnswer = -1; // Default value indicates no answer chosen
    metadata = null; // Default to empty
    imageAddress = ""; // Empty image location
  }

  /**
   * Getter of the Question topic
   * 
   * @return the Question topic
   */
  public String getTopic() {
    return topic;
  }

  /**
   * Setter of the Question topic
   */
  public void setTopic(String topic) { // FileHandler
    this.topic = topic.trim().toLowerCase();
  }

  /**
   * Getter of the Question Description
   * 
   * @return the Question Description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Setter of the Question Description
   */
  public void setDescription(String description) { // FileHandler
    this.description = description;
  }

  /**
   * Getter of the Question choices
   * 
   * @return ArrayList of Question Choices
   */
  public ArrayList<Choice> getChoices() {
    return choices;
  }

  /**
   * Adds one question choice to the choices ArrayList; will not add a choice
   * beyond 5 total choices
   * 
   * @param choice
   */
  public void addChoice(Choice choice) { // Method used in FileHandler, no other checks needed
    if (choices.size() < 5)
      choices.add(choice);
  }

  /**
   * This method removes a Choice from choices ArrayList based on the description
   * 
   * @param choiceDes: the description String for choice
   * @throws corAnsRemovedException when the choice marked as correct is removed
   */
  public void removeChoice(String choiceDes) throws corAnsRemovedException {
    // Check that there is a choice to remove and the description is non-null
    if (choiceDes == null || choices.size() == 0) {
      return;
    }

    // Call findChoice to get the choice matching the description or null if no
    // match
    Choice target = findChoice(choiceDes);

    // If the choice is not found, exit removeChoice
    if (target == null)
      return;
    
    // Throw Exception if the choice to be removed is the correct answer and
    // prompt the user to decide a new answer or cancel this removal
    if (target.getIsCorrect())
      throw new corAnsRemovedException();

    // If choice has passed all checks, remove target choice
    choices.remove(target);
  }

  /**
   * Helper method for removeChoice returns the Choice object in the choices
   * ArrayList with the matching description or null if not found
   * 
   * @param des: the choice description
   * @return matching choice if found, otherwise null
   */
  private Choice findChoice(String des) {
    
    // Search choices ArrayList for Choice with matching description
    for (int i = 0; i < choices.size(); i++) {
      
      // Return matching choice if found
      if (choices.get(i).getDescription().equals(des))
        return choices.get(i);
    }
    // Return null if choice not found
    return null;
  }

  /**
   * Getter of the Image
   * 
   * @return image of question
   */
  public Image loadImage() {
    return image;
  }

  /**
   * Getter of the image address for the question
   * 
   * @return image address String
   */
  public String getImageAddress() {
    return imageAddress;
  }

  /**
   * Setter of the image(assume the imageAddress is valid)
   * 
   * @param imageAddress String
   */
  public void saveImage(String imageAddress) {
    imageAddress = imageAddress.trim().toLowerCase();
    if (imageAddress.compareTo("none") == 0 || imageAddress.compareTo("") == 0) {
      imageAddress = ("application/noImage.png");
    }
    image = new Image(imageAddress);
    this.imageAddress = imageAddress;
  }

  /**
   * This method set which choice the user has selected
   * 
   * @param answerNumber the integer representing the user's choice
   */
  public void setUserAnswer(int answerNumber) {
    this.userAnswer = answerNumber;
  }

  /**
   * Getter of UserAnswer
   * 
   * @return integer representing the user's answer
   */
  public int getUserAnswer() {
    return userAnswer;
  }

  /**
   * This method sets the metadata String for the question
   * 
   * @param metadata: additional data String for the question
   */
  public void setMetadata(String metadata) { // FileHandler
    this.metadata = metadata;
  }

  /**
   * This method returns the meta-data String for the question
   * 
   * @return metadata String for question
   */
  public String getMetadata() {
    return metadata;
  }

  /**
   * This method returns the correct choice for this question
   * 
   * @return the correct choice;
   */
  public Choice getCorrectChose() {
    // Search for correct answer
    for (int i = 0; i < choices.size(); i++) {
      if (choices.get(i).getIsCorrect()) {
        return choices.get(i);
      }
    }
    // Return null when no correct answer found
    return null;
  }
}