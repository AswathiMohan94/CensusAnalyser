package CensusAnalyserDummy;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;
public class CensusAnalyser {
    List<IndiaCensusDAO> censusList = null;
    List<StateCodeDAO> codeList = null;


    public CensusAnalyser() {
        this.censusList = new ArrayList<IndiaCensusDAO>();
        this.codeList = new ArrayList<StateCodeDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaCensusCSV> csvfileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            if (censusList != null) {
                this.censusList = new ArrayList<IndiaCensusDAO>();
            }
            while (csvfileIterator.hasNext()) {
                this.censusList.add(new IndiaCensusDAO(csvfileIterator.next()));
            }
            return censusList.size();
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        } catch (NoSuchFileException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.Incorrect_Delimiter_OR_wrong_Header);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }


    public int loadIndianStateCode(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> getCSVFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            if (codeList != null) {
                this.codeList = new ArrayList<StateCodeDAO>();
            }
            while (getCSVFileIterator.hasNext()) {
                this.codeList.add(new StateCodeDAO(getCSVFileIterator.next()));
            }
           return codeList.size();
            //return this.getCount(getCSVFileIterator);

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> Iterator) {
        Iterable<E> csvIterable = () -> Iterator;
        int numOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEntries;
    }


    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("no census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
        return sortedStateCensusJson;

    }


    public String getStateCodeWiseSortedCensusData() throws CensusAnalyserException {
        if (codeList == null || codeList.size() == 0) {
            throw new CensusAnalyserException("no code data", CensusAnalyserException.ExceptionType.NO_Code_DATA);
         }
        Comparator<StateCodeDAO> codeComparator = Comparator.comparing(code -> code.StateCode);
        this.SortCode(codeComparator);
        String sortedStateCodeJson = new Gson().toJson(censusList);
        return sortedStateCodeJson;

    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("no census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.population);
        this.sortPopulation(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusList);
               return sortedStateCensusJson;

    }

    public String getDensityWiseSortedCensusData() throws CensusAnalyserException {
        if (censusList == null || censusList.size() == 0) {
            throw new CensusAnalyserException("no census data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.densityPerSqkm);
        this.sortDensity(censusComparator);
        String sortedStateCensusJson= new Gson().toJson(censusList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusDAO> CensusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (CensusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);

                }
            }
        }
    }
    private void SortCode(Comparator<StateCodeDAO> codeComparator) {
        for (int i = 0; i < codeList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                StateCodeDAO code1 = codeList.get(j);
                StateCodeDAO code2 = codeList.get(j + 1);
                if (codeComparator.compare(code1, code2) > 0) {
                    codeList.set(j, code2);
                    codeList.set(j + 1, code1);

                }
            }
        }
    }
    private void sortPopulation(Comparator<IndiaCensusDAO> CensusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (CensusComparator.compare(census1, census2) < 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);

                }
            }
        }
    }

    private void sortDensity(Comparator<IndiaCensusDAO> CensusComparator) {
        for (int i = 0; i < censusList.size() - 1; i++) {
            for (int j = 0; j < censusList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusList.get(j);
                IndiaCensusDAO census2 = censusList.get(j + 1);
                if (CensusComparator.compare(census1, census2) > 0) {
                    censusList.set(j, census2);
                    censusList.set(j + 1, census1);

                }
            }
        }
    }

    //bubble sorting done sort the state codes
    private void sortCode(Comparator<StateCodeDAO> CodeComparator) {
        for (int i = 0; i < codeList.size() - 1; i++) {
            for (int j = 0; j < codeList.size() - i - 1; j++) {
                StateCodeDAO code1 = codeList.get(j);
                StateCodeDAO code2 = codeList.get(j + 1);
                if (CodeComparator.compare(code1, code2) > 0) {
                    codeList.set(j, code2);
                    codeList.set(j + 1, code1);
                }
            }
        }
    }
}
