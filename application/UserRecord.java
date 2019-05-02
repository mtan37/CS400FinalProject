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

/**
 * Represents the choices of the user
 * 
 * @author Nate Sackett
 *
 */
public class UserRecord {
  ArrayList<String> topicsChosen; // ArrayList of topics chosen by the user
  private Integer numRqst;//show total number of questions requested by the user
  private Integer numCor;
  private boolean currQuizSaved;// show if the questions in current quiz have been saved by the user

  /**
   * No-arg constructor initializes UserRecord with no choices or topics chosen
   */
  public UserRecord() {
    topicsChosen = new ArrayList<String>();
    numRqst = 0;
    numCor = 0;
    currQuizSaved = false;
  }
  
  /**
   * Specifies the number of questions requested by the user
   * 
   * @param numRqst integer number of questions requested
   */
  protected void setNumRqst(int numRqst) 
  {
    System.out.println("numRqst set from " + this.numRqst + "to" + numRqst);
    this.numRqst = new Integer(numRqst);
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
    int percent = (int)((float) numCor / (float) numRqst * 100);
    return percent;
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
