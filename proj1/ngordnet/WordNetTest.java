package ngordnet;
import org.junit.Test;
import static org.junit.Assert.*;

public class WordNetTest {

    @Test
    public void testIsNoun() {
        WordNet wn = new WordNet("./wordnet/synsets14.txt", "./wordnet/hyponyms14.txt");
        System.out.println(wn.isNoun("jump"));
        System.out.println(wn.isNoun("leap"));
        System.out.println(wn.isNoun("nasal_decongestant"));
    }

    // @Test
    // public void testLength() {
    //     GenericList<String> b = new GenericList<String>();
    //     assertEquals(0, b.length());
    //     b.insert("a");
    //     b.insert("Bb");
    //     assertEquals(2, b.length());
    // }

    // @Test
    // public void testToString() {
    //     GenericList<Integer> a = new GenericList<Integer>();
    //     assertEquals("[]", a.toString());
    //     a.insert(9);
    //     assertEquals("[9]", a.toString());
    //     a.insert(5);
    //     a.insert(7);
    //     assertEquals("[7, 5, 9]", a.toString());
    // }

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(WordNet.class);
    }

}
