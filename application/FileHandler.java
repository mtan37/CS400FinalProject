package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 * Handles files for the QuizGenerator class
 * 
 * @author Nate Sackett
 * @author Hui Beom
 *
 */
public class FileHandler {

  Hashtable<String, ArrayList<Question>> questionBank; // Hash table-- key: question topic, value: ArrayList of
                                                       // questions

  /**
   * Constructor adds hash table containing the bank of questions for the quiz
   * 
   * @param questionBank
   */
  public FileHandler(Hashtable<String, ArrayList<Question>> questionBank) {

    this.questionBank = questionBank;
  }

  /**
   * Show user dialog to select file to be read in
   * @return ArrayList<Question> a list of questions generated from the JSON file or null if exception occur
   */
  public ArrayList<Question> pickFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open JSON file");
    fileChooser.setInitialDirectory(new File("./"));
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.JSON"));

    File file = fileChooser.showOpenDialog(null);
    ArrayList<Question> questionList = readFile(file);
    return questionList;
  }

  /**
   * Reads a json file and inserts question data into bank of questions
   * 
   * @param file: file to be read from
   * @return ArrayList<Question> a list of questions generated from the JSON file or null if exception occur
   */
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
   * 
   * @param saveAddress: location in the computer to save the json file
   */
  public void saveFile(String saveAddress) {
    // TODO: May need catch other exceptions, but I'm not sure which
    try {
      
      // Create printwriter immediately to check for valid address
      PrintWriter printWriter = new PrintWriter(saveAddress);

      // Create new json object to add question bank to
      JSONObject jsonOutput = new JSONObject();

      // Create new json array for the questions
      JSONArray questionArray = new JSONArray();

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
          jsonQuestion.put("image", question.loadImage());

          // Prepare to add choices to question
          // Create choiceArray for the question
          JSONArray choiceArray = new JSONArray();

          // Populate choiceArray
          ArrayList<Choice> choices = question.getChoices();
          for (Choice c : choices) {

            // Create a map for each choice
            Map mChoice = new LinkedHashMap(2);

            // Add truth value to map
            mChoice.put("isCorrect", c.getIsCorrect());

            // Add choice description to map
            mChoice.put("choice", c.getDescription());

            // Add choice map to choiceArray
            choiceArray.add(mChoice);
          }

          // Add choices to question
          jsonQuestion.put("choiceArray", choices);

          // Add question to questionArray
          questionArray.add(jsonQuestion);

        });

      });

      // Add questionArray to main json object
      jsonOutput.put("questionArray", questionArray);

      // Use printWriter created at beginning of program to save json object
      printWriter.write(jsonOutput.toJSONString());
      
    } catch (FileNotFoundException e) {
      // TODO: Not a valid save address
    } catch (IOException e) {
      // TODO: 
    }
  }
}
