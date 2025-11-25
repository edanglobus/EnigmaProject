package jaxb;

import java.util.List;

// 4. The Reflector
@XmlAccessorType(XmlAccessType.FIELD)
public class Reflector {

        @XmlAttribute(name = "id")
        private String id; // Using String because ID is Roman numeral "I", "II"

        @XmlElement(name = "BTE-Reflect")
        private List<Reflect> reflects;
}

