package App;

import Admin.AdminController;
import Bottom.BottomController;
import Header.HeaderController;
import Members.SingleMemberController;
import ProgressTask.ProgressTaskController;
import engine.dto.DTOStocksSummary;
import engine.dto.DTOUser;
import engine.dto.DTOUsers;
import engine.stockMarket.StockMarket;
import engine.stockMarket.StockMarketApi;
import errors.ConstraintError;
import errors.FileFormatNotSupportedError;
import errors.NotUpperCaseError;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Optional;

public class AppController {
    @FXML
    private ScrollPane headerComponent;
    @FXML
    public HeaderController headerComponentController;
    @FXML
    public ScrollPane bottomComponent;
    @FXML
    public BottomController bottomComponentController;
    @FXML
    public TabPane membersTabPane;

    private String xmlPath;
    private Tab adminTab;
    public StockMarketApi stockMarket;
    private boolean adminIsOpen;

    @FXML
    public void initialize() {
        if (headerComponentController != null && bottomComponentController != null) {
            headerComponentController.setAppController(this);
            bottomComponentController.setAppController(this);
        }
        adminIsOpen = false;
    }

    public void setXmlPath(String path) {
        try {
            openPbar();
            this.xmlPath = path;
            stockMarket = new StockMarket(xmlPath);
            addMembers();
            changeMessage("XML Loaded");
        } catch (ConstraintError | InputMismatchException | NotUpperCaseError | FileFormatNotSupportedError e) {
            changeMessage("Error: failed to load xml - " + e.getMessage());
        }
        catch (Error | NullPointerException | JAXBException | IOException | InterruptedException e) {
            changeMessage("Error: failed to load xml");
        }
    }

    public void changeMessage(String newMessage) {
        bottomComponentController.changeMessage(newMessage);
    }

    public void addMembers() {
        try {
            if (membersTabPane.getTabs().size() > 0) {
                if (adminIsOpen) {
                    addAdminTab();
                    adminIsOpen = false;
                }
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
                adminIsOpen = false;
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
                adminIsOpen = true;
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

    public void openPbar() throws IOException, InterruptedException {
        URL url = getClass().getResource("/ProgressTask/progressTask.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        ProgressTaskController progressTaskController = loader.getController();

        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        progressTaskController.startProgress();
    }
}
