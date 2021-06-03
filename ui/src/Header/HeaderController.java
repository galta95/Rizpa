package Header;

import App.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class HeaderController {

    private AppController appController;
    final FileChooser fc = new FileChooser();
    ObservableList<String> themeChoiceBoxList = FXCollections.observableArrayList("LightMode", "DarkMode", "Default");

    @FXML
    private Button adminBtn;

    @FXML
    private ChoiceBox<String> theme;

    @FXML
    public void initialize() {
        theme.getItems().setAll(themeChoiceBoxList);
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

    public void setTheme(ActionEvent actionEvent) {
        Scene scene = (Scene) theme.getScene();
        if (theme.getValue().equals("DarkMode")) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/App/darkmode.css").toExternalForm());
        } else if (theme.getValue().equals("LightMode")) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/App/lightmode.css").toExternalForm());
        } else {
            scene.getStylesheets().clear();
        }
    }
}
