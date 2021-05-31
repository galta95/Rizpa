package Header;

import App.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileNotFoundException;

public class HeaderController {

    private AppController appController;
    final FileChooser fc = new FileChooser();
    ObservableList<String> animationsChoiceBoxList = FXCollections.observableArrayList("on", "off");

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
    void loadXmlClicked(ActionEvent event) throws JAXBException, FileNotFoundException {
        File file = fc.showOpenDialog(null);
        appController.setXmlPath(file.getPath());
    }

    @FXML
    void themeClicked(MouseEvent event) {

    }
}
