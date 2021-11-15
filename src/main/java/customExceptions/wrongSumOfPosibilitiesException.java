package customExceptions;

/**
 * The type Wrong sum of posibilities exception.
 */
public class wrongSumOfPosibilitiesException extends Throwable {
    /**
     * The Details String, with message.
     */
    public String details;

    /**
     * Instantiates a new Wrong sum of posibilities exception. Contains specific error message.
     */
    public wrongSumOfPosibilitiesException(){
        details="Sum of Possibilities have to be equal 100 ";
    }
    public String toString(){
        return details;
    }
}
