package fileHandler;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "BTE-Enigma")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnigmaConfig {

    @XmlElement(name = "ABC")
    private String alphabet;

    @XmlElementWrapper(name = "BTE-Rotors")
    @XmlElement(name = "BTE-Rotor")
    private List<RotorConfig> rotors;

    @XmlElementWrapper(name = "BTE-Reflectors")
    @XmlElement(name = "BTE-Reflector")
    private List<ReflectorConfig> reflectors;

    // JAXB needs a no-arg constructor
    public EnigmaConfig() {}

    public String getAlphabet() {
        return alphabet != null ? alphabet.trim() : null;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public List<RotorConfig> getRotors() {
        return rotors;
    }

    public void setRotors(List<RotorConfig> rotors) {
        this.rotors = rotors;
    }

    public List<ReflectorConfig> getReflectors() {
        return reflectors;
    }

    public void setReflectors(List<ReflectorConfig> reflectors) {
        this.reflectors = reflectors;
    }




}

