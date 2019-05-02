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

/**
 * Describes an exception thrown when a correct answer is removed from a
 * question in a quiz
 */
//@SuppressWarnings("serial")
public class corAnsRemovedException extends Exception {
  
  private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "The correct choice is going to be removed. "
            + "Please decide/input a new correct answer or Cancel";
    }
}