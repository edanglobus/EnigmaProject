import Service.Router;
import Service.Wiring;

public class Main {
    public static void main(String[] args) {

        String alphabet = "ABCD";

        // 1. Create wiring and set it from the two columns
        Wiring wiring = new Wiring();
        String rightCol = "BCDA"; // index 0 = B, 1 = C, 2 = D, 3 = A
        String leftCol  = "CBDA";

        wiring.setWiring(rightCol, leftCol, alphabet);

        // 2. Create router (ID = 1, noches = 0 for now)
        Router router = new Router(1, 0, alphabet);
        router.setWiring(wiring);   // use the wiring we just built

        // position = 0 by default – we test pure wiring first
        System.out.println("=== encodeForward with position = 0 ===");
        for (int i = 0; i < alphabet.length(); i++) {
            char inChar = alphabet.charAt(i);  // A, B, C, D
            int inIndex = i;                   // 0, 1, 2, 3

            int outIndex = router.encodeForward(inIndex);
            char outChar = alphabet.charAt(outIndex);

            System.out.println(
                    inChar + " (" + inIndex + ") -> " +
                            outChar + " (" + outIndex + ")"
            );
        }

        // 3. Now test with rotor position = 1
        System.out.println("\n=== encodeForward with position = 1 ===");
        // you probably have a setter or you can add one:
        // public void setPosition(int position) { this.position = position % sizeABC; }
        router.setPosition(1);

        for (int i = 0; i < alphabet.length(); i++) {
            char inChar = alphabet.charAt(i);
            int inIndex = i;

            int outIndex = router.encodeForward(inIndex);
            char outChar = alphabet.charAt(outIndex);

            System.out.println(
                    inChar + " (" + inIndex + ") -> " +
                            outChar + " (" + outIndex + ")"
            );
        }
    }
}
