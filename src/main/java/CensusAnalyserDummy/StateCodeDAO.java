package CensusAnalyserDummy;

public class StateCodeDAO {
    public String StateName;
    public String StateCode;



    public StateCodeDAO(IndiaStateCodeCSV indiaStateCodeCSV) {
        this.StateName = indiaStateCodeCSV.StateName;
        //  this.TIN = IndiaStateCodeCSV.TIN;
        this.StateCode = indiaStateCodeCSV.StateCode;
    }
}

