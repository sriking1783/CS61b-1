package ngordnet;

public class WordLengthProcessor implements YearlyRecordProcessor {
    public double process(YearlyRecord yearlyRecord) {
        double weightedSum = 0;
        double weight = 0;
        for (String s : yearlyRecord.words()) {
            weightedSum += s.length() * yearlyRecord.count(s);
            weight += yearlyRecord.count(s);
        }
        double average = weightedSum / weight;
        return average;
    }
}
