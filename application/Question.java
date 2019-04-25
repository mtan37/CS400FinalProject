package application;

import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * This class represents the Question of the Quiz
 * @author SHAO BIN DANIEL SHI HONG
 *
 */
public class Question {
  private String description; // description of the question
  private boolean isFetched; // if this Question is Fetched
  private ArrayList<Choice> choices; // choices of the current Question
  private int userAnswer; // this indicates which question user is currently Choosing
  private String topic; 
  private Image image;
  
  /**
   * Constructor 
   * @param topic 
   * @param choices 
   * @param description
   */
  public Question(String topic, ArrayList<Choice> choices, String description){
    this.topic = topic;
    this.choices = choices;
    this.description = description;
  }
  /**
   * Getter of the Question Description
   * @return the Question Description 
   */
  public String getDescriptino() {
    return description;
  }
  /**
   * Setter of the Question Description
   */
  public void setDescription(String des) {
     description = des;
  }
  /**
   * Getter of IsFetched
   * @return true if this Question is fetched for use, false otherwise
   */
  public boolean getIsFetched() {
    return isFetched;
  }
  /**
   *  Setter of isFetched
   * @param isFetchedOrNot
   */
  public void setIsFetched(boolean isFetchedOrNot) {
      isFetched = isFetchedOrNot;
  }
  /**
   * Getter of topic
   * @return
   */
  public String getTopic() {
    return topic;
  }
  /**
   * Setter of topic
   * @param topic
   */
  public void setTopic(String topic) {
     this.topic = topic;
  }
  /**
   * Getter of the Image
   * @return image
   */
  public Image loadImage() {
    return image;
  }
  /**
   * Setter of the image
   * @param image
   */
  public void saveImgae(Image image) {
    this.image = image;
  }
  /**
   * This method sets which number of choice the User
   * is choosing
   * @param answerNumber the number of the User choice
   */
  public void setUserAnswer(int answerNumber) {
      this.userAnswer = answerNumber;
  }
  /**
   * Getter of UserAnswer
   * @return the User's answer number
   */
  public int getUserAnswer() {
      
      return userAnswer;
    
  }
  
}