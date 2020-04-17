package CensusAnalyserDummy;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public abstract class CensusAdapter {
    public abstract Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException;
        private <E> Map<String, CensusDAO> loadCensusData(Class<E> censusCSVClass, String csvFilePath) throws CensusAnalyserException {
            Map<String, CensusDAO> censusStateMap = new HashMap<>();
            if (!csvFilePath.contains(".csv")) {
                throw new CensusAnalyserException("Enter proper file extention", CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG);
            }

            try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
                ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
                Iterator<E> csvIterator = csvBuilder.getCSVFileIterator(reader, censusCSVClass);
                Iterable<E> csvIterable = () -> csvIterator;
                if (censusCSVClass.getName().equals("CensusAnalyser.IndiaCensusCSV")) {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(IndiaCensusCSV.class::cast)
                            .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                } else if (censusCSVClass.getName().equals("CensusAnalyser.USCensusCSV")) {
                    StreamSupport.stream(csvIterable.spliterator(), false)
                            .map(USCensusCSV.class::cast)
                            .forEach(censusCSV -> censusStateMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                }

                return censusStateMap;
            } catch (IOException e) {
                throw new CensusAnalyserException(e.getMessage(),
                        CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);

            } catch (CSVBuilderException e) {
                throw new CensusAnalyserException(e.getMessage(), e.type.name());
            }

        }
}

