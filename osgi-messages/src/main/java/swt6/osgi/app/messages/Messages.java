package swt6.osgi.app.messages;

import javafx.scene.image.Image;
import swt6.osgi.app.IApp;

public class Messages implements IApp {

  private Image icon;

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

}
