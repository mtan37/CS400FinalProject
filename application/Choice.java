package application;

/**
 * This Class represents a choice for a quiz
 * 
 * @author Shao Bin Daniel Shi Hong
 */
public class Choice {
   
   
   private boolean isCorrect;  //  Indicates whether this Choice is correct
   private String description;  //  The choice description/text
   
   /**
    * Choice Constructor initializes the choice description and its truth value
    * 
    * @param isCorrect indicates if the choice is correct 
    * @param description: describes the choice
    */
   public Choice(boolean isCorrect, String description) {
     
        this.isCorrect = isCorrect;
        this.description = description;
       
   }
   
   /**
    * Getter of isCorrect; returns boolean indicating whether this choice is true
    * 
    * @return true if is correct, false otherwise
    */
   public boolean getIsCorrect() {
     return isCorrect;
   }
   
   /**
    * Setter of isCorrect; sets whether choice is true or false
    * 
    * @param isCorrect: truth value of this choice
    */
   public void setIsCorrect(boolean isCorrect) {
     this.isCorrect = isCorrect;
   }
   
   /**
    * Getter of the choice description; returns the actual text of the choice
    * 
    * @return the description of the choice
    */
   public String getDescription() {
     return description;
   }
   
   /**
    * Setter of the description; sets the text describing the choice
    * 
    * @param des: the text describing the choice
    */
   public void setDescription(String des) {
     this.description = des;
   }
}
