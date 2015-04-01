// Represents a card from a 52-card deck.
public class Card {

    // Declare some constants. Your code may or may not find this helpful.
    public static final int SPADES = 1;
    public static final int HEARTS = 2;
    public static final int DIAMONDS = 3;
    public static final int CLUBS = 4;

    // These should be declared private, but declare them public for ease of
    // testing (name, for compatibility with the provided Junit test)
    public int suit;
    public int number;

    public Card(int suit, int number) {
        if (suit < 1 || suit > 4) {
            throw new IllegalArgumentException("Invalid card suit input");
        }
        if (number < 1 || number > 13) {
            throw new IllegalArgumentException("Invalid card number input");
        }
        this.suit = suit;
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Card) {
            Card obj = (Card) o;
            if (obj.suit == this.suit && obj.number == this.number) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.suit * 100) + this.number;
    }
}