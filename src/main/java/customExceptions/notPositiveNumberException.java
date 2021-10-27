package customExceptions;

public class notPositiveNumberException extends Exception{
    public String details;
    public notPositiveNumberException(){
        details="Inserted value is not a possitive number";
    }
    public String toString(){
        return details;
    }
}
