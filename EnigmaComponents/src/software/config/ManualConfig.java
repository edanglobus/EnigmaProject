package software.config;

import hardware.engine.Engine;
import hardware.parts.Reflector;
import hardware.parts.Rotor;
import hardware.engine.rotorsManagers;


import java.util.*;

public class ManualConfig extends MachineConfig {
    Scanner sc = new Scanner(System.in);

    public ManualConfig(storage.StorageManager SM) {
        super(SM);
    }

    private List<Rotor> askRotors() {
        System.out.println("Enter rotors IDs separated by commas left to right, "
                + storageManager.rotorStorageString() + ":");
        String input = sc.nextLine().trim();
        String[] parts = input.split("\\s*,\\s*");
        Set<Integer> usedRotors = new HashSet<>();
        List<Rotor> rotors = new ArrayList<>();
        for (String part : parts) {
            int rotorId = Integer.parseInt(part);
            if (usedRotors.contains(rotorId)) {
                throw new IllegalStateException ("Cannot build enigma machine with duplicate rotors");
            }
            usedRotors.add(rotorId);
            rotors.add(storageManager.optionalGetRotorByID(rotorId));
        }
        return rotors.reversed();
    }

    private Reflector askReflector() {
        System.out.println("Choose one reflector " + storageManager.reflectorStorageString() + ": ");
        String input = sc.nextLine().trim();
        return storageManager.optionalGetReflectorByID(input);
    }

    private List<Character> askPositions() {
        System.out.println("Enter initial positions left to right, based on alphabet " + storageManager.getABC() + ":");
        String input = sc.nextLine().trim().toUpperCase();
        return breakPositionString(input);
    }

    private List<Character> breakPositionString(String positions) {
        // Returns IntStream
        // Cast int to Character
        return positions.chars()              // Returns IntStream
                .mapToObj(c -> (char) c)           // Cast int to Character
                .toList().reversed();
    }

    @Override
    public Engine configureAndGetEngine() {
        List<Rotor> rotors = askRotors();
        List<Character> positions = askPositions();
        if (rotors.size() != positions.size()) {
            throw new IllegalArgumentException("Number of positions must match rotors amount");
        }

        storageManager.setOriginalPosition(positions);
        Reflector reflector = askReflector();


        rotorsManagers manager = new rotorsManagers(rotors.toArray(new Rotor[0]));
        List<Integer> indexOfPositions = manager.MappingInputCharPositionByRightColumnToIndex(positions);
        manager.setRotorPosition(indexOfPositions);
        return new Engine(reflector, manager, storageManager.getABC());
    }
}
