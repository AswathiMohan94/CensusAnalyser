package CensusAnalyserDummy;

import java.io.IOException;
import java.util.Map;

public class CensusAdapterFactory {
    public static Map getCensusData(CensusAnalyser.Country country, String ...csvFilepath) throws CensusAnalyserException, CSVBuilderException, IOException {

        if (country.equals(CensusAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilepath);
        if ( country.equals(CensusAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilepath);
        throw new CensusAnalyserException("unknown Country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}



