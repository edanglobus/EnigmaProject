package jaxb;

import java.util.List;

// 2. The Rotor
// @XmlAccessorType(XmlAccessType.FIELD)
public class RotorDescriptor {
    @XmlAttribute(name = "id")
    private int id;

    @XmlAttribute(name = "notch")
    private int notch;

    @XmlElement(name = "BTE-Positioning")
    private List<Positioning> positions;

    // Getters...
}

