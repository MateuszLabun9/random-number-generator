package customExceptions;

public class wrongAmountOfValuesException extends Exception{
    private String details;
    public wrongAmountOfValuesException(String msg){
        details="Amount of inserted values should be " + msg;
    }
    public String toString(){
        return details;
    }


}
