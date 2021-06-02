package TradeForm;

import App.AppController;
import Members.SingleMemberController;
import engine.dto.*;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        gateTextArea.setDisable(true);
        typeChoiceBox.setOnAction(actionEvent -> {
            if (typeChoiceBox.getValue().equals("LMT")) {
                gateTextArea.setDisable(false);
            } else {
                gateTextArea.setDisable(true);
                gateTextArea.clear();
            }
        });
    }

    public void closeForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        appController.changeMessage("ORDER CANCELED");
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
        Stage stage = (Stage) doneButton.getScene().getWindow();
        DTOOrder dtoOrder = null;
        String symbol = stocksChoiceBox.getValue();
        String date = DateTimeFormatter.ofPattern("HH:mm:ss:SSS").format(LocalDateTime.now());
        String orderType = typeChoiceBox.getValue();
        DTOUserPotentialStockQuantity potentialQuantity = this.stockMarketApi.getUserStockPotentialQuantity(parent.userName, symbol);
        int numOfShares = 0;
        int price = 0;


        try {
            if(stocksChoiceBox.getValue().isEmpty() || (gateTextArea.getText().isEmpty() && !gateTextArea.isDisable()) ||
                    quantityTextField.getText().isEmpty() || typeChoiceBox.getValue().isEmpty()) {
                throw new Error();
            }
        }
        catch (NullPointerException | Error error) {
            appController.changeMessage("ORDER FAILED: All fields have to be full");
            stage.close();
            return;
        }

        try {
            numOfShares = Integer.parseInt(quantityTextField.getText());
        } catch (NumberFormatException e) {
            appController.changeMessage("ORDER FAILED: Gate Limit and Quantity must be a number");
            stage.close();
            return;
        }

        if (numOfShares < 1) {
            appController.changeMessage("ORDER FAILED: Quantity must be more than 0");
            stage.close();
            return;
        }

        if (symbol.equals("")) {
            appController.changeMessage("ORDER FAILED: Must choose stock");
            stage.close();
            return;
        }

        if (orderType.equals("")) {
            appController.changeMessage("ORDER FAILED: Must choose order type");
            stage.close();
            return;
        }

        switch (orderType) {
            case "MKT": {
                if (Direction.getSelectedToggle().equals(buyRadioButton)) {
                    dtoOrder = this.stockMarketApi.executeMktOrderBuy(symbol, date, numOfShares, 0, parent.userName);
                } else if (Direction.getSelectedToggle().equals(sellRadioButton)) {
                    if (potentialQuantity.getPotentialQuantity() < numOfShares) {
                        appController.changeMessage("ORDER FAILED: Dont have enough shares to sell");
                        stage.close();
                        return;
                    }
                    dtoOrder = this.stockMarketApi.executeMktOrderSell(symbol, date, numOfShares, 0, parent.userName);
                }
                break;
            }
            case "LMT": {
                try {
                    price = Integer.parseInt(gateTextArea.getText());
                } catch (NumberFormatException e) {
                    appController.changeMessage("ORDER FAILED: Gate Limit and Quantity must be a number");
                    stage.close();
                    return;
                }
                if (price < 1) {
                    appController.changeMessage("ORDER FAILED: Price must be more than 0");
                    stage.close();
                    return;
                }
                if (Direction.getSelectedToggle().equals(buyRadioButton)) {
                    dtoOrder = this.stockMarketApi.executeLmtOrderBuy(symbol, date, numOfShares, price, parent.userName);
                } else if (Direction.getSelectedToggle().equals(sellRadioButton)) {
                    if (potentialQuantity.getPotentialQuantity() < numOfShares) {
                        appController.changeMessage("ORDER FAILED: Dont have enough shares to sell");
                        stage.close();
                        return;
                    }
                    dtoOrder = this.stockMarketApi.executeLmtOrderSell(symbol, date, numOfShares, price, parent.userName);
                }
                break;
            }
            default:
                break;
        }

        assert dtoOrder != null;
        if (!dtoOrder.getSuccessfulOrder()) {
            appController.changeMessage("ORDER FAILED: Price must be more than 0");
            stage.close();
            return;
        }

        parent.updateHoldingLabel();
        this.appController.addMembers(); //Remove this

        if (dtoOrder.getNumberOfSharesInsertedToList() == 0) {

            appController.changeMessage("ORDER SUCCEEDED: Completed in " + dtoOrder.getDealsCounter() + " deals");
        } else {
            appController.changeMessage("ORDER SUCCEEDED: There were " + dtoOrder.getDealsCounter() +
                    " deals. " + dtoOrder.getNumberOfSharesInsertedToList() + " shares inserted to waiting list");
        }
        stage.close();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void setParent(SingleMemberController singleMemberController) {
        this.parent = singleMemberController;
    }
}
