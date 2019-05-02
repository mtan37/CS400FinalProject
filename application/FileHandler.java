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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Handles files for the QuizGenerator class; reads/writes json files
 * 
 * @author Nate Sackett, Hui Beom, Marvin Tan
 */
public class FileHandler {

  // Hash table uses the Question topics as the keys and stores ArrayLists of Questions as values
  Hashtable<String, ArrayList<Question>> questionBank; 

  /**
   * Basic constructor (no-argument)
   */
  public FileHandler() {}

  /**
   * Show user dialog to select file to be read in
   * 
   * @return ArrayList<Question> a list of questions generated from the json file or null if an exception occurs
   */
  public ArrayList<Question> pickFile() {
    // Initialize a FileChooser
    FileChooser fileChooser = new FileChooser();
    
    // Set the window title, initial directory, and only allow json files
    fileChooser.setTitle("Open JSON file");
    fileChooser.setInitialDirectory(new File("./"));
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.JSON", "*.json"));

    // Create a File object and 
    File file = fileChooser.showOpenDialog(null);
    
    // Return null when the fileChooser dialog is exited
    if (file == null)
      return null;
    
    ArrayList<Question> questionList = readFile(file);
    return questionList;
  }

  /**
   * Reads a json file and inserts question data into bank of questions
   * 
   * @param file: file to be read from
   * @return ArrayList<Question> a list of questions generated from the JSON file or null if exception occurs
   */
  @SuppressWarnings("rawtypes")
  public ArrayList<Question> readFile(File file) {
    try {
      ArrayList<Question> questionList = new ArrayList<Question>();

      // Parse file to create JSON object
      Object obj = new JSONParser().parse(new FileReader(file));
      JSONObject jo = (JSONObject) obj; // cast to JSON object

      // Store JSONArray of questions and create iterator to parse array
      JSONArray questionArray = (JSONArray) jo.get("questionArray");
      Iterator questionIterator = questionArray.iterator();
      
      // Iterate through array while more questions exist
      while (questionIterator.hasNext()) {
        JSONObject objQ = (JSONObject) questionIterator.next();
        Question q = new Question(); // Create a new empty question

        // Set question meta-data
        q.setMetadata(objQ.get("meta-data").toString());

        // Set text of question
        q.setDescription(objQ.get("questionText").toString());

        // Set topic of question
        q.setTopic(objQ.get("topic").toString());
        
        // Set image of question
        String imageAddress = (String) objQ.get("image");
        q.saveImage(imageAddress);

        // Store JSONArray of choices for the question and create iterator to parse
        // array
        JSONArray choiceArray = (JSONArray) objQ.get("choiceArray");
        Iterator choiceIterator = choiceArray.iterator();

        // Create counter for number of choices and ignore all choices beyond the fifth
        int choiceCounter = 0;

        // Loop through choices, not exceeding 5 choices
        while (choiceIterator.hasNext() && choiceCounter < 5) {
          JSONObject objC = (JSONObject) choiceIterator.next();
          
          // Create choice with default values
          Choice c = new Choice(false, null);

          // Get and update truth value for choice
          String truthValue = objC.get("isCorrect").toString();
          if(truthValue.compareTo("T") == 0)
            c.setIsCorrect(true);
          else {
            c.setIsCorrect(false);
          }

          // Get and update choice description
          c.setDescription(objC.get("choice").toString());

          // Add completed choice to question
          q.addChoice(c);

          // Increment choice counter
          choiceCounter++;
        }

        // Add completed question to question bank
        questionList.add(q);
      }
      return questionList;
      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return null;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    } catch (ParseException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Saves questions in the question bank to a json file
   */
  @SuppressWarnings("unchecked") // For multiple unchecked type casts in making json objects
  public boolean saveFile(Hashtable<String, ArrayList<Question>> questionBank) {
    try {
      // Get save name and location from user
      File saveAddress = pickSaveFile();
      
      // Check that a save location was specified
      if (saveAddress == null)
        throw new FileNotFoundException();
      
      // Create printwriter immediately to check for valid address
      PrintWriter printWriter = new PrintWriter(saveAddress);

      // Create new json object to add question bank to
      JSONObject jsonOutput = new JSONObject();

      // Create new json array for the questions
      JSONArray questionArray = new JSONArray();

      if(questionBank == null) {
        System.out.println("null");
      }
      // Parse the hash table of questions by topic
      questionBank.forEach((topic, questionList) -> { // String topic, ArrayList<Question> questionList

        // Parse the question list
        questionList.forEach(question -> {

          // Create a JSON object for each question
          JSONObject jsonQuestion = new JSONObject();

          // Add meta-data to question
          jsonQuestion.put("meta-data", question.getMetadata());

          // Add question text to question
          jsonQuestion.put("questionText", question.getDescription());

          // Add topic to question
          jsonQuestion.put("topic", topic);

          // Add image to question
          jsonQuestion.put("image", question.getImageAddress());

          // Prepare to add choices to question
          // Create choiceArray for the question
          JSONArray choiceArray = new JSONArray();

          // Populate choiceArray
          ArrayList<Choice> choices = question.getChoices();
          for (Choice c : choices) {
            //create the JSONObject represents choices at position i of choiceArray
            JSONObject currChoice = new JSONObject();
            
            // Add truth value to map
            if(c.getIsCorrect())
              currChoice.put("isCorrect", "T");
            else
              currChoice.put("isCorrect", "F");

            // Add choice description to map
            currChoice.put("choice", c.getDescription());

            // Add choice map to choiceArray
            choiceArray.add(currChoice);
          }

          // Add choices to question
          jsonQuestion.put("choiceArray", choiceArray);

          // Add question to questionArray
          questionArray.add(jsonQuestion);

        }); // End lambda for Questions
      }); // End lambda for HashTable

      // Add questionArray to main json object
      jsonOutput.put("questionArray", questionArray);

      // Use printWriter created at beginning of program to save json object
      printWriter.write(jsonOutput.toJSONString());
      
      // Remove everything from printWriter stream and close
      printWriter.flush();
      printWriter.close();
      return true;
      
    } catch (FileNotFoundException e) {
      // No save location selected. This is okay. Do nothing.
      return false;
    }
  }
  
  /**
   * Helper method for saveFile opens user dialog to select save location
   * 
   * @return File for save address
   */
  private File pickSaveFile() {
    // Initialize a FileChooser to choose a save location
    FileChooser fileChooser = new FileChooser();
    
    // Set title so user knows to pick a save location
    fileChooser.setTitle("Save File:");
    
    // Set default save name
    fileChooser.setInitialFileName("questionBank.json");

    // Prompt user for save location
    File saveAddress = fileChooser.showSaveDialog(null);
    
    // If saveAddress is null don't proceed
    if (saveAddress == null)
      return null;
    
    // Return the save address
    return saveAddress;
  }
}
