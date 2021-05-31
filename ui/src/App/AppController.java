package App;

import Admin.AdminController;
import Bottom.BottomController;
import Header.HeaderController;
import Members.SingleMemberController;
import engine.dto.DTOStocksSummary;
import engine.dto.DTOUser;
import engine.dto.DTOUsers;
import engine.stockMarket.StockMarket;
import engine.stockMarket.StockMarketApi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class AppController {
    @FXML
    private ScrollPane headerComponent;
    @FXML
    private HeaderController headerComponentController;
    @FXML
    private ScrollPane bottomComponent;
    @FXML
    private BottomController bottomComponentController;
    @FXML
    private TabPane membersTabPane;

    private String xmlPath;
    private boolean adminLoaded;
    private Tab adminTab;
    public StockMarketApi stockMarket;


    @FXML
    public void initialize() {
        if (headerComponentController != null && bottomComponentController != null) {
            headerComponentController.setAppController(this);
            bottomComponentController.setAppController(this);
        }
        adminLoaded = false;
    }

    public void setXmlPath(String path) throws JAXBException, FileNotFoundException {
        this.xmlPath = path;
        stockMarket = new StockMarket(xmlPath);
//        TODO: before adding - delete old members from the app (ui)
        addMembers();
        changeMessage("XML Loaded");
    }

    public void changeMessage(String newMessage) {
        bottomComponentController.changeMessage(newMessage);
    }

    public void addMembers() {
        try {
            DTOUsers users = this.stockMarket.getAllUsers();
            for (DTOUser user: users) {
                addMemberTab(user);
            }
        } catch (Error e) {
            changeMessage(e.getMessage());
        }
    }

    public void addAdminTab() {
        try {
            if (adminLoaded) {
                membersTabPane.getTabs().remove(adminTab);
                adminLoaded = false;
            } else {
                DTOStocksSummary stocksSummary = this.stockMarket.getStocksSummary();
                adminTab = new Tab("Admin");
                FXMLLoader loader = new FXMLLoader();
                URL url = this.getClass().getResource("/Admin/Admin.fxml");
                loader.setLocation(url);

                Node admin = loader.load();
                AdminController adminController = loader.getController();
                adminController.updateAdmin(stocksSummary);

                adminTab.setContent(admin);
                membersTabPane.getTabs().add(adminTab);
                adminLoaded = true;
            }
        } catch (Error | IOException e) {
            System.out.println(e.getMessage());
            changeMessage(e.getMessage());
        }
    }

    public void addMemberTab(DTOUser user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = this.getClass().getResource("/Members/singleMember.fxml");
            loader.setLocation(url);
            Tab tab = new Tab(user.getUserName());

            Node singleMember = loader.load();
            SingleMemberController singleMemberController = loader.getController();
            singleMemberController.updateMember(user.getTotalHoldings(), user.getHoldings());

            tab.setContent(singleMember);
            membersTabPane.getTabs().add(tab);
        } catch (Error | IOException e) {
            System.out.println(e.getMessage());
            changeMessage(e.getMessage());
        }
    }
}
