package application;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * This class represents the Question of the Quiz
 * 
 * @author SHAO BIN DANIEL SHI HONG
 * @author Nate Sackett
 *
 */
public class Question {
  private String topic; 
  private String description; // description of the question
  private ArrayList<Choice> choices; // choices of the current Question
  private Image image; // Question image
  private boolean isFetched; // if this Question has been used
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
    metadata = null; // Default empty
  }
  
  /**
   * 3 argument constructor
   * 
   * @param topic       = question topic
   * @param choices     = array list of
   * @param description
   */
  public Question(String topic, ArrayList<Choice> choices, String description) {
    this.topic = topic;
    this.description = description;
    this.choices = choices;
    image = null; // No default image
    isFetched = false; // Initialize question not already having been used
    userAnswer = -1; // Default value indicates no answer chosen
    metadata = null; // Dfault empty
    
  }

  /**
   * Getter of topic
   * 
   * @return
   */
  public String getTopic() {
    return topic;
  }

  /**
   * Setter of topic
   * 
   * @param topic
   */
  public void setTopic(String topic) {
    this.topic = topic;
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
  public void setDescription(String des) {
    description = des;
  }
  
  /**
   * Getter of the Question choices
   * @return
   */
  public ArrayList<Choice> getChoices() {
    return choices;
  }
  
  /**
   * Adds one question choice to the choices ArrayList; will not add a choice beyond 5 total choices
   * @param choice
   */
  public void addChoice(Choice choice) {
    if (choices.size() < 5)
      choices.add(choice);
  }

  /**
   * Getter of the Image
   * 
   * @return image
   */
  public Image loadImage() {
    return image;
  }

  /**
   * Setter of the image
   * 
   * @param image
   */
  public void saveImgae(Image image) {
    this.image = image;
  }
  
  /**
   * Getter of IsFetched
   * 
   * @return true if this Question is fetched for use, false otherwise
   */
  public boolean getIsFetched() {
    return isFetched;
  }

  /**
   * Setter of isFetched
   * 
   * @param isFetchedOrNot
   */
  public void setIsFetched(boolean isFetchedOrNot) {
    isFetched = isFetchedOrNot;
  }

  /**
   * This method sets which number of choice the User is choosing
   * 
   * @param answerNumber the number of the User choice
   */
  public void setUserAnswer(int answerNumber) {
    this.userAnswer = answerNumber;
  }

  /**
   * Getter of UserAnswer
   * 
   * @return the User's answer number
   */
  public int getUserAnswer() {
    return userAnswer;
  }

}