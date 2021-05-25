package Members;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class SingleMemberController {
    private Member memberListener;

    @FXML
    private TableView<?> holdingTableView;

    @FXML
    private TableColumn<?, ?> stockTableColumn;

    @FXML
    private TableColumn<?, ?> quantityTableColumn;

    @FXML
    private Label holdingLabel;

    @FXML
    private Label totalHoldingLabel;

    @FXML
    private Button orderButton;

    @FXML
    public void initialize() {
        assert totalHoldingLabel != null;
        holdingTableView = new TableView();

    }

    public void creatMember(int holdingLabel, Map<String, Integer> holdings) {
        this.memberListener = new Member();
        totalHoldingLabel.textProperty().bind(new MemberBinding(memberListener));
        
        /*holdingTableView.setEditable(true);

        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn emailCol = new TableColumn("Email");

        holdingTableView.getColumns().addAll(lastNameCol, emailCol);*/

        this.setHoldingLabel(holdingLabel);
    }

    public void setHoldingLabel(int holdingLabel) {
        this.memberListener.setTotalHoldings(holdingLabel);
    }
}
