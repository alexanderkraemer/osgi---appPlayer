package swt6.osgi.app.messages;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.osgi.app.IApp;


public class Messages implements IApp {
    private final Logger logger =  LoggerFactory.getLogger(Messages.class.getName());
    private Image icon;
    private VBox messageStack = new VBox();

    public Messages() {
        icon = new Image(this.getClass().getResourceAsStream("messages_logo.png"));
    }

    @Override
    public String getAppName() {
        return "Messages App";
    }

    @Override
    public Image getAppIcon() {
        return icon;
    }

    @Override
    public Node getNode() {
        VBox n = new VBox();
        n.getChildren().add(this.makeMessenger());

        n.setUserData(this.getAppName());
        return n;
    }

    private Node makeMessenger() {

        TextField messageBox = new TextField("");
        messageBox.setPadding(new Insets(5));

        Button send = new Button("Send");
        HBox messageField = new HBox(messageBox, send);

        send.setOnAction(e -> {
            String mess = messageBox.getText();
            messageStack.getChildren().add(new Text(mess));
            messageBox.setText("");
            logger.info("message sent with content: " + mess);
        });

        VBox box = new VBox(messageStack, messageField);
        box.setPadding(new Insets(20));


        return box;
    }
}
