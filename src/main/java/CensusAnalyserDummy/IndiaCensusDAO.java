package CensusAnalyserDummy;


public class IndiaCensusDAO {
    public String state;
    private int areaInSqKm;
    private int densityPerSqkm;
    public int population;


    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        this.state = indiaCensusCSV.state;
        this.areaInSqKm = indiaCensusCSV.areaInSqKm;
        this.densityPerSqkm = indiaCensusCSV.densityPerSqKm;
        this.population = indiaCensusCSV.population;
    }
}