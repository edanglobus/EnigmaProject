package UI;

import Service.Engine;
import Service.Reflector;
import Service.Rotor;
import Service.rotorsManagers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ManualConfiguration {
   private  Engine engine;
   private Map<Integer, Rotor> allRotors;
   private List<Reflector> allReflectors;
   private final Storage storage;
   private final String alphabet;

    public ManualConfiguration(Storage storage) {
        this.storage = storage;
        this.allRotors = storage.getAllRotors();
        this.allReflectors = storage.getAllReflectors();
        this.alphabet = storage.getABC();
    }

    public List<Integer> askRotors() {

        System.out.println("Enter rotors IDs separated by commas (left to right): ");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        String[] parts = input.split("\\s*,\\s*");

        List<Integer> rotorIds = new ArrayList<>();

        for (String p : parts) {
            try {
                int id = Integer.parseInt(p);
                if (!allRotors.containsKey(id)) {
                    System.out.println("Rotor " + id + " does not exist.");
                    continue;   // או לזרוק שגיאה — אתה תבחר**************************************************************
                }
                rotorIds.add(id);
            } catch (NumberFormatException e) {
                System.out.println("Invalid rotor ID: " + p);
            }
        }

        return rotorIds;
    }

   private void askReflector(){
       System.out.println("which reflector do you want to use?");
       Scanner sc = new Scanner(System.in);
       String input = sc.nextLine().trim();
   }

    private String askPositions(int rotorCount) {
        System.out.println("Enter initial positions (" + rotorCount +
                " letters, left to right, based on alphabet " + alphabet + "):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.length() != rotorCount) {
            throw new IllegalArgumentException("Positions length must match number of rotors.");
        }

        // אפשר להוסיף בדיקה שכל תו קיים ב־alphabet
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (alphabet.indexOf(c) == -1) {
                throw new IllegalArgumentException("Char '" + c + "' not in alphabet " + alphabet);
            }
        }
        return input;
    }

   private List<Rotor> getSelectedRotors(List<Integer> rotorIds){
        List<Rotor> selectedRotors = new ArrayList<>();
        for (int id : rotorIds) {
            selectedRotors.add(allRotors.get(id));
        }
        return selectedRotors;
   }

   public Rotor[] reverseOrder(Rotor[] rotors, String positions) {
       int n = 3;

       Rotor[] rotorsArray = new Rotor[n];
       for (int i = 0; i < n; i++) {
           rotorsArray[i] = rotors[n - 1 - i]; //reverse order
       }
       for (int i = 0; i < n; i++) {
           char c = positions.charAt(positions.length() - 1 - i); // reverse order
           int pos = alphabet.indexOf(c);
           rotorsArray[i].setPosition(pos); // set initial position
       }
       return rotorsArray;
   }














}




