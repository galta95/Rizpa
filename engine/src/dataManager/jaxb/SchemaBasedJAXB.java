package dataManager.jaxb;

import dataManager.jaxb.generated.RizpaStockExchangeDescriptor;
import dataManager.jaxb.generated.RseStocks;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SchemaBasedJAXB {
    private final static String JAXB_XML_GAME_PACKAGE_NAME = "dataManager.jaxb.generated";
    private RizpaStockExchangeDescriptor exchangeDescriptor;
    public void loadXml(String[] args) {
        try {
            InputStream inputStream = new FileInputStream(new File("engine/src/resources/ex1-small.xml"));
            this.exchangeDescriptor = this.deserializeFrom(inputStream);
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private RizpaStockExchangeDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (RizpaStockExchangeDescriptor) u.unmarshal(in);
    }

}
