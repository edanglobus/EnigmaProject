import EnigmaHistory.ConfigurationStats;
import FileHandler.EnigmaJaxbLoader;
import Manual.ManualConfiguration;
import Service.Engine;
import Service.Rotor;
import Service.Utils;
import parts.StorageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainManager {

    private final EnigmaJaxbLoader Loader = new EnigmaJaxbLoader();
    private final StorageManager SM = new StorageManager(Loader);
    private String path;
    private Engine enigmaEngine;
    List<ConfigurationStats> fullHistory = new ArrayList<>();



    boolean isFileLoaded = false;


    public void order1() throws Exception {
        System.out.println("Supply XML path:");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine().trim(); //to check path

        SM.loadSupplyXMLCheckAndBuildStorages(path);
        isFileLoaded = true;
    }

    public void order3() {
        if (!isFileLoaded) {
            throw new UnsupportedOperationException("XML File Not Loaded Yet - Make Order 1 First");
        }
        ManualConfiguration manualConfiguration = new ManualConfiguration(SM);
        this.enigmaEngine = manualConfiguration.configureAndGetEngine();
        ConfigurationStats state = new ConfigurationStats(getCode(true));
        fullHistory.add(state);
    }

    public void order5() {
        if (enigmaEngine == null) {
            throw new UnsupportedOperationException("Engine Not Configured Yet - Make Order 3/4 First");
        }
        System.out.printf("Write the string you want to encode/decode:\n");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim().toUpperCase();
        //start measure time
        long start = System.nanoTime();

        String cipher = enigmaEngine.processString(input);
        //end measure time
        long end = System.nanoTime();

        System.out.printf("The result is:\n%s\n", cipher);

        if (!fullHistory.isEmpty()) {
           //pull the last configuration stats
            ConfigurationStats currentStats = fullHistory.getLast();
            //add the string to the history
            currentStats.addProcessedString(input, cipher, (end - start));
        }

    }

    public void order2(){
        StringBuilder sb = new StringBuilder();
        sb.append("Amount of rotors: ");
        sb.append(SM.getRotorsAmount());
        sb.append("\n");
        sb.append("Amount of reflectors: ");
        sb.append(SM.getReflectorsAmount());
        sb.append("\n");
        sb.append("amount of string that encoded: ");
        sb.append(enigmaEngine.getNumberOfEncryptions());
        sb.append("\n");
        sb.append("Original positions: ");
        sb.append(getCode(true));
        sb.append("\n");
        sb.append("Current code: ");
        sb.append(getCode(false));
        System.out.println(sb.toString());
    }

    public void order6(){
        if (enigmaEngine == null) {
            throw new UnsupportedOperationException("Engine Not Configured Yet - Make Order 3/4 First");
        }
        Rotor[] rotors = enigmaEngine.getRotorsManagers().getRotors();
        List<Character> originalPosition = SM.getOriginalPosition();
        for (int i = 0; i < rotors.length; i++) {
            rotors[i].setPosition(Utils.charToIndex(originalPosition.get(i), SM.getABC()));
        }
    }

    public void order7(){
        showHistory();
    }

    public  String getCode(boolean original) {


        return '<' + buildRotorString() +
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
        Rotor[] rotors = enigmaEngine.getRotorsManagers().getRotors();
        int index = rotors.length - 1;
        for (int i = index; i >=0 ; i--) {
            sb.append(rotors[i].getID());
            sb.append(',');
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public  String buildReflectorString(){
        return enigmaEngine.getReflectorId();
    }

    public String buildPositionString(boolean original) {
        StringBuilder sb = new StringBuilder();
        Rotor[] rotors = enigmaEngine.getRotorsManagers().getRotors();
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
        if (fullHistory.isEmpty()) {
            System.out.println("No history found. The machine hasn't been configured yet.");
            return;
        }
        System.out.println("History:\n");
        for (ConfigurationStats stats : fullHistory) {
            System.out.println(stats);
            System.out.println("----------------------------------------");
        }
    }
}
