package CensusAnalyserDummy;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {


    @CsvBindByName(column = "State", required = true)
    public static String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "stateId", required = true)
    public String stateId;

    @CsvBindByName(column = "Total area", required = true)
    public double totalArea;

    @CsvBindByName(column = "PopulationDensity", required = true)
    public double populationDensity;


}
