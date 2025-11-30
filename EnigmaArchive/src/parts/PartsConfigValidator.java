package parts;

import FileHandler.PositioningConfig;
import FileHandler.ReflectorConfig;
import FileHandler.RotorConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartsConfigValidator {
    public static Set<Integer> usedRoutorIds = new HashSet<>();
    public static Set<Integer> usedReflectorIds = new HashSet<>();



    public PartsConfigValidator() {
    }

    public boolean runRotorValidation(RotorConfig rotor) {
        int id = rotor.getID();
        List<PositioningConfig> wires = rotor.getWires();
        boolean pass = false;
        pass = idInRange(1,Integer.MAX_VALUE, id) && rotorIdNotUsed(rotor);
        return pass;
    }
    public boolean runReflectorValidation(ReflectorConfig reflector) {
        int id = reflector.getID();
        boolean pass = false;
        pass = idInRange(1,5, id) && reflectorIdNotUsed(reflector);
        return pass;
    }

    private boolean rotorIdNotUsed(RotorConfig rotor) {
        if (!usedRoutorIds.contains(rotor.getID())) {
            usedRoutorIds.add(rotor.getID());
            return true;
        }
        return false;
    }
    private boolean reflectorIdNotUsed(ReflectorConfig rotor) {
        if (!usedReflectorIds.contains(rotor.getID())) {
            usedReflectorIds.add(rotor.getID());
            return true;
        }
        return false;
    }

    private boolean idInRange(int from, int to, int id) {
        return id >= from && id <= to;
    }
}
