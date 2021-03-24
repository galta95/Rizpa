package dataManager.jaxb;

import dataManager.jaxb.generated.RizpaStockExchangeDescriptor;
import engine.RSE;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SchemaBasedJAXB {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "dataManager.jaxb.generated";

    public static RSE loadXml(String path) {
        try {
            InputStream inputStream = new FileInputStream(new File(path));
            return new RSE(deserializeFrom(inputStream));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static RizpaStockExchangeDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }
}
