package application;


import java.util.ArrayList;
import java.util.Hashtable;
/**
 * This class generates and manipulate
 * the quizzes
 * @author  Nate Sackett
 *          SHAO BIN DANIEL SHI HONG
 */
public class QuizGenerator {
  FileHandler fileHandler;
  UserRecord userRecord;
  Hashtable<String, ArrayList<Question>> questionBank;
  ArrayList<String> topic; // all topics in the questionBank
  ArrayList<String> currChosenTopics;// new add by Marvin
  Integer numQuestion;// show total number of questions, added by Marvin
  Integer numQUsed;// Shows how many questions have been used already

  public QuizGenerator() {
    this.fileHandler = new FileHandler(questionBank);
    this.userRecord = new UserRecord();
    this.questionBank = new Hashtable<String, ArrayList<Question>>();
    this.topic = new ArrayList<String>();
    this.currChosenTopics = new ArrayList<String>();
    this.numQuestion = new Integer(0);
    numQUsed = 0;
  }

  public void loadFile() {
    fileHandler.pickFile();
  }

  public Question loadQuestion() {// return null if no more question needed(numQUsed == numQuestion)
    return null;

  }

  public Question testLoadQuestion() {
    return null;

  }

  public void keepRecord() {

  }

  /**
   * Added by Daniel This method searches based on the topic and add the question under this topic
   * 
   * @param question
   */
  public boolean addQuestion(Question question) { 
    
    
    if (question == null) {
      return false; // might throw NullKeyException in the future
    }
  
    String qTopic = question.topic;
    if (topic.contains(qTopic)) { // check if we have this topic in our Qbank
      //if do, directly add 
      questionBank.get(qTopic).add(question);

    } else { // if not, add a new topic 

      ArrayList<Question> newQuestionList = new ArrayList<>();
      newQuestionList.add(question);
      questionBank.put(qTopic, newQuestionList);
      topic.add(qTopic); // add the new topic

    }

    numQuestion++;
    return true;
  }
  public boolean removeQuestion(Question question) {
    //TO _ DO : need to discuss more about 
    // what argument is most convenient
      return false;
  }
  public Question getQuestion() {
      //to-do//
      return null;
  }
  
}

