package parts.reflector;

import Service.Reflector;
import parts.PartsStorage;

import java.util.Map;

public class ReflectorStorage implements PartsStorage {
    private Map<String, Reflector> reflectorMap;

    public ReflectorStorage(Map<String, Reflector> reflectors) {
        this.reflectorMap = reflectors;
    }

    public Reflector getReflectorByID(String id) {
        id = convertNumberToRome(id);
        return reflectorMap.get(id);
    }

    public boolean containsReflector(String id) {
        id = convertNumberToRome(id);
        return reflectorMap.containsKey(id);
    }

    public void printAvailableReflectors() {
        System.out.println("Available Reflectors:");
        for (String id : reflectorMap.keySet()) {
            System.out.print(id + " ");
        }
    }

    @Override
    public int getPartCount() {
        return reflectorMap.size();
    }

    public void setReflectorMap(Map<String, Reflector> reflectors) {
        this.reflectorMap = reflectors;
    }

    @Override
    public String toString() {
        return "ReflectorStorage: \n" + reflectorMap.values();
    }

    private String convertNumberToRome(String id) {
        return switch (id) {
            case "1" -> "I";
            case "2" -> "II";
            case "3" -> "III";
            case "4" -> "IV";
            case "5" -> "V";
            default -> throw new IllegalArgumentException("Invalid reflector ID: " + id);
        };
    }

}
