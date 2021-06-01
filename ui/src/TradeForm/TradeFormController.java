package TradeForm;

import App.AppController;
import Members.SingleMemberController;
import engine.dto.DTOStock;
import engine.dto.DTOStocks;
import engine.stockMarket.StockMarketApi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class TradeFormController implements Initializable {
    private List<String> allStocks;
    private List<String> userStocks;
    private StockMarketApi stockMarketApi;
    private AppController appController;
    private SingleMemberController parent;

    @FXML
    private Button doneButton;

    @FXML
    private Button cancelButton;

    @FXML
    private RadioButton buyRadioButton;

    @FXML
    private RadioButton sellRadioButton;

    @FXML
    private ToggleGroup Direction;

    @FXML
    private TextField gateTextArea;

    @FXML
    private TextField quantityTextField;

    @FXML
    private ChoiceBox<String> stocksChoiceBox;

    @FXML
    private ChoiceBox<String> typeChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> typeChoiceBoxList = typeChoiceBox.getItems();
        typeChoiceBoxList.addAll("MKT", "LMT");
        typeChoiceBox.setItems(typeChoiceBoxList);
    }

    public void closeForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setStocks(ActionEvent actionEvent) {
        ObservableList<String> stocksChoiceBoxList = FXCollections.observableArrayList();

        if (Direction.getSelectedToggle().equals(buyRadioButton)) {
            stocksChoiceBoxList.addAll(allStocks);
            stocksChoiceBox.setItems(stocksChoiceBoxList);
        } else if (Direction.getSelectedToggle().equals(sellRadioButton)) {
            stocksChoiceBoxList.addAll(userStocks);
            stocksChoiceBox.setItems(stocksChoiceBoxList);
        }
    }

    public void showMemberInformation(Map<String, Integer> holdings, StockMarketApi stockMarketApi) {
        this.allStocks = new LinkedList<>();
        this.userStocks = new LinkedList<>();
        this.stockMarketApi = stockMarketApi;

        holdings.forEach((String symbol, Integer quantity) -> {
            userStocks.add(symbol);
        });
        for (DTOStock stock : stockMarketApi.getAllStocks()) {
            this.allStocks.add(stock.getSymbol());
        }
    }

    public void doneOnClick() {
        String symbol = stocksChoiceBox.getValue();
        String date = new Date().toString();
        String orderType = typeChoiceBox.getValue();
        int numOfShares;
        int price;

        try {
            numOfShares = Integer.parseInt(quantityTextField.getText());
            price = Integer.parseInt(gateTextArea.getText());
        } catch (NumberFormatException e) {
            throw new Error("Gate Limit and Quantity must be a number");
        }

        if (symbol.equals("")) {
            throw new Error("Must choose stock");
        }

        if (orderType.equals("")) {
            throw new Error("Must choose order type");
        }

        switch (orderType) {
            case "MKT": {
                if (Direction.getSelectedToggle().equals(buyRadioButton)) {
                    this.stockMarketApi.executeMktOrderBuy(symbol, date, numOfShares, price);
                } else if (Direction.getSelectedToggle().equals(sellRadioButton)) {
                    this.stockMarketApi.executeMktOrderBuy(symbol, date, numOfShares, price);
                } else {
                    System.out.println("ERROR");
                }
                break;
            }
            case "LMT": {
                if (Direction.getSelectedToggle().equals(buyRadioButton)) {
                    this.stockMarketApi.executeLmtOrderBuy(symbol, date, numOfShares, price);
                } else if (Direction.getSelectedToggle().equals(sellRadioButton)) {
                    this.stockMarketApi.executeLmtOrderSell(symbol, date, numOfShares, price);
                } else {
                    System.out.println("ERROR");
                }
                break;
            }
            default:
                break;
        }

        Stage stage = (Stage) doneButton.getScene().getWindow();
        parent.updateHoldingLabel();
        //this.appController.addMembers();
        stage.close();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setParent(SingleMemberController singleMemberController) {
        this.parent = singleMemberController;
    }
}
