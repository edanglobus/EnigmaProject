package fileHandler;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class RotorConfig implements WiredPart {

    @XmlAttribute(name = "id")
    private int id;

    @XmlAttribute(name = "notch")
    private int notch;

    @XmlElement(name = "BTE-Positioning")
    private List<PositioningConfig> positions;

    public RotorConfig() {}

    public int getId() {
        return id;
    }

    public int getNotch() {
        return notch;
    }

    public List<PositioningConfig> getPositions() {
        return positions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNotch(int notch) {
        this.notch = notch;
    }

    public void setPositions(List<PositioningConfig> positions) {
        this.positions = positions;
    }

    @Override
    public String getPartType() {
        return ReflectorConfig.class.getSimpleName();
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public List<PositioningConfig> getWires() {
        return this.positions;
    }
}
