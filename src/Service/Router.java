package Service;

import java.util.Objects;

public class Router implements Roundable, CipherMapable {
    private Wiring wiring;
    private final int ID;
    private int noches;
    private int position;
    private final int sizeABC;
    private final String alphabet;


    public Router(int ID, int noches, String alphabet) {
        this.ID = ID;
        this.noches = noches;
        this.alphabet = alphabet;
        sizeABC = alphabet.length();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private int normalize(int index) {
        int res = index % sizeABC;
        if (res < 0) {
            res += sizeABC;
        }
        return res;
    }

    public void setWiring(Wiring connection) {
        this.wiring = connection;
    }

    /**
     * Converts an input signal index (0...size-1) into the
     * rotor contact that the signal actually hits after the rotor has rotated.
     * Example:
     *   alphabet index = 1 (letter B)
     *   rotor position = 2
     *   -> signal will hit contact (1 + 2) mod size
     */
    private int mapInputToRotorContact(int inputIndex) {
        return normalize(inputIndex + position);
    }
    private int mapOutputToRotorContact(int outputIndex) {
        return normalize(outputIndex - position);
    }

    /**
     * Passes a signal forward through the rotor (right → left).
     * Applies rotor rotation before wiring and removes rotation after wiring.
     */
    public int encodeForward(int inputIndex) {

        // 1. Convert logical index to actual rotor contact after rotation
        int entryContact = mapInputToRotorContact(inputIndex);

        // 2. Pass through the internal wiring (static wiring of rotor at position 0)
        int wiredContact = wiring.wiringForwards[entryContact];

        // 3. Convert wired contact back to logical index (undo rotation)
        return mapOutputToRotorContact(wiredContact);
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Router router = (Router) o;
        return ID == router.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }
}
