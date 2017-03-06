package swt6.osgi.app;

import javafx.scene.Node;
import javafx.scene.image.Image;

public interface IApp {
  public String getAppName();
  public Image getAppIcon();
  public Node getNode();
}
