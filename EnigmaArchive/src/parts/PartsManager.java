package parts;

import fileHandler.*;
import parts.reflector.ReflectorStorage;
import parts.routor.RoutorStorage;

public class PartsManager {

    EnigmaConfig EC
    PartsConfigValidator PCV;
    RoutorStorage RS;
    ReflectorStorage RFS;


    public PartsManager(EnigmaConfigMapper ECM) {
        this.EC = ECM.getEnigmaConfig();
        this.PCV = new PartsConfigValidator(EC.getAlphabet());
    }

    public boolean validatePartsConfig() {
        if (EC.getRotors() != null) {
            for (RotorConfig rotorConfig : EC.getRotors()) {
                if (PCV.runValidation(rotorConfig)) {
                    return false;
                }
            }
        }

        if (EC.getReflectors() != null) {
            for (ReflectorConfig reflectorConfig : EC.getReflectors()) {
                if (PCV.runValidation(reflectorConfig)) {
                    return false;
                }
            }
        }

        return true;
    }

    public int getPartCount(PartsStorage storage) {
        return storage.getPartCount();
    }
}
