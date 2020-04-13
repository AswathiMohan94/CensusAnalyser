package CensusAnalyserDummyTest;

import CensusAnalyserDummy.CensusAnalyser;
import CensusAnalyserDummy.CensusAnalyserException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_FILE_TYPE = "./src/main/resources/IndiaStateCensusData.pdf";
    private static final String Incorrect_Delimiter = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_header = "./src/test/java/wrongfiles/IndiaStateCensusData.csv";


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
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
    public void givenIndiaCensusData_WithWrongFileType_ShouldThrowException () {
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
        public void givenIndiaCensusData_WithWrongDelimiter_ShouldThrowException () {
            try {
                CensusAnalyser censusAnalyser = new CensusAnalyser();
                ExpectedException exceptionRule = ExpectedException.none();
                exceptionRule.expect(CensusAnalyserException.class);
                censusAnalyser.loadIndiaCensusData(Incorrect_Delimiter);
            } catch (CensusAnalyserException e) {
                Assert.assertEquals(CensusAnalyserException.ExceptionType.Incorrect_Delimiter_OR_wrong_Header, e.type);
            }
        }
    }
