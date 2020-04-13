package CensusAnalyserDummy;

public class CensusAnalyserException extends Exception {

    public ExceptionType type;


    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,NO_CENSUS_DATA,WRONG_FILE_TYPE,Incorrect_Delimiter_OR_wrong_Header,;
    }
     //ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}
