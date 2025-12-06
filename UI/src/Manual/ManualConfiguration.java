package Manual;

import Service.Engine;
import Service.Reflector;
import Service.Rotor;
import Service.rotorsManagers;
import parts.StorageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ManualConfiguration {
    private final StorageManager storageManager;

    public ManualConfiguration(StorageManager SM) {
        this.storageManager = SM;
    }

    private List<Rotor> askRotors() {
        System.out.println("Enter rotors IDs separated by commas (left to right): ");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        String[] parts = input.split("\\s*,\\s*");

        List<Rotor> rotors = new ArrayList<>();
        for (String part : parts) {
            int rotorId = Integer.parseInt(part);
            rotors.add(storageManager.optionalGetRotorByID(rotorId));
        }

        return rotors.reversed();
    }

    private Reflector askReflector() {
        System.out.println("which reflector do you want to use?");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        return storageManager.optionalGetReflectorByID(input);
    }

    private List<Character> askPositions() {
        System.out.println("Enter initial positions (letters, left to right, based on alphabet " + storageManager.getABC() + "):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toUpperCase();
        return breakPositionString(input);
    }

    private List<Character> breakPositionString(String positions) {
        // Returns IntStream
        // Cast int to Character
        return positions.chars()              // Returns IntStream
                .mapToObj(c -> (char) c)           // Cast int to Character
                .toList().reversed();
    }

    public Engine configureAndGetEngine() {
        List<Rotor> rotors = askRotors();
        List<Character> positions = askPositions();
        storageManager.setOriginalPosition(positions);
        Reflector reflector = askReflector();
        List<Integer> indexOfPositions = storageManager.getIndexInABC(positions);



        rotorsManagers manager = new rotorsManagers(rotors.toArray(new Rotor[0]));
        manager.setRotorPosition(indexOfPositions);
        return new Engine(reflector, manager, storageManager.getABC());
    }

}

