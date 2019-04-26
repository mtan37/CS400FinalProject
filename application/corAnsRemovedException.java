package application;


@SuppressWarnings("serial")
public class corAnsRemovedException extends Exception {
    @Override
    public String getMessage() {
        return "The correct choice is going to be removed. "
            + "Please decide/input a new correct answer or Cancel";
    }
   
}
