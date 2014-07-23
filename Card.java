/**
 * Card models a standard playing card. Each card has a suit
 * and rank... It also has an integer worth that can be used
 * for comparison purposes and an image number used to index 
 * an array of card images. Card could easily be modified or
 * extended to support alternative definitions of worth, but
 * the current implementation supports its current use.
 * 
 * There are two constructors. The first constructor accepts a
 * suit, a rank, and an imageNum used to look up an Image object
 * for the card in an array. The second constructor accepts a 
 * String that contains two characters (the suit and the rank)
 * and an imageNum. There is no default constructor.
 * 
 * Accessor methods were not created. For simplicity, the Card's 
 * fields were defined as public.
 * 
 * Card overrides the toString method, returning a String that
 * contains two characters: the suit and the rank.
 */
public class Card {

    // fields
    public char suit; // the card's suit (c, d, h, or s)
    public char rank; // rank appearing on the card
                      // (1-9, t, j, q, or k)
    public int worth; // numerical worth of each card: 2-9
                      // are worth their face values, t-k
                      // are worth 10, and 1 (i.e., the ace)
                      // is worth 11
    public int imageNum; // index of card's image in the
                         // Image array

    // primary constructor
    public Card(char s, char r, int num) {
        suit = s;
        rank = r;
        imageNum = num;
        if (rank == 'k' || rank == 'q' || rank == 'j' || rank == 't') {
            worth = 10;
        } else if (rank == '1') {
            worth = 11;
        } else {
            worth = Integer.parseInt("" + r);
        }
    }

    // alternate constructor
    public Card(String c, int num) {
        this(c.charAt(0), c.charAt(1), num);
    }

    @Override
    public String toString() {
        return "" + suit + rank;
    }
}