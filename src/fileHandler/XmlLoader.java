package fileHandler;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XmlLoader {

    private final String filePath;   // full file path from user
    private Document document;       // parsed XML

    public XmlLoader(String filePath) {
        this.filePath = filePath;
    }
    /**
     * Loads and parses the XML file into a DOM Document object.
     */
    public void load() throws Exception {

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("XML file does not exist at path: " + filePath);
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        document = builder.parse(file);
        document.getDocumentElement().normalize();
    }

    /**
     * @return The parsed XML Document object.
     * @throws IllegalStateException if load() was not called yet.
     */
    public Document getDocument() {
        if (document == null) {
            throw new IllegalStateException("XML was not loaded yet. Call load() first.");
        }
        return document;
    }
}
