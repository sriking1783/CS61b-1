package ngordnet;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.QuickChart;
import com.xeiam.xchart.SwingWrapper;
import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.ChartBuilder;
import java.util.List;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Set;

/** Utility class for generating plots. */
public class Plotter {
    /** Creates a plot of the TimeSeries TS. Labels the graph with the
      * given TITLE, XLABEL, YLABEL, and LEGEND. */
    public static void plotTS(TimeSeries<? extends Number> ts, String title, 
                              String xlabel, String ylabel, String legend) {
        Collection<Number> allYears = ts.years();
        Collection<Number> allCounts = ts.data();

        // Create Chart
        Chart chart = QuickChart.getChart(title, ylabel, xlabel, legend, allYears, allCounts);
     
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    /** Creates a plot of the absolute word counts for WORD from STARTYEAR
      * to ENDYEAR, using NGM as a data source. */
    public static void plotCountHistory(NGramMap ngm, String word, 
                                      int startYear, int endYear) {
        TimeSeries<Integer> history = ngm.countHistory(word, startYear, endYear);
        plotTS(history, "Popularity", "year", "count", word);
    }

    /** Creates a plot of the normalized weight counts for WORD from STARTYEAR
      * to ENDYEAR, using NGM as a data source. */
    public static void plotWeightHistory(NGramMap ngm, String word, 
                                       int startYear, int endYear) {
        TimeSeries<Double> weightedHistory = ngm.weightHistory(word, startYear, endYear);
        plotTS(weightedHistory, "Popularity", "year", "weight", word);
    }

    /** Creates a plot of the processed history from STARTYEAR to ENDYEAR, using
      * NGM as a data source, and the YRP as a yearly record processor. */
    public static void plotProcessedHistory(NGramMap ngm, int startYear, int endYear,
                                            YearlyRecordProcessor yrp) {

    }

    /** Creates a plot of the total normalized count of WN.hyponyms(CATEGORYLABEL)
      * from STARTYEAR to ENDYEAR using NGM and WN as data sources. */
    public static void plotCategoryWeights(NGramMap ngm, WordNet wn, String categoryLabel,
                                            int startYear, int endYear) {
        Set<String> allWords = wn.hyponyms(categoryLabel);        
        TimeSeries<Double> sumOfWeightedHistory = ngm.summedWeightHistory(allWords, startYear, endYear);
        plotTS(sumOfWeightedHistory, "Popularity", "year", "weight", categoryLabel);
    }

    /** Creates overlaid category weight plots for each category label in CATEGORYLABELS
      * from STARTYEAR to ENDYEAR using NGM and WN as data sources. */
    public static void plotCategoryWeights(NGramMap ngm, WordNet wn, String[] categoryLabels,
                                            int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(800).height(600).xAxisTitle("years").yAxisTitle("data").build();

        for (String category : categoryLabels) {
            Set<String> words = wn.hyponyms(category);        
            TimeSeries<Double> group = ngm.summedWeightHistory(words, startYear, endYear);
            chart.addSeries(category, group.years(), group.data());
        }
        new SwingWrapper(chart).displayChart();
    }

    /** Makes a plot showing overlaid individual normalized count for every word in WORDS
      * from STARTYEAR to ENDYEAR using NGM as a data source. */
    public static void plotAllWords(NGramMap ngm, String[] words, int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(800).height(600).xAxisTitle("years").yAxisTitle("data").build();

        for (String word : words) {
            System.out.println("e");
            TimeSeries<Double> group = ngm.weightHistory(word, startYear, endYear);
            System.out.println("d");
            // System.out.println(group.years());
            // System.out.println(group.data());
            chart.addSeries(word, group.years(), group.data());
            System.out.println("e");
        }
        System.out.println("f");
        new SwingWrapper(chart).displayChart();
        System.out.println("g");

        // Chart chart = new ChartBuilder().width(800).height(600).xAxisTitle("years").yAxisTitle("data").build();

        // for (String word : words) {
        //     TimeSeries<Double> group = ngm.weightHistory(word, startYear, endYear);
        //     chart.addSeries(word, group.years(), group.data());
        // }
        // new SwingWrapper(chart).displayChart();
    }

    /** Returns the numbers from max to 1, inclusive in decreasing order. 
      * Private, so you don't have to implement if you don't want to. */
    private static Collection<Number> downRange(int max) {
        ArrayList<Number> rankings = new ArrayList<Number>();
        for (int i = max; i >= 1; i -= 1) {
            rankings.add(i);
        }
        return rankings;
    }

    /** Plots the count (or weight) of every word against the rank of every word on a
      * log-log plot. Uses data from YEAR, using NGM as a data source. */
    public static void plotZipfsLaw(NGramMap ngm, int year) {

    }
} 
