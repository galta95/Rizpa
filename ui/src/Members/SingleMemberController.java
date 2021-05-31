package Members;

import TradeForm.TradeFormController;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class SingleMemberController {
    private MemberTotalHoldings memberTotalHoldingsListener;
    private String userName;
    private Map<String, Integer> holdings;
    private StockMarketApi stockMarketApi;

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
        tradeFormController.showMemberInformation(this.holdings, this.stockMarketApi);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Trade Form");
        stage.show();
    }
}
