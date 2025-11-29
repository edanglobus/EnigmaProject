package WiringCables;

public class WiringRotor implements Wiring{
    public   int [] wiringForwards;
    public int [] wiringBackwards;


    public WiringRotor(String rightColumn, String leftColumn, String alphabet) {
        this.wiringForwards = new int[alphabet.length()];
        this.wiringBackwards = new int[alphabet.length()];

        if (rightColumn.length() != alphabet.length() || leftColumn.length() != alphabet.length()) {
            throw new IllegalArgumentException("Columns must match alphabet size");
        }
        for (int i = 0; i < alphabet.length(); i++) {
            char letter = alphabet.charAt(i);

            int rightIndex = rightColumn.indexOf(letter); //find the letter in the right column
            int leftIndex = leftColumn.indexOf(letter);  // find the letter in the left column

            if (rightIndex == -1 || leftIndex == -1) {
                throw new IllegalArgumentException(
                        "Letter '" + letter + "' must appear in both columns");
            }
            //connect the wire
            this.wiringForwards[rightIndex] = leftIndex;
            this.wiringBackwards[leftIndex] = rightIndex;
        }
    }


}
