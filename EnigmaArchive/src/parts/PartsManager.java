package parts;


import FileHandler.*;
import parts.reflector.ReflectorStorage;
import parts.routor.RotorStorage;

public class PartsManager {
    EnigmaJaxbLoader supplyLoader;
    EnigmaConfig EC;
    EnigmaConfigMapper ECM;
    PartsConfigValidator PCV;
    RotorStorage RS;
    ReflectorStorage RFS;


    public PartsManager(EnigmaJaxbLoader loader) {
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

        return true;
    }

    private void buildStorages() {
        boolean canBuild = validatePartsConfig() && EC.validateWires();
        if (canBuild) {
            RS = new RotorStorage(ECM.buildRouters());
            RFS = new ReflectorStorage(ECM.buildReflectors());
        } else {
            throw new IllegalStateException("Cannot build storages due to invalid configuration.");
        }
    }

    private void loadSupplyFromXML(String path) throws Exception {
        EC = supplyLoader.loadFromFile(path);
        ECM = new EnigmaConfigMapper(EC);
        PCV = new PartsConfigValidator();
    }

    public void loadSupplyXMLCheckAndBuildStorages(String path) throws Exception {
        loadSupplyFromXML(path);
        buildStorages();
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

}
