package application;

public class QuizGenerator {
	FileHandler fileHandler;
	UserRecord userRecord;
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
