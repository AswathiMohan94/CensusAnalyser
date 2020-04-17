package CensusAnalyserDummyTest;

import CensusAnalyserDummy.*;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_TYPE = "./src/main/resources/IndiaStateCensusData.pdf";
    private static final String Incorrect_Delimiter = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_header = "./src/test/java/wrongfiles/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_PATH = "./src/main/resources/USCensusData.csv";


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_PATH);
            Assert.assertEquals(29, numOfRecords);
            numOfRecords = 0;
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
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
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE_OR_INVALID_FILE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectCSVheader_ShouldThrowException() {
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
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException() {
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
    public void givenIndianCensusData_WhenSortedState_ShouldReturnSortedResult() throws CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", Census[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenStateCodeData_WhenSortedCodeWise_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_STATE_CODE_PATH, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedData = censusAnalyser.SortingCodeWise();
            IndiaStateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AN", stateCodeCSV[0].StateCode);
        } catch (CensusAnalyserException e) {
        }

    }

    @Test
    public void givenIndianCensusData_WhenSortedPopulationWise_ShouldReturnSortedResult() throws CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            CensusAnalyser censusAnalyser1 = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser1.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_PATH);
            String sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
            String sortedUSCensusData = censusAnalyser1.getPopulationWiseSortedCensusData();
            IndiaStateCodeCSV[] stateCodeCSV = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
            USCensusCSV[] USCodeCSV = new Gson().fromJson(sortedUSCensusData, USCensusCSV[].class);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndia_US_CensusData_SortedBasedOnPolpulationDensity_ShouldReturnSortedResult() throws CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            CensusAnalyser censusAnalyser1 = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_PATH);
            String sortedCensusDataIndia = censusAnalyser.CensusData_Sorted_BasedOnPopulationDensity();
            String sortedCensusDataUS = censusAnalyser1.CensusData_Sorted_BasedOnPopulationDensity();
            IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusDataIndia, IndiaCensusCSV[].class);
            USCensusCSV[] Census1 = new Gson().fromJson(sortedCensusDataUS, USCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndia_US_CensusData_AreaWiseSorted_ShouldReturnSortedResult() throws CensusAnalyserException {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
            CensusAnalyser censusAnalyser1 = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_PATH);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.CensusData_Sorted_AreaWise();
            String sortedUSCensusData = censusAnalyser1.CensusData_Sorted_AreaWise();
            USCensusCSV[] Census1 = new Gson().fromJson(sortedUSCensusData, USCensusCSV[].class);
            IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectData() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            int usCensusDataCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US, US_CENSUS_CSV_PATH);
            Assert.assertEquals(51, usCensusDataCount);
        } catch (CensusAnalyserException e) {
        }
    }
}

