package CensusAnalyserDummy;

public class CensusDAO {
    public static String state;
    public String stateCode;
    public double totalArea;
    public double populationDensity;
    public int population;



    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        population = indiaCensusCSV.population;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        totalArea = indiaCensusCSV.areaInSqKm;
    }
    public CensusDAO(USCensusCSV CensusCSV) {
        state = CensusCSV.state;
        stateCode = CensusCSV.stateId;
        population = CensusCSV.population;
        populationDensity = CensusCSV.populationDensity;
    }
    public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state, population ,(int)populationDensity, (int)totalArea);
    }
}
