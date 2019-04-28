

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
  public String topic; 
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
  public void addChoice(Choice choice){ //To do, how many correct answers are allowed?
                                       // need to change the code based on the number of cor ans.
    if(choice == null) {
      return;
    }
    if (choices.size() < 5)
      choices.add(choice);
  }
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
   * Setter of the image
   * 
   * @param image
   */
  public void saveImage(Image image) {
    this.image = image;
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
  public void setMetadata(String metadata) {
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
  

}