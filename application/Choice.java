package application;
/***
 * This Class represents Choice of a QUIZ
 * @author SHAO BIN DANIEL SHI HONG
 *
 */
public class Choice {
   
   
   private boolean isCorrect = false;  //  answer indicates if this Choice is correct
   private String description = "";  //  The description of the choice
   
   /**
    * Choice Constructor
    * @param isCorrect 
    * @param description
    */
   public Choice(boolean isCorrect, String description) {
     
        this.isCorrect = isCorrect;
        this.description = description;
       
   }
   /**
    * 
    * @return
    */
   public boolean getIsCorrect() {
     return isCorrect;
   }
   public void setIsCorrect(boolean isCorrect) {
     this.isCorrect = isCorrect;
   }
   public String getDescription() {
     return description;
   }
   public void setDescription(String des) {
     this.description = des;
   }
   
}
