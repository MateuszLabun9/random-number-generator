package customExceptions;

/**
 * The type Not positive number exception.
 */
public class notPositiveNumberException extends Exception{
    /**
     * The Details String, with message.
     */
    public String details;

    /**
     * Instantiates a new Not positive number exception. Contains specific error message.
     */
    public notPositiveNumberException(){
        details="Inserted value is not a possitive number";
    }
    public String toString(){
        return details;
    }
}
