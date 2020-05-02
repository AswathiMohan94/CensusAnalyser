package CensusAnalyserDummy;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CensusAnalyser {


    public CensusAnalyser() {

    }
    public enum Country {INDIA, US;}

    private Country country;
    Map<String, CensusDAO> censusStateMap;
    Comparator<CensusDAO> censusComparator = null;

    public CensusAnalyser(Country country) {
        this.censusStateMap = new HashMap<>();
        this.country = country;
    }

    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException, IOException, CSVBuilderException {
        try {
            censusStateMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
            return censusStateMap.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE_OR_INVALID_FILE);
        } catch (CensusAnalyserException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name()); }
    }
    public String sort(Comparator<CensusDAO> censusComparator){
        ArrayList RunList = (ArrayList) censusStateMap.values().stream().sorted(censusComparator).collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(RunList);
        String sortedJsonData = new Gson().toJson(RunList);
        return sortedJsonData;
    }
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        censusComparator = Comparator.comparing((CensusDAO variable) -> variable.state);
        return this.sort(censusComparator);
    }
    public String SortingCodeWise() throws CensusAnalyserException {
        censusComparator = Comparator.comparing((CensusDAO variable) -> variable.stateCode);
        return this.sort(censusComparator);
    }
    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        censusComparator = Comparator.comparing((CensusDAO variable) -> variable.population);
        return this.sort(censusComparator);
    }
    public String CensusDataSortedPopulationDensityWise() throws CensusAnalyserException {
        censusComparator = Comparator.comparing((CensusDAO variable) -> variable.populationDensity);
        return this.sort(censusComparator);
    }
    public String getAreaWiseSortedCensusData() throws CensusAnalyserException {
        censusComparator = Comparator.comparing((CensusDAO variable) -> variable.totalArea);
        return this.sort(censusComparator);
    }
    public String USdataPopulationWiseSorted() throws CensusAnalyserException {
        censusComparator = Comparator.comparing((CensusDAO variable) -> variable.population);
        return this.sort(censusComparator);
    }
    public String USCensusDataSortedPopulationDensityWise() throws CensusAnalyserException {
        censusComparator = Comparator.comparing((CensusDAO variable) -> variable.populationDensityUS);
        return this.sort(censusComparator);
    }

    public String UsDataAreaWiseSorted() throws CensusAnalyserException {
        censusComparator = Comparator.comparing((CensusDAO variable) -> variable.totalArea);
        return this.sort(censusComparator);
    }
}