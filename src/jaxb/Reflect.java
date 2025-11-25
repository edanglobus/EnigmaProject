package jaxb;

// 5. The Reflect pairs (inside Reflector)
@XmlAccessorType(XmlAccessType.FIELD)
public class Reflect {
    @XmlAttribute(name = "input")
    private int input;

    @XmlAttribute(name = "output")
    private int output;
}
