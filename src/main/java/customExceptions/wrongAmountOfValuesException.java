package customExceptions;

/**
 * The type Wrong amount of values exception.
 */
public class wrongAmountOfValuesException extends Exception{
    /**
     * The Details String, with message.
     */
    private String details;

    /**
     * Instantiates a new Wrong amount of values exception.Contains specific error message.
     *
     * @param msg the msg passed when throwing exception
     */
    public wrongAmountOfValuesException(String msg){
        details="Amount of inserted values should be " + msg;
    }
    public String toString(){
        return details;
    }


}
