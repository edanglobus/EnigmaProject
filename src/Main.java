//import Service.Router;
//import Service.Wiring;
//
//public class Main {
//    public static void main(String[] args) {
//
//        String alphabet = "ABCD";
//
//        // 1. Create wiring and set it from the two columns
//        Wiring wiring = new Wiring();
//        String rightCol = "BCDA"; // index 0 = B, 1 = C, 2 = D, 3 = A
//        String leftCol  = "CBDA";
//
//        wiring.setWiring(rightCol, leftCol, alphabet);
//
//        // 2. Create router (ID = 1, noches = 0 for now)
//        Router router = new Router(1, 0, alphabet);
//        router.setWiring(wiring);   // use the wiring we just built
//
//        // position = 0 by default – we test pure wiring first
//        System.out.println("=== encodeForward with position = 0 ===");
//        for (int i = 0; i < alphabet.length(); i++) {
//            char inChar = alphabet.charAt(i);  // A, B, C, D
//            int inIndex = i;                   // 0, 1, 2, 3
//
//            int outIndex = router.encodeForward(inIndex);
//            char outChar = alphabet.charAt(outIndex);
//
//            System.out.println(
//                    inChar + " (" + inIndex + ") -> " +
//                            outChar + " (" + outIndex + ")"
//            );
//        }
//
//        // 3. Now test with rotor position = 1
//        System.out.println("\n=== encodeForward with position = 1 ===");
//        // you probably have a setter or you can add one:
//        // public void setPosition(int position) { this.position = position % sizeABC; }
//        router.setPosition(1);
//
//        for (int i = 0; i < alphabet.length(); i++) {
//            char inChar = alphabet.charAt(i);
//            int inIndex = i;
//
//            int outIndex = router.encodeForward(inIndex);
//            char outChar = alphabet.charAt(outIndex);
//
//            System.out.println(
//                    inChar + " (" + inIndex + ") -> " +
//                            outChar + " (" + outIndex + ")"
//            );
//        }
//    }
//}


// ******** main for to check if the XML file is loaded correctly **************

//import fileHandler.EnigmaJaxbLoader;
//import fileHandler.EnigmaConfig;
//import fileHandler.RotorConfig;
//import fileHandler.PositioningConfig;
//import fileHandler.ReflectorConfig;
//import fileHandler.ReflectMappingConfig;
//
//import javax.xml.bind.JAXBException;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter full XML path: ");
//        String path = sc.nextLine();
//
//        EnigmaJaxbLoader loader = new EnigmaJaxbLoader();
//
//        try {
//            EnigmaConfig config = loader.loadFromFile(path);
//
//            System.out.println("Alphabet = " + config.getAlphabet());
//
//            System.out.println("\nRotors:");
//            for (RotorConfig r : config.getRotors()) {
//                System.out.println(" Rotor id=" + r.getId() + ", notch=" + r.getNotch());
//                StringBuilder right = new StringBuilder();
//                StringBuilder left  = new StringBuilder();
//                for (PositioningConfig p : r.getPositions()) {
//                    right.append(p.getRight());
//                    left.append(p.getLeft());
//                }
//                System.out.println("  right column = " + right);
//                System.out.println("  left  column = " + left);
//            }
//
//            System.out.println("\nReflectors:");
//            for (ReflectorConfig ref : config.getReflectors()) {
//                System.out.println(" Reflector id=" + ref.getId());
//                for (ReflectMappingConfig m : ref.getMappings()) {
//                    System.out.println("  " + m.getInput() + " -> " + m.getOutput());
//                }
//            }
//
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//    }
//}



// ************* main for check if the configuration mapping works **************
import fileHandler.EnigmaJaxbLoader;
import fileHandler.EnigmaConfig;
import fileHandler.EnigmaConfigMapper;
import Service.Router;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter full XML path: ");
        String path = sc.nextLine();

        EnigmaJaxbLoader loader = new EnigmaJaxbLoader();

        try {
            // 1. Load JAXB config from XML
            EnigmaConfig config = loader.loadFromFile(path);

            // 2. Map JAXB config → Routers
            EnigmaConfigMapper mapper = new EnigmaConfigMapper(config);
            List<Router> routers = mapper.buildRouters();

            System.out.println("Alphabet: " + config.getAlphabet());
            System.out.println("Routers created: " + routers.size());

            // Example: test encodeForward on first router
            Router first = routers.get(0);
            int inputIndex = 0; // 'A'
            int outputIndex = first.encodeForward(inputIndex);
            System.out.println("First router: A (0) -> " + outputIndex);

            first.setPosition(1);
            outputIndex = first.encodeForward(inputIndex);
            System.out.println("First router: A (1) -> " + outputIndex);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
