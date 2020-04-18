package CensusAnalyserDummy;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

public class CensusAnalyser {
    Map<String, IndiaCensusDAO> censusStateMap = null;
    Map<String, StateCodeDAO> stateCodeMap = null;

    public CensusAnalyser() {
        this.censusStateMap = new HashMap<>();
        this.stateCodeMap = new HashMap<>();

    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext()) {
                IndiaCensusDAO indianCensusDAO = new IndiaCensusDAO(csvFileIterator.next());
                this.censusStateMap.put(indianCensusDAO.state, indianCensusDAO);
            }
            return this.censusStateMap.size();

        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.Incorrect_Delimiter_OR_wrong_Header);
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

    }

    public int loadIndianStateCode(String StateCSVFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(StateCSVFilePath))) {
            ICSVBuilder CSVBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCSVIterator = CSVBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (stateCSVIterator.hasNext()) {
                StateCodeDAO stateCodeDAO = new StateCodeDAO(stateCSVIterator.next());
                this.stateCodeMap.put(StateCodeDAO.StateCode, stateCodeDAO);
            }
            return this.stateCodeMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusStateMap == null || censusStateMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<Map.Entry<String, IndiaCensusDAO>> censusComparator = Comparator.comparing(census -> census.getValue().state);
        LinkedHashMap<String, IndiaCensusDAO> sortedByValue = this.sort(censusComparator);
        Collection<IndiaCensusDAO> list1 = sortedByValue.values();
        String sortedStateCensusJson = new Gson().toJson(list1);
        return sortedStateCensusJson;
    }

    public String SortingCodeWise() throws CensusAnalyserException {
        if (stateCodeMap == null || stateCodeMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<Map.Entry<String, IndiaCensusDAO>> codeComparator = Comparator.comparing(code -> code.getValue().state);
        LinkedHashMap<String, StateCodeDAO> sortedByValue = this.CodeSort(codeComparator);
        Collection<StateCodeDAO> list1 = sortedByValue.values();
        String sortedStateCodeJson = new Gson().toJson(list1);
        return sortedStateCodeJson;
    }


    private <E extends IndiaCensusDAO> LinkedHashMap<String, IndiaCensusDAO> sort(Comparator censusComparator) {
        Set<Map.Entry<String, IndiaCensusDAO>> entries = censusStateMap.entrySet();
        List<Map.Entry<String, IndiaCensusDAO>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, censusComparator);
        LinkedHashMap<String, IndiaCensusDAO> sortedByValue = new LinkedHashMap<>(listOfEntries.size());
        for (Map.Entry<String, IndiaCensusDAO> entry : listOfEntries) {
            sortedByValue.put(entry.getKey(), entry.getValue());
        }
        return sortedByValue;
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
}
