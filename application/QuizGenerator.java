package application;

import java.util.ArrayList;
import java.util.Hashtable;

public class QuizGenerator {
	FileHandler fileHandler;
	UserRecord userRecord;
	Hashtable<String, ArrayList<Question>> questionBank = new Hashtable<String, ArrayList<Question>>();
	ArrayList<String> topic = new ArrayList<String>();
	public QuizGenerator() {
		this.fileHandler = new FileHandler();
		this.userRecord = new UserRecord();
	}
	
	public void loadFile() {
		fileHandler.readFile();
	}
	
	public void loadQuestion() {
		
	}
	
	public void keepRecord() {
		
	}
}
