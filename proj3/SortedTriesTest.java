import java.util.ArrayList;

public class SortedTriesTest {
    public static void main(String[] args) {
        test1();
    }

    public static void test1() {
        ArrayList<Character> list = new ArrayList<Character>();
        list.add('h');
        list.add('i');
        list.add('t');
        list.add('a');
        list.add('d');
        SortedTries cicle = new SortedTries(list);
        cicle.insert("add", list);
        cicle.insert("hit", list);
        cicle.insert("hat", list);
        System.out.println(cicle.pointer.links.keySet());
        System.out.println(cicle.pointer.links.get('h').links.keySet());
    }
}
