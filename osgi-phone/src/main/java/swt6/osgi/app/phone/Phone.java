package swt6.osgi.app.phone;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.osgi.app.IApp;



public class Phone implements IApp{
    private final Logger logger =  LoggerFactory.getLogger(Phone.class.getName());


    private Image icon;
    private String currentNumber = "";

    public Phone() {
        icon = new Image(this.getClass().getResourceAsStream("phone_logo.png"));
    }

    @Override
    public String getAppName() {
        return "Phone App";
    }

    @Override
    public Image getAppIcon() {
        return icon;
    }

    @Override
    public Node getNode() {
        VBox n = new VBox();
        n.getChildren().add(this.makeNumpad());

        n.setUserData(this.getAppName());
        return n;
    }


    private Node makeNumpad(){

        TextField tf = new TextField(currentNumber);
        tf.setDisable(true);


        String[] keys =
                {
                        "1", "2", "3",
                        "4", "5", "6",
                        "7", "8", "9",
                        "*", "0", "#"
                };

        GridPane numPad = new GridPane();
        numPad.setPadding(new Insets(20));

        Button call = new Button("Enter Phone Number");
        call.setDisable(true);

        for (int i = 0; i < 12; i++)
        {
            Button button = new Button(keys[i]);
            int tempI = i;
            button.setOnAction(e -> {
                tf.setText(currentNumber += keys[tempI]);
                call.setDisable(false);
                call.setText("Call");
                logger.info("NumPad Key " + keys[tempI] + " pressed");
            });

            button.getStyleClass().add("num-button");
            numPad.add(button, i % 3, (int) Math.ceil(i / 3));
        }



        call.setOnAction(e -> {
            if(call.getText().equals("Call")){
                logger.info("call started");
                call.setText("End Call ...");
            }
            else {
                logger.info("call ended");
                call.setText("Enter Phone Number");
                tf.setText("");
                call.setDisable(true);
            }
        });

        call.setId("call-button");
        call.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        numPad.add(call, 0, 4);

        GridPane.setColumnSpan(call, 3);
        GridPane.setHgrow(call, Priority.ALWAYS);


        VBox box = new VBox(tf, numPad);
        box.setPadding(new Insets(20, 20, 0,20));
        box.setFillWidth(true);
        return box;
    }
}
