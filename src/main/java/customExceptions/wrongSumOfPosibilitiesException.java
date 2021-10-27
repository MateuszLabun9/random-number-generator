package customExceptions;

public class wrongSumOfPosibilitiesException extends Throwable {
    public String details;
    public wrongSumOfPosibilitiesException(){
        details="Sum of Possibilities have to be equal 100 ";
    }
    public String toString(){
        return details;
    }
}
