import FileHandler.EnigmaJaxbLoader;
import Manual.ManualConfiguration;
import Service.Engine;
import parts.StorageManager;

import java.util.Scanner;

public class MainManager {

    private final EnigmaJaxbLoader Loader = new EnigmaJaxbLoader();
    private final StorageManager PM = new StorageManager(Loader);
    private String path;
    private Engine enigmaEngine;


    boolean isFileLoaded = false;


    public void order1() throws Exception {
        System.out.println("Supply XML path:");
        Scanner sc = new Scanner(System.in);
        String path = sc.nextLine().trim(); //to check path

        PM.loadSupplyXMLCheckAndBuildStorages(path);
        isFileLoaded = true;
    }

    public void order3() {
        if (!isFileLoaded) {
            throw new UnsupportedOperationException("XML File Not Loaded Yet - Make Order 1 First");
        }
        ManualConfiguration manualConfiguration = new ManualConfiguration(PM);
        this.enigmaEngine = manualConfiguration.configureAndGetEngine();
    }

    public void order5() {
        if (enigmaEngine == null) {
            throw new UnsupportedOperationException("Engine Not Configured Yet - Make Order 3/4 First");
        }
        System.out.printf("Write the string you want to encode/decode:\n");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim().toUpperCase();
        String chiper = enigmaEngine.processString(input);
        System.out.printf("The result is:\n%s\n", chiper);
    }

}
