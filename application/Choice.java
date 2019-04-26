package application;


/**
 * This Class represents Choice of a QUIZ
 * @author SHAO BIN DANIEL SHI HONG
 *
 */
public class Choice {
   
   
   private boolean isCorrect;  //  IndicatOR if this Choice is correct
   private String description = "";  //  The description of the choice
   
   /**
    * Choice Constructor
    * @param isCorrect indicates if the choice is correct 
    * @param description describes the choice
    */
   public Choice(boolean isCorrect, String description) {
     
        this.isCorrect = isCorrect;
        this.description = description;
       
   }
   /**
    * Getter of isCorrect
    * @return true if is correct, false other wise
    */
   public boolean getIsCorrect() {
     return isCorrect;
   }
   /**
    * Setter of isCorrect
    * @param isCorrect
    */
   public void setIsCorrect(boolean isCorrect) {
     this.isCorrect = isCorrect;
   }
   /**
    * Getter of the description
    * @return the description of the choice
    */
   public String getDescription() {
     return description;
   }
   /**
    * Setter of the description
    * @param des
    */
   public void setDescription(String des) {
     this.description = des;
   }
   
   
}
