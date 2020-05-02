package CensusAnalyserDummyTest;

import CensusAnalyserDummy.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.io.IOException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_TYPE = "./src/main/resources/IndiaStateCensusData.pdf";
    private static final String Incorrect_Delimiter = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_header = "./src/test/java/wrongfiles/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_PATH = "./src/main/resources/USCensusData.csv";

    CensusAnalyser censusAnalyser = null;

    @Before
    public void setUp() throws Exception {
        censusAnalyser = new CensusAnalyser();
    }
    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() throws IOException, CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_PATH);
            Assert.assertEquals(29, numOfRecords);
            numOfRecords = 0;
        } catch (CensusAnalyserException e) { }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() throws IOException, CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE_OR_INVALID_FILE, e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() throws IOException, CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.TYPE_EXTENSION_WRONG, e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithIncorrectCsvHeader_ShouldThrowException() throws IOException, CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_CSV_header);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE_OR_INVALID_FILE, e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException() throws IOException, CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, Incorrect_Delimiter);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE_OR_INVALID_FILE, e.type);
        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedState_ShouldReturnSortedResult() throws CensusAnalyserException, IOException, CSVBuilderException {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) { }
    }
    @Test
    public void givenStateCodeData_WhenSortedCodeWise_ShouldReturnSortedResult() throws IOException, CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_STATE_CODE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.SortingCodeWise();
            IndiaStateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AN", stateCodeCSV[0].StateCode);
        } catch (CensusAnalyserException e) { }
    }
    @Test
    public void givenIndianCensusData_WhenSortedPopulationWise_ShouldReturnSortedResult() throws CensusAnalyserException, IOException, CSVBuilderException {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Uttar Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedAreaWise_ShouldReturnSortedResult() throws CensusAnalyserException, IOException, CSVBuilderException {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.getAreaWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Rajasthan", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_SortedBasedOnPopulationDensity_ShouldReturnSortedResult() throws CensusAnalyserException, IOException, CSVBuilderException {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.CensusDataSortedPopulationDensityWise();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals("Bihar", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUSData_WhenSortedPopulationWise_ShouldReturnSortedResult() throws CensusAnalyserException, IOException, CSVBuilderException {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_PATH);
            String sortedData = censusAnalyser.USdataPopulationWiseSorted();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedData, IndiaCensusCSV[].class);
            Assert.assertEquals(3.7253956E7, censusCSV[0].population, 0.0);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUSData_WhenSortedPopulationDensityWise_ShouldReturnSortedResult() throws CensusAnalyserException, IOException, CSVBuilderException {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_PATH);
            String sortedData = censusAnalyser.USCensusDataSortedPopulationDensityWise();
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals(601723.0, censusCSV[0].populationDensity, 0.0);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUSData_WhenSortedAreaWise_ShouldReturnSortedResult() throws CensusAnalyserException, IOException, CSVBuilderException {
        try {
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_PATH);
            String sortedData = censusAnalyser.UsDataAreaWiseSorted();
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedData, USCensusCSV[].class);
            Assert.assertEquals(710231.0, censusCSV[0].totalArea, 0.0);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUSCensusData_ShouldReturnCorrectData() throws IOException, CSVBuilderException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            int usCensusDataCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_PATH);
            Assert.assertEquals(51, usCensusDataCount);
        } catch (CensusAnalyserException e) {
        }
    }
}
