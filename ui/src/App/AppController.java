package App;

import Header.HeaderController;
import engine.stockMarket.StockMarket;
import engine.stockMarket.StockMarketApi;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public class AppController {

    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;

    private String xmlPath;
    StockMarketApi stockMarket;

    @FXML
    public void initialize() {
        if (headerComponentController != null) {
            headerComponentController.setAppController(this);
        }
    }

    public void setXmlPath(String path) throws JAXBException, FileNotFoundException {
        this.xmlPath = path;
        stockMarket = new StockMarket(xmlPath);
        System.out.println("XML loaded"); //this is log
    }
}
