package Service;

public class rotorsManagers {
    private final Rotor[] rotors;

    public rotorsManagers(Rotor[] rotors) {
        this.rotors = rotors;
    }


    public void stepRotors(){
        rotors[0].rotate();
        checkRotate();
    }

    //check if the rotors need to rotate the next rotor
    public void checkRotate(){
        for (int i = 0; i < rotors.length - 1; i++) {
            if (rotors[i].getNoche() == rotors[i].getPosition()) {
                rotors[i + 1].rotate();
            } else {
                break;
            }
        }
    }
    //passing the signal through the rotors from right to left
    public int passForward(int index) {
        int signal = index;
        for (Rotor rotor : rotors) {
            signal = rotor.encodeForward(signal);
        }
        return signal;
    }
    //passing the signal through the rotors from left to right
    public int passBackward(int index) {
        int signal = index;
        for (int i = rotors.length - 1; i >= 0; i--) {
            signal = rotors[i].encodeBackward(signal);
        }
        return signal;
    }
}

