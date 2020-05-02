package CensusAnalyserDummy;

public class CensusDAO {
    public static String state;
    public String stateCode;
    public double totalArea;
    public double populationDensity;
    public int population;
    public double populationDensityUS;



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
        populationDensityUS = CensusCSV.populationDensity;
    }

    public CensusDAO(IndiaStateCodeCSV censusCSV) {
    }
}/*
    public IndiaCensusCSV getIndiaCensusCSV(){
        return new IndiaCensusCSV(state, population ,(int)populationDensity, (int)totalArea);
    }

    public IndiaCensusCSV getCensusDTO(CensusAnalyser.Country country) {
        if (country.equals(CensusAnalyser.Country.INDIA))
        {
            return  new USCensusCSV(state,stateCode,population,populationDensity,totalArea);
        }
        return  new IndiaCensusCSV(state,population,(int)populationDensity,(int)totalArea);
    }
}
*/