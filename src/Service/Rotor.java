package Service;

import WiringCables.WiringRotor;

import java.util.Objects;

public class Rotor implements Roundable {
    private final WiringRotor wiringRotor;
    private final int ID;
    private final int noche;
    private int position;
    private final int sizeABC;


    public Rotor(int ID, int noche, int  ABCSize, WiringRotor wiring) {
        this.ID = ID;
        this.noche = noche;
        this.sizeABC = ABCSize;
        this.wiringRotor = wiring;
        this.position = 0;
    }

//    public void setPosition(int position) {
//        this.position = position;
//    }

    private int normalize(int index) {
        int res = index % sizeABC;
        if (res < 0) {
            res += sizeABC;
        }
        return res;
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
        int wiredContact = wiringRotor.wiringForwards[entryContact];

        // 3. Convert wired contact back to logical index (undo rotation)
        return mapOutputToRotorContact(wiredContact);
    }

    public int encodeBackward(int inputIndex) {
        int entryContact = mapInputToRotorContact(inputIndex);
        int wiredContact = wiringRotor.wiringBackwards[entryContact];
        return mapOutputToRotorContact(wiredContact);
    }

    @Override
    public void rotate (){
        this.position = (this.position + 1) % sizeABC;
    }

    public int getNoche() {
        return noche;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rotor rotor = (Rotor) o;
        return ID == rotor.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }
}
