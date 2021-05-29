package Members;

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

    public void updateMember(int totalHoldings, Map<String, Integer> holdings) {
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
        Stage stage = new Stage();

        URL url = getClass().getResource("/TradeForm/tradeForm.fxml");
        Parent root = FXMLLoader.load(url);
        stage.setTitle("Trade Form");

        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(orderButton.getScene().getWindow());
        stage.showAndWait();
    }
}
