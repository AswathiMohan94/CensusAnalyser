package CensusAnalyserDummy;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {


    @Override
    public  Map loadCensusData( String... csvFilePath) throws CSVBuilderException, CensusAnalyserException {
        Map<String, CensusDAO> censusMap = super.loadCensusData(IndiaCensusCSV.class, csvFilePath[0]);
        if(csvFilePath.length>1)
            this.loadIndianCensusStateCode(censusMap, csvFilePath[1]);
        return censusMap;
    }
    private Map loadIndianCensusStateCode(Map<String,CensusDAO> censusMap,String csvFilePath) throws CensusAnalyserException, CSVBuilderException {
        if(!csvFilePath.contains(".csv")){
            throw new CensusAnalyserException("FILE_EXTENSION_ERROR",CensusAnalyserException.ExceptionType.STATE_CODE_FILE_INVALID_EXTENSION);
        }
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> csvIterable = () -> csvIterator;
            StreamSupport.stream(csvIterable.spliterator(), false)
                    .map(IndiaStateCodeCSV.class::cast)
                    .forEach(censusCSV -> censusMap.put(censusCSV.StateCode, new CensusDAO(censusCSV)));

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains(" CSV header error"))
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            else
                throw new CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new
                    CensusAnalyserException(e.getMessage(), CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }

        return censusMap;
    }
}