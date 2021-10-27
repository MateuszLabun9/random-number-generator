package customExceptions;

public class notNumberException extends Exception{

    private String details;
    notNumberException(String msg){
        details="Inserted value is not a number";
    }
    public String toString(){
        return details;
    }
}
