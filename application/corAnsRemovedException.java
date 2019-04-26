package application;


//@SuppressWarnings("serial")
public class corAnsRemovedException extends Exception {
    /**
   * 
   */
  private static final long serialVersionUID = 1L;

    @Override
    public String getMessage() {
        return "The correct choice is going to be removed. "
            + "Please decide/input a new correct answer or Cancel";
    }
   
}
