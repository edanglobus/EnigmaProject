package parts;


import FileHandler.*;
import Service.Reflector;
import Service.Rotor;
import Service.Utils;
import parts.reflector.ReflectorStorage;
import parts.routor.RotorStorage;

import javax.lang.model.element.NestingKind;
import java.util.List;

public class StorageManager {
    private EnigmaJaxbLoader supplyLoader;
    private EnigmaConfig EC;
    private EnigmaConfigMapper ECM;
    private PartsConfigValidator PCV;
    private RotorStorage RS;
    private ReflectorStorage RFS;
    private String ABC;
    private List<Character>  originalPosition;
    private boolean ValidSupply;


    public StorageManager(EnigmaJaxbLoader loader) {
       this.supplyLoader = loader;
    }

    private boolean validatePartsConfig() {
        if (EC.getRotors() != null) {
            for (RotorConfig rotorConfig : EC.getRotors()) {
                if (!PCV.runRotorValidation(rotorConfig)) {
                    return false;
                }
            }
        }

        if (EC.getReflectors() != null) {
            for (ReflectorConfig reflectorConfig : EC.getReflectors()) {
                if (!PCV.runReflectorValidation(reflectorConfig)) {
                    return false;
                }
            }
        }

        return PCV.rotorIdsHasNoHoles() && PCV.reflectorIdsHasNoHoles();
    }

    private boolean validateABCLength() {
        if (EC.getAlphabet() != null && Utils.normalize(ABC.length(), 2) != 0) {
            throw new IllegalArgumentException("Alphabet length must be even.");
        }
        return true;
    }

    private boolean validateSupply() {
        return validateABCLength() && validatePartsConfig() && EC.validateWires();
    }


    private void buildStorages() {
        try {
            RS = new RotorStorage(ECM.buildRouters());
            RFS = new ReflectorStorage(ECM.buildReflectors());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to build storages: " + e.getMessage());
        }
    }

    private void loadSupplyFromXML(String path) throws Exception {
        EC = supplyLoader.loadFromFile(path);
        ECM = new EnigmaConfigMapper(EC);
        PCV = new PartsConfigValidator();
        ABC = EC.getAlphabet();
    }

    public void loadSupplyXMLCheckAndBuildStorages(String path) throws Exception {
        loadSupplyFromXML(path);
        this.ValidSupply = validateSupply();
        buildStorages();
    }

     private boolean IsInSupplyRotorID(int id) {
         return RS.containsRotor(id);
     }

     private boolean IsInSupplyReflectorID(String id) {
         return RFS.containsReflector(id);
     }

     public Reflector optionalGetReflectorByID(String id) {
            if (!IsInSupplyReflectorID(id)) {
                throw (new IllegalArgumentException("Reflector ID " + id + " not found in storage."));
            }
            return RFS.getReflectorByID(id);
        }

     public Rotor optionalGetRotorByID(int id) {
        if (!IsInSupplyRotorID(id)) {
            throw (new IllegalArgumentException("Rotor ID " + id + " not found in storage."));
        }
         return RS.getRotorByID(id);
     }

    public String getABC() {
        return ABC;
    }

    public void showAvailableReflectors() {
        RFS.printAvailableReflectors();
    }

    public void printStorages() {
        System.out.print(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PartsManager{");
        sb.append("RotorStorage=");
        sb.append(RS == null ? "null" : RS.toString());
        sb.append(", ReflectorStorage=");
        sb.append(RFS == null ? "null" : RFS.toString());
        sb.append('}');
        return sb.toString();
    }

    public List<Integer> getIndexInABC(List<Character> lst) {
        return lst.stream()
                .map(ch -> {
                    int index = ABC.indexOf(ch);
                    if (index == -1) {
                        throw new IllegalArgumentException("Character '" + ch + "' not found in alphabet.");
                    }
                    return index;
                })
                .toList();
    }

    public int getRotorsAmount() {
        return RS == null ? 0 : RS.getPartCount();
    }

    public int getReflectorsAmount() {
        return RFS == null ? 0 : RFS.getPartCount();
    }

    public void setOriginalPosition(List<Character> originalPosition) {
        this.originalPosition = originalPosition;
    }

    public List<Character> getOriginalPosition() {
        return originalPosition;
    }

    public void resetStorages() {
        this.RS = null;
        this.RFS = null;
        this.EC = null;
        this.ECM = null;
        this.ABC = null;
        this.originalPosition = null;
        this.ValidSupply = false;
        this.PCV.reset();
    }
}
