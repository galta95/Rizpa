package Admin;

import engine.dto.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    private DTOStocksSummary stocksSummary;

    @FXML
    private ChoiceBox<String> stockChoiceBox;

    @FXML
    private ScrollBar buyScrollPane;

    @FXML
    private Label buyLabel;

    @FXML
    private Label sellLabel;

    @FXML
    private Label dealLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void updateAdmin(DTOStocksSummary stocksSummary) {
        this.stocksSummary = stocksSummary;
        ObservableList<String> stocksItems = stockChoiceBox.getItems();
        for (DTOStockSummary stockSummary: stocksSummary) {
            stocksItems.add(stockSummary.getSymbol());
        }
        stockChoiceBox.setOnAction(this::getStockSummery);
    }

    public void getStockSummery(ActionEvent event){
        StringBuilder buysList = new StringBuilder();
        StringBuilder sellsList = new StringBuilder();
        StringBuilder dealsHistory = new StringBuilder();

        Optional<DTOStockSummary> optionalDTOStockSummary = this.stocksSummary.getStocksSummaryList()
                .stream().filter(stock -> stock.getSymbol().equals(stockChoiceBox.getValue())).findFirst();

        if (!optionalDTOStockSummary.isPresent()) {
            buyLabel.setText("stockSummery");
            throw new Error("cannot find symbol");
        }

        DTOStockSummary stock = optionalDTOStockSummary.get();

        List<DTOTrade> dtoTradeListBuys = stock.getBuysList().getTrades();
        List<DTOTrade> dtoTradeListSells = stock.getSellsList().getTrades();
        List<DTODeal> dtoDealList = stock.getDealsList().getDeals();

        for (DTOTrade trade: dtoTradeListBuys) {
            buysList.append(trade.getDate());
            buysList.append(trade.getOrderType());
            buysList.append(trade.getNumOfShares());
            buysList.append(trade.getPrice());
            // stockSummery.append(trade.); GET USER NAME
        }

        for (DTOTrade trade: dtoTradeListSells) {
            sellsList.append(trade.getDate());
            sellsList.append(trade.getOrderType());
            sellsList.append(trade.getNumOfShares());
            sellsList.append(trade.getPrice());
            // stockSummery.append(trade.); GET USER NAME
        }

        for (DTODeal deal: dtoDealList) {
            sellsList.append(deal.getDate());
            sellsList.append(deal.getOrderType());
            sellsList.append(deal.getNumOfShares());
            sellsList.append(deal.getPrice());
            // stockSummery.append(trade.); GET buyer name and seller name
        }

        buyLabel.setText(buysList.toString());
        sellLabel.setText(sellsList.toString());
        dealLabel.setText(sellsList.toString());
    }
}
