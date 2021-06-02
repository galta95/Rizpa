package Members;

import App.AppController;
import TradeForm.TradeFormController;
import engine.dto.DTOUser;
import engine.dto.DTOUsers;
import engine.stockMarket.StockMarketApi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class SingleMemberController {
    private MemberTotalHoldings memberTotalHoldingsListener;
    public String userName;
    private Map<String, Integer> holdings;
    private StockMarketApi stockMarketApi;
    private AppController appController;

    @FXML
    private TableView<StockHold> holdingTableView;

    @FXML
    private TableColumn<StockHold, String> stockTableColumn;

    @FXML
    private TableColumn<StockHold, Integer> quantityTableColumn;

    @FXML
    private Label holdingLabel;

    @FXML
    private Label totalHoldingLabel;

    @FXML
    private Button orderButton;

    @FXML
    public void initialize() {
        assert totalHoldingLabel != null;
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void updateMember(int totalHoldings, Map<String, Integer> holdings, String userName,
                             StockMarketApi stockMarketApi) {
        this.userName = userName;
        this.holdings = holdings;
        this.stockMarketApi = stockMarketApi;

        this.memberTotalHoldingsListener = new MemberTotalHoldings();
        totalHoldingLabel.textProperty().bind(new MemberTotalHoldingsBinding(memberTotalHoldingsListener));
        this.updateHoldingLabel(totalHoldings);

        ObservableList<StockHold> stockHoldObservableList = getStockHoldObservableList(holdings);

        stockTableColumn.setCellValueFactory(new PropertyValueFactory<StockHold, String>("stock"));
        quantityTableColumn.setCellValueFactory(new PropertyValueFactory<StockHold, Integer>("quantity"));
        holdingTableView.setItems(stockHoldObservableList);
    }

    public void updateHoldingLabel(int updatedHoldings) {
        this.memberTotalHoldingsListener.setTotalHoldings(updatedHoldings);
    }

    public void updateHoldingLabel() {
        DTOUsers users = stockMarketApi.getAllUsers();
        int holdings = 0;

        for (DTOUser user: users) {
            if (user.getUserName().equals(this.userName)) {
                holdings = user.getTotalStocksValue();
            }
        }
        this.updateHoldingLabel(holdings);
    }

    ObservableList<StockHold> getStockHoldObservableList(Map<String, Integer> holdings) {
        ObservableList<StockHold> items = FXCollections.observableArrayList();

        for (Map.Entry<String, Integer> entry : holdings.entrySet()) {
            items.add(new StockHold(entry.getKey(), entry.getValue()));
        }

        return items;
    }

    public void openTradeForm(ActionEvent event) throws IOException {
        URL url = getClass().getResource("/TradeForm/tradeForm.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        TradeFormController tradeFormController = loader.getController();
        tradeFormController.setAppController(appController);
        tradeFormController.setParent(this);
        tradeFormController.showMemberInformation(this.holdings, this.stockMarketApi);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Trade Form");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
}
