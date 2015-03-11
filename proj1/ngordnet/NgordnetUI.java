package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author Aditya Iyengar
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                           + wordFile + ", " + countFile + ", " + synsetFile +
                           ", and " + hyponymFile + ".");

        NGramMap mapNGram = new NGramMap(wordFile, countFile);
        WordNet mapWordNet = new WordNet(synsetFile, hyponymFile);
        Integer startingYear = mapNGram.totalCountHistory().firstKey();
        Integer endingYear = mapNGram.totalCountHistory().lastKey();

        while (true) {
            System.out.print("> ");
            String input = StdIn.readLine();
            String[] words = input.split(" ");
            String initCommand = words[0];
            String[] remainingElements = new String[words.length - 1];
            System.arraycopy(words, 1, remainingElements, 0, words.length - 1);
            try {
                switch(initCommand) {
                    case "quit":
                        return;
                    case "help":
                        In help = new In("./ngordnet/help.txt");
                        String helper = help.readAll();
                        System.out.println(helper);
                        break;
                    case "range":
                        Integer startingDate = Integer.parseInt(remainingElements[0]);
                        Integer endingDate = Integer.parseInt(remainingElements[1]);
                        startingYear = startingDate;
                        endingYear = endingDate;
                        break;
                    case "count":
                        String word = remainingElements[0];
                        Integer year = Integer.parseInt(remainingElements[1]);
                        System.out.println(mapNGram.countInYear(word, year));
                        break;
                    case "hyponyms":
                        String hypernym = remainingElements[0];
                        Set<String> hyponyms = mapWordNet.hyponyms(hypernym);
                        System.out.println(hyponyms);
                        break;
                    case "history":
                        String[] allWords = new String[remainingElements.length];
                        for (int x = 0; x < remainingElements.length; x++) {
                            allWords[x] = remainingElements[x];
                        }
                        Plotter.plotAllWords(mapNGram, allWords, startingYear, endingYear);
                        break;
                    case "hypohist":
                        ArrayList<String> wordsWithHypos = new ArrayList<String>();
                        for (String indivWord : remainingElements) {
                            for (String hypos : mapWordNet.hyponyms(indivWord)) {
                                wordsWithHypos.add(hypos);
                            }
                        }
                        String[] allSubWords = new String[wordsWithHypos.size()];
                        int x = 0;
                        for (String theWord : wordsWithHypos) {
                            allSubWords[x] = theWord;
                            x += 1;
                        }
                        if (allSubWords.length != 0) {
                            Plotter.plotAllWords(mapNGram, allSubWords, startingYear, endingYear);
                        } else {
                            System.out.println("Error: Given word does not have a hyponym set.");
                        }
                        break;
                    default:
                        System.out.println("That is an invalid command.");
                        break;
                }
            } catch (RuntimeException e) {
                System.out.println("Invalid argument: Please input a proper item.");
            }
        }
    }
} 
