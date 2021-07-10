package dataManager;

import errors.FileFormatNotSupportedError;
import dataManager.generated.RizpaStockExchangeDescriptor;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SchemaBasedJAXB {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "dataManager.generated";

    public static RizpaStockExchangeDescriptor loadXml(String path) throws FileNotFoundException, JAXBException {
        try {
            if (!path.endsWith(".xml")) {
                throw new FileFormatNotSupportedError("XML");
            }
            InputStream inputStream = new FileInputStream(new File(path));
            return deserializeFrom(inputStream);
        } catch (JAXBException | FileNotFoundException | FileFormatNotSupportedError e) {
            throw e;
        }
    }

    private static RizpaStockExchangeDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }
}
