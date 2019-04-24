package application;
import java.util.ArrayList;

public class UserRecord {
	 ArrayList<String> choices;
	 private Integer numRqst;
	 private Integer numAns;
	 private Integer numCor;
	 
	 public UserRecord() {
		 choices = new ArrayList();
		 numRqst = 0;
		 numAns = 0;
		 numCor = 0;
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
	 
	 protected float getPercent() {
		return (float) numAns / (float) numCor;
	 }
}
