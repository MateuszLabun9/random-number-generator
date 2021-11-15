package customExceptions;

/**
 * The type Not number exception.
 */
public class notNumberException extends Exception{

    /**
     * The Details String, with message.
     */
    private String details;

    /**
     * Instantiates a new Not number exception.
     *
     * @param msg the msg passed when throwing exception
     */
    notNumberException(String msg){
        details="Inserted value is not a number";
    }
    public String toString(){
        return details;
    }
}
