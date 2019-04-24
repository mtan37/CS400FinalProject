package application;
import java.awt.Image;
import java.util.ArrayList;


/**
 * This is the class represents 
 * quiz question
 * @author SHAO BIN DANIEL SHI HONG
 *
 */
public class Question {
  
 private boolean isFetched = false;  //  This field checks if the Question is fetched by Used
 private ArrayList<Choice> choices;  //  choices relates to this question
 private int userAnswer;  //  the index used indicate the User's choice in ArrayList<Choice> choices.
 private String description;  //  description of the Question
 private String topic;  //  the topic of Question
 Image image;  //  the image of the Question
 
 /**
  * Question Constructor
  * @param topic the quetsion's topic 
  * @param choices the question's choices
  * @param description the question's description
  */
 Question(String topic, ArrayList<Choice> choices, String description){
   this.topic = topic;
   this.choices = choices;
   this.description = description;
   
 }
 /**
  * Setter of isFetched
  * @param yesOrNo is the Question Fetched or Not
  */
 public void setIsFetched(boolean yesOrNo) {
       isFetched = yesOrNo;
 }
 /**
  * Getter of isFetched
  * @return true if fetched false otherwise
  */
 public boolean getIsFetched() {
      return isFetched;
 }
 /**
  * Getter of the Question's description
  * @return the Question's description
  */
 public String getDescription() {
    return description;
 }
 /**
  * Setter of the Question's Description
  * @param newDescription  
  */
 public void changeDescription(String newDescription) {
    this.description = newDescription;
 }
 /**
  * Setter of topic
  * @param topic
  */
 public void setTopic(String topic) {
    this.topic= topic;
 }
 /**
  * Getter of topic
  * @return
  */
 public String getTopic() {
    return topic;
 }
 /**
  * This method stores an image related to the Question
  * to the Question object
  * @param image
  */
 public void saveImage(Image image) {
    this.image = image;
 }
 /**
  * This method returns the image of the question
  * @return image of the question.
  */
 public Image loadImage() {
   return image;
 }
}
