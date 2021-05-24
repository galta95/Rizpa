package Bottom;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Message {

    final private StringProperty message = new SimpleStringProperty();

    public StringProperty messageProperty() {
        return message;
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String newMessage) {
        message.set(newMessage);
    }
}
