package Bottom;

import App.AppController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BottomController {

    private AppController appController;
    private Message messageListener;
    @FXML private Label messageLabel;
    @FXML private Label messageOutputLabel;

    @FXML
    public void initialize() {
        assert messageLabel != null : "fx:id=\"messageLabel\" was not injected: check your FXML file 'Alert.fxml'.";
        assert messageOutputLabel != null : "fx:id=\"messageOutputLabel\" was not injected: check your FXML file 'Alert.fxml'.";

        this.messageListener = new Message();

        messageLabel.textProperty().bind(new MessageBinding(messageListener));
        messageListener.setMessage("Welcome to Rizpa Stock Exchange app!");
    }

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    public void changeMessage(String msg) {
        this.messageListener.setMessage(msg);
    }
}
