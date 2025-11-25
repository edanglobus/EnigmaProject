package jaxb;

// 3. The Positioning (inside Rotor)
@XmlAccessorType(XmlAccessType.FIELD)
public class Positioning {
    @XmlAttribute(name = "right")
    private String right;

    @XmlAttribute(name = "left")
    private String left;
}
