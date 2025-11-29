package FileHandler;

import Service.Rotor;
import WiringCables.WiringRotor;
import java.util.HashMap;
import java.util.Map;

public class EnigmaConfigMapper {

    private final EnigmaConfig config;

    public EnigmaConfigMapper(EnigmaConfig config) {
        this.config = config;
    }

    /**
     * Builds a list of Router objects from the JAXB config.
     * Each RotorConfig from the XML is converted into a Router + Wiring.
     */
    public Map<Integer, Rotor> buildRouters() {

        String alphabet = config.getAlphabet();
        Map<Integer, Rotor> rotors = new HashMap<>();

        for (RotorConfig rotorCfg : config.getRotors()) {

            int id = rotorCfg.getId();
            int notch = rotorCfg.getNotch();

            // 1. Build right and left columns as strings from the positioning list
            StringBuilder rightCol = new StringBuilder();
            StringBuilder leftCol  = new StringBuilder();

            for (PositioningConfig pos : rotorCfg.getPositions()) {
                rightCol.append(pos.getRight());
                leftCol.append(pos.getLeft());
            }

            // 2. Build Wiring object according to the columns
            WiringRotor wiringRotor = new WiringRotor(rightCol.toString(), leftCol.toString(), alphabet);

            // 3. Build Router object, using your Router class
            Rotor rotor = new Rotor(id, notch, alphabet.length(), wiringRotor);
            // router.setRingSetting(0);  // if you have ring setting

            // 4. Add to the result list
            rotors.put(id, rotor);
        }

        return rotors;
    }



    public EnigmaConfig getEnigmaConfig() {
        return this.config;
    }
}
