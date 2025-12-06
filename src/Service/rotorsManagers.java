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
                System.out.printf("Rotor State Before:   Rotor ID: %d, Position: %d, Noche: %d\n", rotors[i+1].getID(), rotors[i+1].getPosition(), rotors[i+1].getNoche());
                rotors[i + 1].rotate();
                System.out.printf("Rotor State After:   Rotor ID: %d, Position: %d, Noche: %d\n", rotors[i+1].getID(), rotors[i+1].getPosition(), rotors[i+1].getNoche());
            } else {
                break;
            }
        }
    }
    //passing the signal through the rotors from right to left
    public int passForward(int index) {
        int signal = index;

        for (Rotor rotor : rotors) {
            System.out.printf("     Passing through Rotor ID: %d, Position: %d, Noche: %d\n", rotor.getID(), rotor.getPosition(), rotor.getNoche());
            System.out.printf("             Input Char: %c (index %d)\n", Utils.MapNumToABC(signal), signal);
            signal = rotor.encodeForward(signal);
            System.out.printf("             Output Char: %c (index %d)\n", Utils.MapNumToABC(signal), signal);
        }

        return signal ;
    }
    //passing the signal through the rotors from left to right
    public int passBackward(int index) {
        int signal = index;
        for (int i = rotors.length - 1; i >= 0; i--) {
            System.out.printf("     Passing through Rotor ID: %d, Position: %d, Noche: %d\n", rotors[i].getID(), rotors[i].getPosition(), rotors[i].getNoche());
            System.out.printf("             Input Char: %c (index %d)\n", Utils.MapNumToABC(signal), signal);
            signal = rotors[i].encodeBackward(signal);
            System.out.printf("             Output Char: %c (index %d)\n", Utils.MapNumToABC(signal), signal);
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

    public List<Integer> MappingInputCharPositionByRightColumnToIndex(List<Character> Inputs) {
        List<Integer> positions = new java.util.ArrayList<>();
        int index = 0;
        for (Rotor rotor : rotors) {
            positions.add(rotor.getWiringRotor().getIndexOfChInRightColumn(Inputs.get(index)));
            index++;
        }
        return positions;
    }
}

