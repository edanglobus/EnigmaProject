package FileHandler;


import javax.xml.bind.annotation.*;
import java.util.List;
import java.util.function.Function;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReflectorConfig implements WiredPart<ReflectMappingConfig> {

    @XmlAttribute(name = "id")
    private String id;  // e.g. "I", "II"

    @XmlElement(name = "BTE-Reflect")
    private List<ReflectMappingConfig> mappings;

    public ReflectorConfig() {}

    public String getId() {
        return id;
    }

    public List<ReflectMappingConfig> getMappings() {
        return mappings;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMappings(List<ReflectMappingConfig> mappings) {
        this.mappings = mappings;
    }

    @Override
    public String getPartType() {
        return ReflectorConfig.class.getSimpleName();
    }

    @Override
    public int getID() {
        // Defining the Lambda
        Function<String, Integer> romanToInt = s -> switch (s) {
            case "I"   -> 1;
            case "II"  -> 2;
            case "III" -> 3;
            case "IV"  -> 4;
            case "V"   -> 5;
            default    -> -1;
        };
        return romanToInt.apply(this.id);
    }

    @Override
    public List<ReflectMappingConfig> getWires() {
        return this.mappings;
    }

}
