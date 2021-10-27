package customExceptions;

public class wrongIntervalException  extends Exception{
    public String details;
    public wrongIntervalException(){
        details="Wrong interval, please insert wider interval";
    }
    public String toString(){
        return details;
    }

}
