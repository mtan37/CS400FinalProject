package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class FileHandler {
	
	JSONParser parser;
	Hashtable< String, ArrayList<Question> > questionBank;
	
	
	public FileHandler(Hashtable< String, ArrayList<Question> > questionBank) {
		parser = new JSONParser();
		this.questionBank = questionBank;
	}
	
	public void pickFile() {
		FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open JSON file");
    	fileChooser.setInitialDirectory(new File("./"));
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON Files", "*.JSON"));
    	
    	File file = fileChooser.showOpenDialog(null);
    	readFile(file);
	}
	
	public boolean readFile(File file) {
		try {

		
	    	Object obj = parser.parse(new FileReader(file));
			JSONObject jo = (JSONObject) obj;
			System.out.println(obj);
		
		//TODO:
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    return false; // TODO:
	}
	
	public void saveFile() {
		
	}
}
