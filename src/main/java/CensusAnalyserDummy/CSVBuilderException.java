package CensusAnalyserDummy;

public class CSVBuilderException extends Exception {
    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,NO_Code_DATA;
    }

    ExceptionType type;
    public CSVBuilderException(String message, ExceptionType unableToParse){
        super(message);
        this.type = type;

    }

}
