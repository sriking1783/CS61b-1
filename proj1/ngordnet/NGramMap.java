package ngordnet;
import java.util.TreeMap;
import java.util.Collection;
import edu.princeton.cs.introcs.In;


public class NGramMap {

    /** Keeps track of count per year, by word */
    private TreeMap<String, TimeSeries<Integer>> wordsTimeMap;
    /** Keeps track of counts per word, by year */
    private TreeMap<Integer, YearlyRecord> wordsByYearMap;
    /** Keeps track of count by year */
    private TimeSeries<Long> countsMap;

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordsTimeMap = new TreeMap<String, TimeSeries<Integer>>();
        wordsByYearMap = new TreeMap<Integer, YearlyRecord>();
        countsMap = new TimeSeries<Long>();

        In words = new In(wordsFilename);
        In counts = new In(countsFilename);
        String eachWordLine = words.readLine();
        while (eachWordLine != null) {
            representWords(eachWordLine);
            eachWordLine = words.readLine();
        }
        String eachCountLine = counts.readLine();
        while (eachCountLine != null) {
            representsCounts(eachCountLine);
            eachCountLine = counts.readLine();
        }
    }
    
    /** Parses through wordsFilename and builds the wordsMap */
    private void representWords(String line) {
        String[] pieces = line.split("\t");
        String word = pieces[0];
        Integer year = Integer.parseInt(pieces[1]);
        Integer count = Integer.parseInt(pieces[2]);
        if (!wordsTimeMap.containsKey(word)) {
            TimeSeries<Integer> countInYear = new TimeSeries<Integer>();
            wordsTimeMap.put(word, countInYear);
        }
        wordsTimeMap.get(word).put(year, count);
        if (!wordsByYearMap.containsKey(year)) {
            YearlyRecord wordsCounts = new YearlyRecord();
            wordsByYearMap.put(year, wordsCounts);
        }
        wordsByYearMap.get(year).put(word, count);
    }

    /** Parses through countsFilename and builts the countsMap */
    private void representsCounts(String line) {
        String[] pieces = line.split(",");
        Integer year = Integer.parseInt(pieces[0]);
        Long count = Long.parseLong(pieces[1]);
        countsMap.put(year, count);
    }

    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {
        if (wordsTimeMap.containsKey(word)) {
            // if (wordsTimeMap.get(word).containsKey(year)) {
                return wordsTimeMap.get(word).get(year);
            // }
        }
        return 0;
    }

    /** Returns a defensive copy of the YearlyRecord of WORD. */
    public YearlyRecord getRecord(int year) {
        return wordsByYearMap.get(year);
    }

    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return countsMap;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> betweenYears;
        betweenYears = new TimeSeries<Integer>(wordsTimeMap.get(word), startYear, endYear);
        return betweenYears;
    }

    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        return wordsTimeMap.get(word);
    }

    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> years = countHistory(word, startYear, endYear);
        TimeSeries<Long> allYears = totalCountHistory();
        TimeSeries<Double> weightedYears = years.dividedBy(allYears);
        return weightedYears;
    }

    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        TimeSeries<Integer> years = countHistory(word);
        TimeSeries<Long> allYears = totalCountHistory();
        TimeSeries<Double> weightedYears = years.dividedBy(allYears);
        return weightedYears;
    }

    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
                              int startYear, int endYear) {
        TimeSeries<Double> summedFrequency = new  TimeSeries<Double>();
        for (String word : words) {
            summedFrequency = summedFrequency.plus(weightHistory(word, startYear, endYear));
        }
        return summedFrequency;
    }

    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        TimeSeries<Double> summedFrequency = new  TimeSeries<Double>();
        for (String word : words) {
            summedFrequency = summedFrequency.plus(weightHistory(word));
        }
        return summedFrequency;
    }

    /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
      * by YRP. */
    // public TimeSeries<Double> processedHistory(int startYear, int endYear,
    //                                            YearlyRecordProcessor yrp) {
    // return null;
    // }

    // /** Provides processed history of all words ever as processed by YRP. */
    // public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
    // return null;
    // }
}
