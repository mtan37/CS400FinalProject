package application;


import java.util.ArrayList;

/**
 * Represents the choices of the user
 * 
 * @author Nate Sackett
 *
 */
public class UserRecord {
  ArrayList<String> topicsChosen; // ArrayList of topics chosen by the user
  private Integer numAns;
  private Integer numRqst;
  private Integer numCor;
  private boolean currQuizSaved;// show if the questions in current quiz have been saved by the user

  /**
   * No-arg constructor initializes UserRecord with no choices or topics chosen
   */
  public UserRecord() {
    topicsChosen = new ArrayList<String>();
    numAns = 0;
    numRqst = 0;
    numCor = 0;
    currQuizSaved = false;
  }

  
  /**
   * Specifies the number of questions answered by the user
   * 
   * @param numRqst integer number of questions answered
   */
  protected void setNumAns(Integer numAns) {
    this.numAns = numAns;
  }
  
  /**
   * Specifies the number of questions requested by the user
   * 
   * @param numRqst integer number of questions requested
   */
  protected void setNumRqst(Integer numRqst) {
    this.numRqst = numRqst;
  }

  /**
   * Specifies the muber of questions answered correctly by the user
   * 
   * @param numCor integer number of questions answered correctly (< number of
   *               questions requested)
   */
  protected void setNumCor(Integer numCor) {
    this.numCor = numCor;
  }

  /**
   * Increases the number of correctly answered questions by one
   */
  protected void incrementNumCor() {
    numCor++;
  }

  /**
   * Return the number of questions answered by the user
   * 
   * @return number of question answered
   */
  protected Integer getNumAns() {
    return numAns;
  }
  
  /**
   * Return the number of questions requested by the user
   * 
   * @return number of question requested
   */
  protected Integer getNumRqst() {
    return numRqst;
  }

  /**
   * Return the number questions answered correctly by the user
   * 
   * @return
   */
  protected Integer getNumCor() {
    return numCor;
  }

  /**
   * Get the integer percentage of questions answered correctly by the user
   * 
   * @return percentage of correct answers
   */
  protected int getPercent() {
    return (int) ((int) (float) numRqst / (float) numCor * 100);
  }

  /**
   * Returns true if current quiz question have been saved by the user, otherwise
   * false
   * 
   * @return true if current quiz question have been saved by the user, otherwise
   *         false
   */
  protected boolean isCurrQuizSaved() {
    return currQuizSaved;
  }

  /**
   * Sets if current quiz is saved by the user
   * @param currQuizSaved boolean indicated whether quiz has been saved
   */
  protected void setCurrQuizSaved(boolean currQuizSaved) {
    this.currQuizSaved = currQuizSaved;
  }
}
