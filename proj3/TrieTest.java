public class TrieTest {
	public static void main(String[] args) {
		Trie cicle = new Trie();
		System.out.println(cicle.pointer.links);
		cicle.insert("hi");
		System.out.println(cicle.pointer.links);
		System.out.println(cicle.pointer.links.get('h').links);

		for (Character c : cicle.pointer.links.keySet()) {
			System.out.println(c + ": " + cicle.pointer.links.get(c));
		}
		for (Character c : cicle.pointer.links.get('h').links.keySet()) {
			System.out.println(c + ": " + cicle.pointer.links.get('h').links.get(c));
		}
	}
}