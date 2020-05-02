package CensusAnalyserDummy;

public class CensusAnalyserException extends Exception {


    public ExceptionType type;

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,WRONG_FILE_TYPE_OR_INVALID_FILE,Incorrect_Delimiter_OR_wrong_Header,
        TYPE_EXTENSION_WRONG,INVALID_COUNTRY,UNABLE_TO_PARSE,STATE_CODE_FILE_INVALID_EXTENSION;
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
