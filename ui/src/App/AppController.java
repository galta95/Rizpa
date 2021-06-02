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
import java.util.Optional;

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
    private Tab adminTab;
    public StockMarketApi stockMarket;

    @FXML
    public void initialize() {
        if (headerComponentController != null && bottomComponentController != null) {
            headerComponentController.setAppController(this);
            bottomComponentController.setAppController(this);
        }
    }

    public void setXmlPath(String path) {
        try {
            this.xmlPath = path;
            stockMarket = new StockMarket(xmlPath);
            addMembers();
            changeMessage("XML Loaded");
        } catch (JAXBException | FileNotFoundException e) {
            changeMessage("Try to load again");
        }
    }

    public void changeMessage(String newMessage) {
        bottomComponentController.changeMessage(newMessage);
    }

    public void addMembers() {
        try {
            if (membersTabPane.getTabs().size() > 0) {
                this.membersTabPane.getTabs().removeAll(membersTabPane.getTabs());
            }
            DTOUsers users = this.stockMarket.getAllUsers();
            for (DTOUser user : users) {
                SingleMemberController singleMember = addMemberTab(user);
                singleMember.setAppController(this);
            }
        } catch (Error e) {
            changeMessage(e.getMessage());
        }
    }

    public void addAdminTab() {
        try {
            Optional<Tab> optionalAdminTab = membersTabPane.getTabs().stream()
                    .filter((tab) -> tab.getText().equals("Admin"))
                    .findFirst();

            if (membersTabPane.getTabs().size() == 0) {
                throw new Error("Must load xml first!");
            }

            if (optionalAdminTab.isPresent()) {
                membersTabPane.getTabs().remove(adminTab);
                headerComponentController.changeAdminButtonTxt("Admin");
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
                headerComponentController.changeAdminButtonTxt("close");
            }
        } catch (Error | IOException e) {
            changeMessage(e.getMessage());
        }
    }

    public SingleMemberController addMemberTab(DTOUser user) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = this.getClass().getResource("/Members/singleMember.fxml");
            loader.setLocation(url);
            Tab tab = new Tab(user.getUserName());

            Node singleMember = loader.load();
            SingleMemberController singleMemberController = loader.getController();
            singleMemberController.updateMember(user.getTotalStocksValue(), user.getHoldings(), user.getUserName(),
                    this.stockMarket);

            tab.setContent(singleMember);
            membersTabPane.getTabs().add(tab);

            return singleMemberController;
        } catch (Error | IOException e) {
            changeMessage(e.getMessage());
            return null;
        }
    }
}
