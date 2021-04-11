package engine;

import dataManager.jaxb.SchemaBasedJAXB;
import engine.stockMarket.RSE;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class Handler {
    public static RSE startApp(String xmlPath) throws FileNotFoundException, JAXBException {
        try {
            return SchemaBasedJAXB.loadXml(xmlPath);
        } catch(Exception e) {
            throw e;
        }
    }
}
