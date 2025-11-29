package FileHandler;


import javax.xml.bind.*;
import java.io.File;

public class EnigmaJaxbLoader {

    /**
     * Loads an EnigmaConfig from an XML file path using JAXB.
     *
     * @param filePath full path to XML file
     * @return populated EnigmaConfig
     * @throws JAXBException if JAXB fails to parse
     */
    public EnigmaConfig loadFromFile(String filePath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EnigmaConfig.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        File file = new File(filePath);
        return (EnigmaConfig) unmarshaller.unmarshal(file);
    }
}
