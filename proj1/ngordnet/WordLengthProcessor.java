package ngordnet;
import java.util.

public class WordLengthProcessor implements YearlyRecordProcessor {
    public double process(YearlyRecord yearlyRecord) {
        int weightedSum = 0;
        int weight = 0;
        for (String s : yearlyRecord.keySet()) {
        	weightedSum += s.length() * yearlyRecord.get(s);
        	weight += yearlyRecord.get(s);
        }
        double average = weightedSum / weight;
        return average;
    }
}
