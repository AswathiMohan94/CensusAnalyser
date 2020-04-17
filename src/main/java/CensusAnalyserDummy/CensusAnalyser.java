package CensusAnalyserDummy;

import com.google.gson.Gson;

import java.util.*;

import static CensusAnalyserDummy.CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM;
import static java.util.stream.Collectors.toCollection;

public class CensusAnalyser {
    public enum Country { INDIA,US;}
    private Country country;
    Map<String, CensusDAO> censusStateMap = null;
    Map<String, IndiaCensusDAO> StateMap = null;
    Map<String, StateCodeDAO> stateCodeMap = null;
    Map<String, USCensusDAO> UScensusStateMap = null;

    public CensusAnalyser(Country country) {
        this.censusStateMap = new HashMap<>();
        this.UScensusStateMap = new HashMap<String, USCensusDAO>();
        this.StateMap = new HashMap<>();
        this.stateCodeMap = new HashMap<>();
        this.country = country;
      }


    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        try {
            censusStateMap = CensusAdapterFactory.getCensusData(country, csvFilePath);
            return censusStateMap.size();

        }
        catch (RuntimeException  e){
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE_OR_INVALID_FILE);

        }catch (CensusAnalyserException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CENSUS_FILE_PROBLEM);
        }
        Comparator<CensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        ArrayList censusDTOS =censusStateMap.values().stream().
                sorted(censusComparator).
                map(censusDAO -> censusDAO.getCensusDTO(country)).
                collect(toCollection(ArrayList::new));
        String sortedStateCensusJson = new Gson().toJson(censusDTOS);
        return sortedStateCensusJson; }

    public String SortingCodeWise() throws CensusAnalyserException {
        if (stateCodeMap == null || stateCodeMap.size() == 0)
            throw new CensusAnalyserException("No Census Data", CENSUS_FILE_PROBLEM);
        Comparator<Map.Entry<String, IndiaCensusDAO>> codeComparator = Comparator.comparing(code -> code.getValue().state);
        LinkedHashMap<String, StateCodeDAO> sortedByValue = this.CodeSort(codeComparator);
        Collection<StateCodeDAO> list1 = sortedByValue.values();
        String sortedStateCodeJson = new Gson().toJson(list1);
        return sortedStateCodeJson;
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CENSUS_FILE_PROBLEM);
        }
        Comparator<Map.Entry<String, IndiaCensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().population);
        LinkedHashMap<String, IndiaCensusDAO> sortedByValue = this.sortPopulation(censusComparator);
        ArrayList<IndiaCensusDAO> list = new ArrayList<>(sortedByValue.values());  //for getting mostpopulated state the order need to be reversed into descending order
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;}

    public String CensusData_Sorted_BasedOnPopulationDensity() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CENSUS_FILE_PROBLEM);
        }
        Comparator<Map.Entry<String, IndiaCensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().densityPerSqkm);
        LinkedHashMap<String, IndiaCensusDAO> sortedByValue = this.sortPopulationDensity(censusComparator);
        ArrayList<IndiaCensusDAO> list = new ArrayList<>(sortedByValue.values()); //for getting the state which having the hightest density of population, the order need to be reversed into descending order
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }
    public String CensusData_Sorted_AreaWise() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CENSUS_FILE_PROBLEM);
        }
        Comparator<Map.Entry<String, IndiaCensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().areaInSqKm);
        LinkedHashMap<String, IndiaCensusDAO> sortedByValue = this.sortArea(censusComparator);
        ArrayList<IndiaCensusDAO> list = new ArrayList<>(sortedByValue.values()); //for getting the state which having the hightest area, the order need to be reversed into descending order
        Collections.reverse(list);
        String sortedStateCensusJson = new Gson().toJson(list);
        return sortedStateCensusJson;
    }
     private <E extends StateCodeDAO> LinkedHashMap<String, StateCodeDAO> CodeSort(Comparator codeComparator) {
        Set<Map.Entry<String, StateCodeDAO>> entries = stateCodeMap.entrySet();
        List<Map.Entry<String, StateCodeDAO>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, codeComparator);
        LinkedHashMap<String, StateCodeDAO> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<String, StateCodeDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
    }

    private <E extends IndiaCensusDAO> LinkedHashMap<String, IndiaCensusDAO> sortPopulation(Comparator censusComparator) {
        Set<Map.Entry<String, IndiaCensusDAO>> entries = StateMap.entrySet();
        List<Map.Entry<String, IndiaCensusDAO>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, IndiaCensusDAO> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<String, IndiaCensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue()); }
        return sortedByValue; }

    private <E extends IndiaCensusDAO> LinkedHashMap<String, IndiaCensusDAO> sortPopulationDensity(Comparator censusComparator) {
        Set<Map.Entry<String, IndiaCensusDAO>> entries = StateMap.entrySet();
        List<Map.Entry<String, IndiaCensusDAO>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, IndiaCensusDAO> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<String, IndiaCensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue()); }
        return sortedByValue; }

    private <E extends IndiaCensusDAO> LinkedHashMap<String, IndiaCensusDAO> sortArea(Comparator censusComparator) {
        Set<Map.Entry<String, IndiaCensusDAO>> entries = StateMap.entrySet();
        List<Map.Entry<String, IndiaCensusDAO>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, IndiaCensusDAO> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<String, IndiaCensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue()); }
        return sortedByValue; }


}
