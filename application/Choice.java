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
 * This Class represents a choice for a quiz
 * 
 * @author Shao Bin Daniel Shi Hong
 */
public class Choice {
   
   
   private boolean isCorrect;  //  Indicates whether this Choice is correct
   private String description;  //  The choice description/text
   
   /**
    * Choice Constructor initializes the choice description and its truth value
    * 
    * @param isCorrect indicates if the choice is correct 
    * @param description: describes the choice
    */
   public Choice(boolean isCorrect, String description) {
     
        this.isCorrect = isCorrect;
        this.description = description;
       
   }
   
   /**
    * Getter of isCorrect; returns boolean indicating whether this choice is true
    * 
    * @return true if is correct, false otherwise
    */
   public boolean getIsCorrect() {
     return isCorrect;
   }
   
   /**
    * Setter of isCorrect; sets whether choice is true or false
    * 
    * @param isCorrect: truth value of this choice
    */
   public void setIsCorrect(boolean isCorrect) {
     this.isCorrect = isCorrect;
   }
   
   /**
    * Getter of the choice description; returns the actual text of the choice
    * 
    * @return the description of the choice
    */
   public String getDescription() {
     return description;
   }
   
   /**
    * Setter of the description; sets the text describing the choice
    * 
    * @param des: the text describing the choice
    */
   public void setDescription(String des) {
     this.description = des;
   }
}
