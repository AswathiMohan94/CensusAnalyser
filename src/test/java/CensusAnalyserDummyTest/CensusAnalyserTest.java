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
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_PATH);
            Assert.assertEquals(29, numOfRecords);
            numOfRecords = 0;
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.WRONG_FILE_TYPE, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithIncorrectCSVheader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_header);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.Incorrect_Delimiter_OR_wrong_Header, e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,Incorrect_Delimiter);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.Incorrect_Delimiter_OR_wrong_Header, e.type);
        }
    }

  /*  @Test
    public void givenIndianStateCodeData_ShouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_PATH);
            Assert.assertEquals(29, numOfStateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }
*/
    @Test
    public void givenIndianCensusData_WhenSortedState_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        System.out.println(sortedCensusData);

        IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh", Census[0].state);


    }

    @Test
    public void givenStateCodeData_WhenSortedCodeWise_ShouldReturnSortedResult()  {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_STATE_CODE_PATH,INDIA_CENSUS_CSV_FILE_PATH);
        /*String sortedCodeData = null;
        try {
            sortedCodeData = censusAnalyser.SortingCodeWise();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        System.out.println(sortedCodeData);

        IndiaStateCodeCSV[] Code = new Gson().fromJson(sortedCodeData, IndiaStateCodeCSV[].class);
        Assert.assertEquals("AD", Code[0].StateCode);
        Assert.assertEquals("WB", Code[36].StateCode);
    */
            String sortedData = censusAnalyser.SortingCodeWise();
            IndiaStateCodeCSV[] stateCodeCSVS = new Gson().fromJson(sortedData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("AN", stateCodeCSVS[0].StateCode);
        } catch (CensusAnalyserException e) {
        }

    }
    @Test
    public void givenIndianCensusData_WhenSortedPopulationWise_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        System.out.println(sortedCensusData);
        int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(29, numOfRecords);
    }

    @Test
    public void givenIndianCensusData_SortedBasedOnPolpulationDensity_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.CensusData_Sorted_BasedOnPopulationDensity();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        System.out.println(sortedCensusData);
    }

    @Test
    public void givenIndianCensusData_AreaWiseSorted_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.CensusData_Sorted_AreaWise();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        System.out.println(sortedCensusData);
    }

    @Test
    public void givenUSCensusData_ShouldReturnCorrectData() {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                int usCensusDataCount = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_CSV_PATH);
                Assert.assertEquals(51, usCensusDataCount);
            } catch (CensusAnalyserException e) {
            }
        }
    }

