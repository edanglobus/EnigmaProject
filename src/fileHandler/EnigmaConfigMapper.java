package fileHandler;

import Service.Router;
import Service.Wiring;

import java.util.ArrayList;
import java.util.List;

public class EnigmaConfigMapper {

    private final EnigmaConfig config;

    public EnigmaConfigMapper(EnigmaConfig config) {
        this.config = config;
    }

    /**
     * Builds a list of Router objects from the JAXB config.
     * Each RotorConfig from the XML is converted into a Router + Wiring.
     */
    public List<Router> buildRouters() {

        String alphabet = config.getAlphabet();
        List<Router> result = new ArrayList<>();

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
            Wiring wiring = new Wiring();
            wiring.setWiring(rightCol.toString(), leftCol.toString(), alphabet);

            // 3. Build Router object, using your Router class
            Router router = new Router(id, notch, alphabet);
            router.setWiring(wiring);     // make sure setWiring is public
            router.setPosition(0);        // default position (can be changed later)
            // router.setRingSetting(0);  // if you have ring setting

            // 4. Add to the result list
            result.add(router);
        }

        return result;
    }
}
