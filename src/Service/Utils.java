package Service;

public class Utils {

    public static int charToIndex(char c, String alphabet) {
        c = Character.toUpperCase(c);
        return alphabet.indexOf(c);
    }


    // Convert index to letter
    public static char indexToChar(int index, String alphabet) {
        index = ((index % alphabet.length()) + alphabet.length()) % alphabet.length();
        return alphabet.charAt(index);
    }

    public static int normalize(int index, int size) {
        int res = index % size;
        if (res < 0) {
            res += size;
        }
        return res;
    }
}
