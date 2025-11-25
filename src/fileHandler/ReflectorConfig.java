package fileHandler;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ReflectorConfig {

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
}
