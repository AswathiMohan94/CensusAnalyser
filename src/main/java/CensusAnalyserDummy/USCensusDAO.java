package CensusAnalyserDummy;

public class USCensusDAO {
    public static String state;
    public String stateId;
    public double totalArea;
    public double populationDensity;
    public int population;


    public USCensusDAO(USCensusCSV usCensusCSV) {
        this.state = USCensusCSV.state;
        this.population = usCensusCSV.population;
        this.populationDensity = usCensusCSV.populationDensity;
        this.stateId = usCensusCSV.stateId;
        this.totalArea = usCensusCSV.totalArea;
    }
}
