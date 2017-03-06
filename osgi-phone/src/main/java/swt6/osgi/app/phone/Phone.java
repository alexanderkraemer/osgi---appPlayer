package swt6.osgi.app.phone;

import javafx.scene.image.Image;
import swt6.osgi.app.IApp;

public class Phone implements IApp {

  private Image icon;

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

}
