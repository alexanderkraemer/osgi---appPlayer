package swt6.osgi.app.alarm;

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

import java.util.IntSummaryStatistics;
import java.util.Timer;
import java.util.TimerTask;


public class Alarm implements IApp {

    class NumberTextField extends TextField
    {

        @Override
        public void replaceText(int start, int end, String text)
        {
            if (validate(text))
            {
                super.replaceText(start, end, text);
            }
        }

        @Override
        public void replaceSelection(String text)
        {
            if (validate(text))
            {
                super.replaceSelection(text);
            }
        }

        private boolean validate(String text)
        {
            return text.matches("[0-9]*");
        }
    }

    private final Logger logger =  LoggerFactory.getLogger(Alarm.class.getName());
    private Image icon;

    static int interval;
    static Timer timer;
    private NumberTextField countdown;
    private Button send;
    private boolean isRunning = false;

    public Alarm() {
        icon = new Image(this.getClass().getResourceAsStream("alarm_logo.png"));
    }

    @Override
    public String getAppName() {
        return "Alarm App";
    }

    @Override
    public Image getAppIcon() {
        return icon;
    }

    @Override
    public Node getNode() {
        VBox n = new VBox();
        n.getChildren().add(this.makeAlarm());

        n.setUserData(this.getAppName());
        return n;
    }

    private Node makeAlarm() {

        countdown = new NumberTextField();
        countdown.setPadding(new Insets(5));

        send = new Button("Start Countdown");
        HBox messageField = new HBox(countdown, send);

        messageField.setPadding(new Insets(20));

        send.setOnAction(e -> {
            if(!isRunning && Integer.parseInt(countdown.getText()) > 0) {
                isRunning = true;
                countdown.setDisable(true);
                send.setDisable(true);
                logger.info("countdown started with " + countdown.getText() + " seconds");
                this.StartTimer(Integer.parseInt(countdown.getText()));
            }
        });

        return messageField;
    }

    private void StartTimer(int seconds){
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = seconds;
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {

                setInterval();
                // fire event, that countdown has ended!
            }
        }, delay, period);
    }

    private  final int setInterval() {

        if (interval < 1){
            countdown.setText("");
            countdown.setDisable(false);
            send.setDisable(false);
            isRunning = false;
            timer.cancel();
        }

        this.countdown.setText(Integer.toString(interval));
        return --interval;
    }
}
