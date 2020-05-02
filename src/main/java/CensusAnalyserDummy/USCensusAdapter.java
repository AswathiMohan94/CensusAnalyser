package CensusAnalyserDummy;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter{

    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        return super.loadCensusData(USCensusCSV.class,csvFilePath[0]);
    }
}
