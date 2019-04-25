package application;
import java.util.ArrayList;

public class UserRecord {
	 ArrayList<String> choices;
	 private Integer numRqst;
	 private Integer numAns;
	 private Integer numCor;
	 private boolean currQuizSaved;//show if the questions in current quiz have been saved by the user
	 public UserRecord() {
		 choices = new ArrayList<String>();
		 numRqst = 0;
		 numAns = 0;
		 numCor = 0;
	     currQuizSaved = false;
	 }
	 
	 // Setters
	 protected void setNumRqst(Integer numRqst) {
		this.numRqst = numRqst;
	 }
	 
	 protected void setNumAns(Integer numAns) {
		this.numAns = numAns;
	 }
	 
	 protected void setNumCor(Integer numCor) {
		this.numCor = numCor;
	 }
	 
	 // Getters
	 protected Integer getNumRqst() {
		return numRqst;
	 }
	 
	 protected Integer getNumAns() {
		return numAns;
	 }
	 
	 protected Integer getNumCor() {
		return numCor;
	 }
	 
	 protected int getPercent() {
		return (int) ((int)(float)numAns / (float)numCor * 100);
	 }

	 protected boolean isCurrQuizSaved() {
    return currQuizSaved;
  }

	 protected void setCurrQuizSaved(boolean currQuizSaved) {
    this.currQuizSaved = currQuizSaved;
  }
}
