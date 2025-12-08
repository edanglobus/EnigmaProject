package hardware.engine;


import hardware.Utils;
import hardware.parts.Reflector;

public class Engine {

    private final Reflector reflector;
    private final rotorsManagers manager;
    private final String alphabet;
    private int numberOfEncryptions = 0;


    public Engine(Reflector reflector, rotorsManagers manager, String alphabet) {
        this.reflector = reflector;
        this.manager = manager;
        this.alphabet = alphabet;

        if (manager.getRotors().length < 3) {
            throw new IllegalArgumentException("At least 3 rotors are required to initialize the engine.");
        }
    }

    public char processChar(char ch) {
        int index = alphabet.indexOf(ch);
        if (index == -1) {
            throw new IllegalArgumentException("Char '" + ch + "' not in alphabet");
        }
        System.out.printf("\nProcessing char: %c (index %d)\n", ch, index);
        manager.stepRotors();
        int signal = manager.passForward(index);
        System.out.printf("Char after rotors forward pass: %c  (index %d)\n", Utils.MapNumToABC(signal), signal);
        signal = reflector.reflect(signal);
        System.out.printf("             Reflecting output Char: %c %d\n",Utils.MapNumToABC(index) , index);

        System.out.printf("Char after reflector: %c  (index %d)\n", Utils.MapNumToABC(signal), signal);
        signal = manager.passBackward(signal);
        System.out.printf("Char after rotors backward pass: %c  (index %d)\n", Utils.MapNumToABC(signal), signal);
        return alphabet.charAt(signal);
    }

    public String processString(String str){
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            sb.append(processChar(ch));
        }
        numberOfEncryptions++;
        return sb.toString();
    }

    public rotorsManagers getRotorsManagers() {
        return manager;
    }

    public void trackRotorsState() {
        manager.printRotorsState();
    }

    public int getNumberOfEncryptions() {
        return numberOfEncryptions;
    }

    public String getReflectorId(){
        return reflector.getID();
    }





}




