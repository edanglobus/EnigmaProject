package FileHandler;


import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class PositioningConfig {

    @XmlAttribute(name = "right")
    private String right;

    @XmlAttribute(name = "left")
    private String left;

    public PositioningConfig() {}

    public String getRight() {
        return right;
    }

    public String getLeft() {
        return left;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public void setLeft(String left) {
        this.left = left;
    }
}
