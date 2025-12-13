import history.ConfigurationStats;
import jaxb.EnigmaJaxbLoader;

import hardware.engine.Engine;
import hardware.Utils;
import hardware.parts.Rotor;
import machine.Machine;
import software.config.AutoConfig;
import software.config.MachineConfig;
import software.config.ManualConfig;
import storage.StorageManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MachineManager {

    private final EnigmaJaxbLoader Loader = new EnigmaJaxbLoader();
    private StorageManager SM = new StorageManager(Loader);
    private final Machine enigmaMachine = new Machine();



    boolean isFileLoaded = false;


    public void order1() throws Exception {
        try {
            System.out.println("Supply XML path:");
            Scanner sc = new Scanner(System.in);
            String path = sc.nextLine().trim(); //to check path

            StorageManager tempSM = new StorageManager(Loader);
            tempSM.loadSupplyXMLCheckAndBuildStorages(path);
            SM = tempSM;

            isFileLoaded = true;
        }
        catch (IllegalArgumentException iae){
            throw new Exception("Invalid XML file: " + iae.getMessage());
        }
        catch (Exception e) {
            throw new Exception("Failed to load XML file: " + e.getMessage());
        }
    }

    public void order2(){
        String sb = "Amount of rotors: " +
                SM.getRotorsAmount() +
                "\n" +
                "Amount of reflectors: " +
                SM.getReflectorsAmount() +
                "\n" +
                "amount of string that encoded: " +
                (enigmaMachine.getEngine() != null ? enigmaMachine.getEngine().getNumberOfEncryptions() : 0) +
                "\n" +
                "Original positions: " +
                getCode(true) +
                "\n" +
                "Current code: " +
                getCode(false);
        System.out.println(sb);
    }

    public void order3() {
        if (!isFileLoaded) {
            throw new UnsupportedOperationException("XML File Not Loaded Yet - Make Order 1 First");
        }
        MachineConfig machineConfiguration = new ManualConfig(SM);
        this.enigmaMachine.setEngine(machineConfiguration.configureAndGetEngine());
        ConfigurationStats state = new ConfigurationStats(getCode(true));
        enigmaMachine.getFullHistory().add(state);
    }

    public void order4() {
        if (!isFileLoaded) {
            throw new UnsupportedOperationException("XML File Not Loaded Yet - Make Order 1 First");
        }
        MachineConfig machineConfiguration = new AutoConfig(SM);
        this.enigmaMachine.setEngine(machineConfiguration.configureAndGetEngine());
        ConfigurationStats state = new ConfigurationStats(getCode(true));
        enigmaMachine.getFullHistory().add(state);
    }


    public void order5() {
        if (enigmaMachine.getEngine() == null) {
            throw new UnsupportedOperationException("Engine Not Configured Yet - Make Order 3/4 First");
        }
        System.out.printf("Write the string you want to encode/decode:\n");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim().toUpperCase();
        //start measure time
        long start = System.nanoTime();

        String cipher = enigmaMachine.getEngine().processString(input);
        //end measure time
        long end = System.nanoTime();

        System.out.printf("The result is:\n%s\n", cipher);

        if (!enigmaMachine.getFullHistory().isEmpty()) {
           //pull the last configuration stats
            enigmaMachine.getFullHistory().getLast().addProcessedString(input, cipher, (end - start));
        }

    }

    public void order6(){
        if (enigmaMachine.getEngine() == null) {
            throw new UnsupportedOperationException("Engine Not Configured Yet - Make Order 3/4 First");
        }
        Rotor[] rotors = enigmaMachine.getEngine().getRotorsManagers().getRotors();
        List<Character> originalPosition = SM.getOriginalPosition();
        for (int i = 0; i < rotors.length; i++) {
            rotors[i].setPosition(rotors[i].getWiringRotor().getIndexOfChInRightColumn(originalPosition.get(i)));
        }
    }

    public void order7(){
        showHistory();
    }



    public void saveMachineState() {
        System.out.print("Enter file path for BINARY save (e.g., 'session_backup.dat'): ");
        Scanner sc = new Scanner(System.in);
        String filePathName = sc.nextLine().trim();
        String finalFileName = filePathName.endsWith(".dat") ? filePathName : filePathName + ".dat";
        Path filePath = Paths.get(finalFileName);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {

            oos.writeObject();

            System.out.println("Full machine state saved successfully (BINARY) to: " + filePath.toAbsolutePath());

        } catch (NotSerializableException nse) {
             System.err.println("Serialization Error: A class is missing 'implements Serializable'. Please ensure all required classes (Rotor, Engine, etc.) are marked as serializable.");
             System.err.println("Error details: " + nse.getMessage());
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    public  String getCode(boolean original) {
       if (enigmaMachine.getEngine() == null) {
           return "Machine Not Configured Yet - No Code Available";
       }
        return '<' +
                buildRotorString() +
                '>' +
                '<' +
                buildPositionString(original) +
                '>' +
                '<' +
                buildReflectorString() +
                '>';

    }

    public  String buildRotorString(){
        StringBuilder sb = new StringBuilder();
        Rotor[] rotors = enigmaMachine.getEngine().getRotorsManagers().getRotors();
        int index = rotors.length - 1;
        for (int i = index; i >=0 ; i--) {
            sb.append(rotors[i].getID());
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public  String buildReflectorString(){
        return enigmaMachine.getEngine().getReflectorId();
    }

    public String buildPositionString(boolean original) {
        StringBuilder sb = new StringBuilder();
        Rotor[] rotors = enigmaMachine.getEngine().getRotorsManagers().getRotors();
        List<Character> originalPosition = SM.getOriginalPosition();
        int index = rotors.length - 1;
        for (int i = index; i >= 0; i--) {
            sb.append(original ? originalPosition.get(i) : SM.getABC().charAt(rotors[i].getPosition()));
            sb.append('(');
            int res = rotors[i].getNoche() - (original ? Utils.charToIndex(originalPosition.get(i), SM.getABC()) : rotors[i].getPosition());
            sb.append(Utils.normalize(res, rotors[i].sizeABC));
            sb.append(')');
            sb.append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }



    public void showHistory() {
        if (enigmaMachine.getFullHistory().isEmpty()) {
            System.out.println("\nNo history found. The machine hasn't been configured yet.");
            return;
        }
        System.out.println("\nHistory:\n");
        for (ConfigurationStats stats : enigmaMachine.getFullHistory()) {
            System.out.println(stats);
            System.out.println("----------------------------------------");
        }
    }
}
