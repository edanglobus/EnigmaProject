package Service;

public class Engine {

    private final Reflector reflector;
    private  final rotorsManagers manager;
    private final String alphabet;

    public Engine(Reflector reflector, rotorsManagers manager, String alphabet) {
        this.reflector = reflector;
        this.manager = manager;
        this.alphabet = alphabet;
    }

    public char processChar(char ch) {
        int index = alphabet.indexOf(ch);
        if (index == -1) {
            throw new IllegalArgumentException("Char '" + ch + "' not in alphabet");
        }
        manager.stepRotors();
        int signal = manager.passForward(index);
        signal = reflector.reflect(signal);
        signal = manager.passBackward(signal);
        return alphabet.charAt(signal);
    }

    public String processString(String str){
        StringBuilder sb = new StringBuilder();
        for (char ch : str.toCharArray()) {
            sb.append(processChar(ch));
        }
        return sb.toString();
    }




}
