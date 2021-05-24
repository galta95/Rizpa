package App;

import Bottom.BottomController;
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
    @FXML private ScrollPane bottomComponent;
    @FXML private BottomController bottomComponentController;


    private String xmlPath;
    StockMarketApi stockMarket;

    @FXML
    public void initialize() {
        if (headerComponentController != null && bottomComponentController != null ) {
            headerComponentController.setAppController(this);
            bottomComponentController.setAppController(this);

        }
    }

    public void setXmlPath(String path) throws JAXBException, FileNotFoundException {
        this.xmlPath = path;
        stockMarket = new StockMarket(xmlPath);
        changeMessage("XML Loaded");
    }

    /**
     * This method is used to change the app message
     * @param newMessage This is the message to show
     */
    public void changeMessage(String newMessage) {
        bottomComponentController.changeMessage(newMessage);
    }
}
