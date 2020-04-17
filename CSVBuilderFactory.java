package CensusAnalyserDummy;

public class CSVBuilderFactory {
    public static ICSVBuilder createCSVBuilder(){
        return new openCSVBuilder();
    }
}