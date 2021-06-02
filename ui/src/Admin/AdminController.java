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
            buysList.append("\n");
            buysList.append(trade.getOrderType());
            buysList.append("\n");
            buysList.append(trade.getNumOfShares());
            buysList.append("\n");
            buysList.append(trade.getPrice());
            buysList.append("\n");
            buysList.append(trade.getUserName());
            buysList.append("\n");
        }

        for (DTOTrade trade: dtoTradeListSells) {
            sellsList.append(trade.getDate());
            sellsList.append("\n");
            sellsList.append(trade.getOrderType());
            sellsList.append("\n");
            sellsList.append(trade.getNumOfShares());
            sellsList.append("\n");
            sellsList.append(trade.getPrice());
            sellsList.append("\n");
            sellsList.append(trade.getUserName());
            sellsList.append("\n");
        }

        for (DTODeal deal: dtoDealList) {
            dealsHistory.append(deal.getDate());
            dealsHistory.append("\n");
            dealsHistory.append(deal.getOrderType());
            dealsHistory.append("\n");
            dealsHistory.append(deal.getNumOfShares());
            dealsHistory.append("\n");
            dealsHistory.append(deal.getPrice());
            dealsHistory.append("\n");
            dealsHistory.append("Deal executed between ");
            dealsHistory.append(deal.getProducer());
            dealsHistory.append(" and ");
            dealsHistory.append(deal.getConsumer());
            dealsHistory.append("\n");
        }

        buyLabel.setText(buysList.toString());
        sellLabel.setText(sellsList.toString());
        dealLabel.setText(dealsHistory.toString());
    }
}
