package application;

import java.util.ArrayList;
import java.util.Hashtable;

public class QuizGenerator {
	FileHandler fileHandler;
	UserRecord userRecord;
	Hashtable<String, ArrayList<Question>> questionBank;
	ArrayList<String> topic;
	ArrayList<String> currChosenTopics;//new add by Marvin
	Integer numQuestion;//show total number of questions, added by Marvin
	public QuizGenerator() {
		this.fileHandler = new FileHandler(questionBank);
		this.userRecord = new UserRecord();
		this.questionBank = new Hashtable<String, ArrayList<Question>>();
		this.topic = new ArrayList<String>();
		this.currChosenTopics = new ArrayList<String>();
		this.numQuestion = new Integer(0);
	}
	
	public void loadFile() {
		fileHandler.pickFile();
	}
	
	public void loadQuestion() {
		
	}
	
	public void keepRecord() {
		
	}
}
