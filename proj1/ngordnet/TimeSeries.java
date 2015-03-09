package ngordnet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.NavigableSet;
import java.util.Collection;
import java.util.ArrayList;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {    
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        // Do nothing.
    }

    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have 
      * to implement it if you don't want to. */
    private NavigableSet<Integer> validYears(int startYear, int endYear) {
        NavigableSet<Integer> valid = new TreeSet<Integer>();
        for (int i = startYear; i <= endYear; i++) {
            if (this.years().contains(i)) {
                valid.add(i);
            }
        }
        return valid;
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {
        for (Integer year : ts.keySet()) {
            if (year >= startYear && year <= endYear) {
                this.put(year, ts.get(year));
            }
        }
    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {
        for (Integer i : ts.keySet()) {
            this.put(i, ts.get(i));
        }
    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> quotient = new TimeSeries<Double>();
        for (Integer i : this.keySet()) {
            if (!ts.keySet().contains(i)) {
                throw new IllegalArgumentException("Missing a key in the input");
            }
            Double divided = this.get(i).doubleValue() / ts.get(i).doubleValue();
            quotient.put(i, divided);
        }
        return quotient;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> summation = new TimeSeries<Double>();
        for (Integer x : this.keySet()) {
            Double sum = this.get(x).doubleValue();
            if (ts.containsKey(x)) {
                sum = sum + ts.get(x).doubleValue();
            }
            summation.put(x, sum);
        }
        for (Integer y : ts.keySet()) {
            if (!summation.containsKey(y)) {
                summation.put(y, ts.get(y).doubleValue());
            }
        }
        return summation;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        Collection<Number> allYears = new ArrayList<Number>();
        for (Integer i : this.keySet()) {
            allYears.add(i);
        }
        return allYears;
    }

    /** Returns all data for this time series. 
      * Must be in the same order as years(). */
    public Collection<Number> data() {
        Collection<Number> allData = new ArrayList<Number>();
        for (Number year : this.years()) {
            allData.add(this.get(year));
        }
        return allData;
    }
}
