package TradeForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class TradeFormController {

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
    private ChoiceBox<?> typeChoiceBox;

    @FXML
    public void initialize() {

    }

    public void closeForm(ActionEvent event) throws IOException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setStocks(ActionEvent actionEvent) {
        ObservableList<String> stocksChoiceBoxList = FXCollections.observableArrayList();

        if(Direction.getSelectedToggle().equals(buyRadioButton)) {
            stocksChoiceBoxList.add("Gal is");
            stocksChoiceBox.setItems(stocksChoiceBoxList);
        } else if (Direction.getSelectedToggle().equals(sellRadioButton)) {
            stocksChoiceBoxList.add("Efes");
            stocksChoiceBox.setItems(stocksChoiceBoxList);
        }
    }
}
