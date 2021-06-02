package Header;

import App.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;

public class HeaderController {

    private AppController appController;
    final FileChooser fc = new FileChooser();
    ObservableList<String> animationsChoiceBoxList = FXCollections.observableArrayList("on", "off");

    @FXML
    private Button adminBtn;

    @FXML
    private ChoiceBox animationsChoiceBox;

    @FXML
    public void initialize() {
        animationsChoiceBox.setItems(animationsChoiceBoxList);
    }

    @FXML
    void adminClicked(ActionEvent event) {
        this.appController.addAdminTab();
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    void loadXmlClicked(ActionEvent event) {
        File file = fc.showOpenDialog(null);
        if (file == null) {
            appController.changeMessage("Load XML first");
        } else {
            appController.setXmlPath(file.getPath());
        }
    }

    @FXML
    void themeClicked(MouseEvent event) {

    }

    public void changeAdminButtonTxt(String text) {
        adminBtn.setText(text);
    }
}
