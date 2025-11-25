package jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        try {
            File file = new File("ex1-sanity-small.xml");

            // 1. Create Context for the Root Class

            JAXBContext jaxbContext = JAXBContext.newInstance(EnigmaDescriptor.class);

            // 2. Create Unmarshaller
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // 3. Read file into Object
            EnigmaDescriptor enigma = (EnigmaDescriptor) jaxbUnmarshaller.unmarshal(file);

            // 4. Print Result to verify
            System.out.println("ABC: " + enigma.getMachineABC());
            System.out.println("Rotors count: " + enigma.getRotors().size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
