package CensusAnalyserDummy;

public class CensusAnalyserException extends Exception {


    public ExceptionType type;

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,NO_CENSUS_DATA,NO_Code_DATA,WRONG_FILE_TYPE,Incorrect_Delimiter_OR_wrong_Header,
        TYPE_EXTENSION_WRONG;
    }

//    ExceptionType type;

    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type=ExceptionType.valueOf(name);
    }
}
