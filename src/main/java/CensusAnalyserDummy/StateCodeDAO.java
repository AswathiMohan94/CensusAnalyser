package CensusAnalyserDummy;

public class StateCodeDAO {
    public static String StateCode;
    public String StateName;



    public StateCodeDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        this.StateName = indiaStateCodeCSV.StateName;
        //  this.TIN = IndiaStateCodeCSV.TIN;
        this.StateCode = indiaStateCodeCSV.StateCode;
    }
}

