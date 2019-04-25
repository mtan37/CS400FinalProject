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
	Integer numQUsed;//Shows how many questions have been used already
	public QuizGenerator() {
		this.fileHandler = new FileHandler(questionBank);
		this.userRecord = new UserRecord();
		this.questionBank = new Hashtable<String, ArrayList<Question>>();
		this.topic = new ArrayList<String>();
		this.currChosenTopics = new ArrayList<String>();
		this.numQuestion = new Integer(0);
		numQUsed=0;
	}
	
	public void loadFile() {
		fileHandler.pickFile();
	}
	
	public Question loadQuestion() {//return null if no more question needed(numQUsed == numQuestion)
    return null;
		
	}
	public Question testLoadQuestion() {
    return null;
      
    }
	
	public void keepRecord() {
		
	}
}

