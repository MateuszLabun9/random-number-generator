package customExceptions;

/**
 * The type Wrong interval exception.
 */
public class wrongIntervalException  extends Exception{
    /**
     * The Details String, with message.
     */
    public String details;

    /**
     * Instantiates a new Wrong interval exception. Contains specific error message.
     */
    public wrongIntervalException(){
        details="Wrong interval, please insert wider interval";
    }
    public String toString(){
        return details;
    }

}
