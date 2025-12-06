package Service;

import WiringCables.WiringRotor;

import java.util.List;

public class rotorsManagers {
    private final Rotor[] rotors;

    public rotorsManagers(Rotor[] rotors) {
        this.rotors = rotors;
    }

    public void stepRotors(){
        rotors[0].rotate();
        checkRotate();
    }

    public void setRotorPosition(List<Integer> position){
        if (position.size() != rotors.length){
            throw new IllegalArgumentException("Position list size does not match number of rotors");
        }
        int size = rotors.length;
        for (int i = 0; i < size ; i++) {
            int input = position.get(i);
            rotors[i].setPosition(input);
        }
    }


    //check if the rotors need to rotate the next rotor
    public void checkRotate(){
       int sizeABC = rotors[0].sizeABC;
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
        String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = rotors.length - 1; i >= 0; i--) {
            WiringRotor wiringRotor = rotors[i].getWiringRotor();
            int res2 = wiringRotor.wiringBackwards[signal];
            System.out.println(check.charAt(res2) + "index in: " + signal);

            signal = rotors[i].encodeBackward(signal);

            int res = wiringRotor.wiringBackwards[signal];
            System.out.println(check.charAt(res) + "index out: " + signal);
        }
        return signal;
    }

    public void printRotorsState(){
        for (Rotor rotor : rotors) {


            System.out.printf("Rotor ID: %d, Position: %d, Noche: %d\n", rotor.getID(), rotor.getPosition(), rotor.getNoche());
        }
    }

    public Rotor[] getRotors() {
        return rotors;
    }
}

