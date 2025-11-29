package parts;

import fileHandler.WiredPart;
import fileHandler.PositioningConfig;
import fileHandler.ReflectMappingConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PartsConfigValidator {
    String ABC;
    boolean isValid = false;
    public static Set<Integer> usedRoutorIds = new HashSet<>();
    public static Set<Integer> usedReflectorIds = new HashSet<>();



    public PartsConfigValidator(String ABC) {
        this.ABC = ABC;
    }

    public <IO> boolean runValidation(WiredPart<IO> wp) {
        String type = wp.getPartType();
        int id = wp.getID();
        List<IO> wires = wp.getWires();
        boolean pass = false;


        if (type.equals("RotorConfig")) {
            pass = idInRange(1,Integer.MAX_VALUE) && idNotUsed() && routorWiringisValid((List<PositioningConfig>) wires);
        } else if (type.equals("ReflectorConfig")) {
            pass = idInRange(1,5) && idNotUsed() && reflectorWiringisValid((List<ReflectMappingConfig>) wires);
        }
        return pass;
    }

    public boolean reflectorWiringisValid(List<ReflectMappingConfig> wires) {

       int minPort = 1;
       int maxPort = wires.size() * 2;

       // Check that each mapping is valid and that no input or output is repeated
       boolean[] usedInputs = new boolean[maxPort - minPort + 1];
       boolean[] usedOutputs = new boolean[maxPort - minPort + 1];

       for (ReflectMappingConfig mapping : wires) {
           int input = mapping.getInput();
           int output = mapping.getOutput();

           if (!mapping.validWiring(minPort, maxPort)) {
               return false;
           }

           if (usedInputs[input - minPort] || usedOutputs[output - minPort]) {
               return false; // Duplicate input or output
           }

           usedInputs[input - minPort] = true;
           usedOutputs[output - minPort] = true;
       }
       return true;
    }

    public boolean routorWiringisValid(List<PositioningConfig> wires) {
        int minPort = 0;
        int maxPort = ABC.length() - 1;

        // Check that each positioning is valid and that no right or left value is repeated
        Set<Integer> usedReoutorInPorts = new HashSet<>();
        Set<Integer> usedReoutorOutPorts = new HashSet<>();

        for (PositioningConfig position : wires) {
            String right = position.getRight();
            String left = position.getLeft();

            if(!(ABC.contains(right)) || !(ABC.contains(left)) ) {
                return false;
            }
            if (usedReoutorInPorts.contains(ABC.indexOf(right)) || usedReoutorOutPorts.contains(ABC.indexOf(left))) {
                return false; // Duplicate found
            }
            usedReoutorInPorts.add(ABC.indexOf(right));
            usedReoutorOutPorts.add(ABC.indexOf(left));
        }
        return true;
    }

    public boolean reflectorWiringisValid() {
        return isValid;
    }

    public boolean idNotUsed() {
        if (currentWiredPart.getPartType().equals("RouterConfig")) {
            if (!usedRoutorIds.contains(currentWiredPart.getID())) {
                usedRoutorIds.add(currentWiredPart.getID());
                return true;
            }
        } else if (currentWiredPart.getPartType().equals("ReflectorConfig")) {
            if (!usedReflectorIds.contains(currentWiredPart.getID())) {
                usedReflectorIds.add(currentWiredPart.getID());
                return true;
            }
        }
        return false;
    }

    public boolean idInRange(int from, int to) {
        return currentWiredPart.getID() >= from && currentWiredPart.getID() <= to;
    }
}
