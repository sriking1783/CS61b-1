public class TrieTest {
	public static void main(String[] args) {
		// test1();
		test2();
	}

	// public static void test1() {
	// 	Trie cicle = new Trie();
	// 	cicle.insert("hi");
	// 	// System.out.println(cicle.pointer.links);
	// 	// System.out.println(cicle.pointer.links.get('h').links);

	// 	// for (Character c : cicle.pointer.links.keySet()) {
	// 	// 	System.out.println(c + ": " + cicle.pointer.links.get(c));
	// 	// }
	// 	// for (Character c : cicle.pointer.links.get('h').links.keySet()) {
	// 	// 	System.out.println(c + ": " + cicle.pointer.links.get('h').links.get(c));
	// 	// }

	// 	cicle.insert("hit");
	// 	System.out.println(cicle.pointer.links);
	// 	System.out.println(cicle.pointer.links.get('h').links);
	// 	System.out.println(cicle.pointer.links.get('h').links.get('i').links);

	// 	for (Character c : cicle.pointer.links.keySet()) {
	// 		System.out.println(c + ": " + cicle.pointer.links.get(c));
	// 	}
	// 	for (Character c : cicle.pointer.links.get('h').links.keySet()) {
	// 		System.out.println(c + ": " + cicle.pointer.links.get('h').links.get(c));
	// 	}
	// 	for (Character c : cicle.pointer.links.get('h').links.get('i').links.keySet()) {
	// 		System.out.println(c + ": " + cicle.pointer.links.get('h').links.get('i').links.get(c));
	// 	}
	// }

	public static void test2() {
	    Trie t = new Trie();
	    t.insert("hello");
	    t.insert("hey");
	    t.insert("goodbye");
	    System.out.println(t.find("hell", false));
	    System.out.println(t.find("hello", true));
	    System.out.println(t.find("good", false));
	    System.out.println(t.find("bye", false));
	    System.out.println(t.find("heyy", false));
	    System.out.println(t.find("hell", true));   
	}
}