package CensusAnalyserDummyTest;

import CensusAnalyserDummy.CensusAnalyser;
import CensusAnalyserDummy.CensusAnalyserException;
import CensusAnalyserDummy.IndiaCensusCSV;
import CensusAnalyserDummy.IndiaStateCodeCSV;
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


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
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
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
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
            censusAnalyser.loadIndiaCensusData(WRONG_FILE_TYPE);
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
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_header);
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
            censusAnalyser.loadIndiaCensusData(Incorrect_Delimiter);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.Incorrect_Delimiter_OR_wrong_Header, e.type);
        }
    }

   /* @Test
    public void givenIndianCSV_ShouldReturnExactCount() throws CensusAnalyserException {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_PATH);
            Assert.assertEquals(37, numOfStateCode);
            numOfStateCode = 0;
        }catch (CensusAnalyserException e) {
        }

    }*/
      @Test
    public void givenIndianCensusData_WhenSortedState_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh", Census[0].state);
        System.out.println(sortedCensusData);

    }

    @Test
    public void givenIndianCensusData_WhenSortedPopulation_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.getPopulationWiseSortedCensusData();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        //   System.out.println(sortedCensusData);
        int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(29, numOfRecords);
    }

    @Test
    public void givenIndianCensusData_WhenSortedPopulationDensity_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
        String sortedCensusData = null;
        try {
            sortedCensusData = censusAnalyser.getDensityWiseSortedCensusData();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        IndiaCensusCSV[] Census = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
        //   System.out.println(sortedCensusData);
    }

    @Test
    public void givenStateCode_WhenSortedState_ShouldReturnSortedResult() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser();
        censusAnalyser.loadIndianStateCode(INDIA_STATE_CODE_PATH);
        String sortedStateCode = null;
        try {
            sortedStateCode = censusAnalyser.getStateCodeWiseSortedCensusData();
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
        IndiaStateCodeCSV[] Code = new Gson().fromJson(sortedStateCode, IndiaStateCodeCSV[].class);
        Assert.assertEquals("AD", Code[0].StateCode);
        Assert.assertEquals("wB", Code[36].StateCode);


    }
}













       //  ObjectMapper mapper = new ObjectMapper();

     //   mapper.writeValue(new File("c:\\country.json"), countryObj );


        //Assert.assertEquals("199812341", Census[0].population);
        //199812341->population of uttar pradesh


/*
        }*/

