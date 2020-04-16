package CensusAnalyserDummy;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {

    @CsvBindByName(column = "State Name", required = true)
    public String StateName;

    @CsvBindByName(column = "StateCode", required = true)
    public String StateCode;



    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "StateName='" + StateName + '\'' +
                ", StateCode='" + StateCode + '\'' +
                '}';
    }
}
