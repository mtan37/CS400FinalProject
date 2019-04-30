package application;


import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * This class represents the Question of the Quiz
 * 
 * @author SHAO BIN DANIEL SHI HONG
 * @author Nate Sackett
 * @author Zhengyi Chen
 *
 */
public class Question {
  public String topic; // Question topic
  private String description; // description of the question
  private ArrayList<Choice> choices; // choices of the current Question
  private Image image; // Question image
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
  }
  
  /**
   * 3 argument constructor
   * 
   * @param topic       = question topic
   * @param choices     = array list of
   * @param description
   */
  public Question(String topic, ArrayList<Choice> choices, String description) {
    this.topic = topic.trim().toLowerCase();
    this.description = description;
    this.choices = choices;
    image = null; // No default image
    isFetched = false; // Initialize question not already having been used
    userAnswer = -1; // Default value indicates no answer chosen
    metadata = null; // Default to empty
    
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
   * @return
   */
  public ArrayList<Choice> getChoices() {
    return choices;
  }
  
  /**
   * Adds one question choice to the choices ArrayList; will not add a choice beyond 5 total choices
   * 
   * @param choice
   */
  public void addChoice(Choice choice){ // Method used in FileHandler, no other checks needed
    if (choices.size() < 5)
      choices.add(choice);
  }
  
  //TODO: Comment method parameters
  /**
   * This method remove choice from choices
   * based on the description of choice
   * @param choiceDes
   * @throws corAnsRemovedException 
   */
  public void removeChoice(String choiceDes) throws corAnsRemovedException {
      if(choiceDes == null || choices.size() == 0) {
        return;
      }
      Choice target = findChoice(choiceDes);
      if(target == null) {
        return;
      }
      //TO DO: what if the choice to be removed is the correct answer?
      // need to prompt the user to decide a new answer or cancel this removal
      // throw Exception
      if(target.getIsCorrect()) {
        throw new corAnsRemovedException();
      }
      choices.remove(target);    
  }
  
  private Choice findChoice(String des) {
    for(int i = 0; i < choices.size(); i++) {
       if(choices.get(i).getDescription().equals(des)) 
         return choices.get(i);
       
    }
    return null;
  }
  
  public Choice getChoice() {
    return null; // TO DO, argument need to be discussed.
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
   * Setter of the image(assume the imageAddress is valid)
   * 
   * @param image
   */
  public void saveImage(String imageAddress) {
    imageAddress = imageAddress.trim().toLowerCase();
    if(imageAddress.compareTo("none") == 0 || imageAddress.compareTo("") == 0) {
      imageAddress = ("application/wallpaper-icon.png");
    }
    image = new Image(imageAddress.trim());
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
  
  /**
   * This method sets the metadata String for the question
   * 
   * @param metadata
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
   * @return the correct choice; 
   */
  public Choice getCorrectChose() {
    for (int i=0; i< choices.size(); i++) {
      if(choices.get(i).getIsCorrect()) {
        return choices.get(i);
      }
    }
    return null;
  }

}