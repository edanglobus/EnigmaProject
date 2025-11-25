package jaxb;

import java.util.List;


@XmlRootElement(name = "BTE-Enigma")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnigmaDescriptor {
    @XmlElement(name = "ABC")
    private String machineABC;

    // Using Wrapper to handle <BTE-Rotors> holding a list of <BTE-Rotor>
    @XmlElementWrapper(name = "BTE-Rotors")
    @XmlElement(name = "BTE-Rotor")
    private List<RotorDescriptor> rotors;

    @XmlElementWrapper(name = "BTE-Reflectors")
    @XmlElement(name = "BTE-Reflector")
    private List<ReflectorDescriptor> reflectors;

    // Standard Getters/Setters...
    public List<RotorDescriptor> getRotors() { return rotors; }
    public List<ReflectorDescriptor> getReflectors() { return reflectors; }
    public String getMachineABC() { return machineABC; }
}
