package Bottom;

import javafx.beans.binding.StringBinding;

public class MessageBinding extends StringBinding {

    private Message theMessage;

    public MessageBinding(Message message) {
        this.theMessage = message;
        bind(message.messageProperty());
    }

    @Override
    protected String computeValue() {
        String message = theMessage.getMessage();
        return "Message: " + message;
    }
}
