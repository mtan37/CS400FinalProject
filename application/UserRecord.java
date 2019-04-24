package application;
import java.util.ArrayList;

public class UserRecord {
	 ArrayList<String> choices = new ArrayList<String>();
	 private Integer numQsRqst;
	 private Integer numQsAns;
	 private Integer numQsCor;
	 
	 protected void setQs( int qsRqst, int qsAns, int qsCor ) {
		 numQsRqst = qsRqst;
		 numQsAns = qsAns;
		 numQsCor = qsCor;
	 }
}
