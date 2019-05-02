package application;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

/**
 * This class generates and manipulate the quizzes
 * 
 * @author Nate Sackett, Shao Bin Daniel Shi Hong
 */
public class QuizGenerator {
  FileHandler fileHandler; // Handles reading/writing json files
  UserRecord userRecord; // Holds user's actions
  Hashtable<String, ArrayList<Question>> questionBank; // HashTable holds ArrayLists of questions by topic
  ArrayList<String> topic; // all topics in the questionBank

  /**
   * Constructor initializes all instance variables as empty
   */
  public QuizGenerator() {
    this.fileHandler = new FileHandler();
    this.userRecord = new UserRecord();
    this.questionBank = new Hashtable<String, ArrayList<Question>>();
    this.topic = new ArrayList<String>();
    this.userRecord.topicsChosen = new ArrayList<String>();

  }

  /**
   * Load file from a JSON file
   * 
   * @return true if file successfully loaded
   */
  public boolean loadFile() {
    // Get ArrayList of questions using the file handler
    ArrayList<Question> questionList = fileHandler.pickFile();

    // Return false when file not loaded
    if (questionList == null)
      return false;

    // Repeatedly call addQuestion to add the questions to the question bank
    Iterator<Question> it = questionList.iterator();
    while (it.hasNext()) {
      Question currQ = it.next();
      addQuestion(currQ);
    }
    // Return true upon successful completion
    return true;
  }

  /**
   * This method searches based on the question topic and adds the question under
   * the correct topic
   * 
   * @param question
   */
  public boolean addQuestion(Question question) {

    // Stop if question is null
    if (question == null) {
      return false;
    }

    String qTopic = question.topic;
    if (topic.contains(qTopic)) { // check if we have this topic in our Qbank
      // if do, directly add
      questionBank.get(qTopic).add(question);

    } else { // if not, add a new topic

      ArrayList<Question> newQuestionList = new ArrayList<>();
      newQuestionList.add(question);
      questionBank.put(qTopic, newQuestionList);
      topic.add(qTopic); // add the new topic

    }
    return true;
  }

  /**
   * This method clears isFetched for all questions to prepare for the next qui.
   */
  private void clear() {
    for (int i = 0; i < topic.size(); i++) {
      ArrayList<Question> questionList = questionBank.get(topic.get(i));
      for (int j = 0; j < questionList.size(); j++) {
        questionList.get(j).isFetched = false;
      }
    }
  }

  /**
   * This method returns size of questions under a specified topic
   * 
   * @param topicList List of topics requested by User
   * @return number of questions associated with topicList
   */
  private int getQuestionSize(ArrayList<String> topicList) {
    int size = 0;
    for (int i = 0; i < topicList.size(); i++) {
      size += questionBank.get(topicList.get(i)).size();
    }
    return size;
  }

  /**
   * This method generates a random QuestionList based on the currChosenTopic and
   * userRecord.numRqsts requested by user
   * 
   * @return a list of random questions, null if failed.
   */
  public Question[] generateQuestionList() {
    // return null if we don't have enough questions.
    int questionSize = getQuestionSize(userRecord.topicsChosen);
    if (questionSize < userRecord.getNumRqst()) {
      userRecord.setNumRqst(questionSize);
    }

    clear(); // clear the history data from last quiz

    ArrayList<String> topicList = new ArrayList<>();
    Random rand = new Random();
    // copy the topic List
    for (int i = 0; i < userRecord.topicsChosen.size(); i++) {
      topicList.add(userRecord.topicsChosen.get(i));
    }

    // list with random question
    Question[] qList = new Question[userRecord.getNumRqst()];

    int numberQused = 0; // record how many questions have been generated
    int numTopic = topicList.size();
    int topicPosition = rand.nextInt(numTopic);
    String randTopic = topicList.get(topicPosition); // pick random topic.
    // get would return list of questions associated with this topic
    ArrayList<Question> questionList = questionBank.get(randTopic);

    // pick random question in the list
    Question randQuestion = pickRandomQuestionOftopic(questionList);

    System.out.println("numberQused" + numberQused);
    System.out.println("request num Q" + userRecord.getNumRqst());
    while (numberQused < userRecord.getNumRqst()) {

      if (randQuestion == null) {
        // remove the topic as all questions under it have been fetched
        topicList.remove(randTopic);
        numTopic--;
      } else {
        qList[numberQused] = randQuestion;
        numberQused++;
      }

      topicPosition = rand.nextInt(numTopic);
      randTopic = topicList.get(topicPosition);
      questionList = questionBank.get(randTopic);
      randQuestion = pickRandomQuestionOftopic(questionList);// pick random question

    }

    return qList;

  }

  /**
   * This is the helper method that pick a random question from a questionList,
   * 
   * @param questionList
   * @return random question,null if all questions in the list have been fetched
   */
  private Question pickRandomQuestionOftopic(ArrayList<Question> questionList) {
    // This method returns a list of questions not yet fetched
    ArrayList<Question> notFetchedQuestion = checkAllFetched(questionList);
    int numQuestionReq = notFetchedQuestion.size();
    if (numQuestionReq == 0)
      return null; // all question fetched, return null
    Random rand = new Random();
    Question randQuestion = notFetchedQuestion.get(rand.nextInt(numQuestionReq));
    randQuestion.isFetched = true;
    return randQuestion;

  }

  /**
   * This is a private method checking if all questions in the questionList have
   * been fetched for generating the quiz.
   * 
   * @param questionList
   * @return a list of questions that aren't fetched
   */
  private ArrayList<Question> checkAllFetched(ArrayList<Question> questionList) {

    ArrayList<Question> notFetchedQuestion = new ArrayList<>();

    if (questionList == null) {
      return notFetchedQuestion; // size if zero, means all questions consumed.
    }

    for (int i = 0; i < questionList.size(); i++) {
      // if any question is not fetched,add to the notFetchedList
      Question question = questionList.get(i);
      if (!question.isFetched) {
        notFetchedQuestion.add(question);
      }

    }
    return notFetchedQuestion;

  }
}
