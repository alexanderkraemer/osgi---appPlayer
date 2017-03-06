package swt6.osgi;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import swt6.osgi.app.IApp;
import swt6.osgi.util.JavaFxUtils;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class AppWindow {

    private Stage stage;
    private ToolBar toolBar;
    private VBox rootPane;
    private List<IApp> appList = new ArrayList<>();
    private IApp currentActiveApp;
    private Canvas canvas;
    private List<EventHandler<WindowEvent>> onCloseHandlers = new ArrayList<>();

    private class PaintCanvas extends Canvas {

        private GraphicsContext gc;

        public PaintCanvas() {
            gc = this.getGraphicsContext2D();
            gc.setLineWidth(2);

            // register for resize events
            // this.widthProperty().addListener(event -> redraw());
            // this.heightProperty().addListener(event -> redraw());
        }
    }

    public AppWindow() {
        try {
            JavaFxUtils.runAndWait(() -> {
                toolBar = new ToolBar();
                canvas = new PaintCanvas();
                rootPane = new VBox(toolBar, canvas);

                canvas.widthProperty().bind(rootPane.widthProperty());
                canvas.heightProperty().bind(rootPane.heightProperty());
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        try {
            JavaFxUtils.runAndWait(() -> {
                if (stage == null) {
                    stage = new Stage();
                    stage.setScene(new Scene(rootPane, 500, 500));
                    stage.setMinWidth(250);
                    stage.setMinHeight(250);
                    stage.setOnCloseRequest(evt -> {
                        onCloseHandlers.forEach(h -> h.handle(evt));
                    });
                    stage.setTitle("AppPlayer");
                }
                stage.show();
            });
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }

    public void close()
    {
        try {
            JavaFxUtils.runAndWait(() -> {if (stage != null) stage.close();});
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void addOnCloseEventHandler(EventHandler<WindowEvent> evt) {
        try {
            JavaFxUtils.runAndWait(() -> onCloseHandlers.add(evt));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void removeOnCloseEventHandler(EventHandler<WindowEvent> evt) {
        try {
            JavaFxUtils.runAndWait(() -> onCloseHandlers.remove(evt));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void addApp(IApp app) {
        try {
            JavaFxUtils.runAndWait(() -> {
                appList.add(app);
                if (currentActiveApp == null) {
                    setCurrentApp(app);
                }

                // add toolbar button
                Button button = new Button();
                button.setTooltip(new Tooltip(app.getAppName()));
                button.setGraphic(new ImageView(app.getAppIcon()));
                button.setUserData(app.getAppName());
                button.setOnAction(evt -> toolbarButtonPressed(evt));
                toolBar.getItems().add(button);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void removeApp(IApp app) {
        try {
            JavaFxUtils.runAndWait(() -> {
                String name = app.getAppName();

                // remove app by name
                Iterator<IApp> appIt = appList.iterator();
                while (appIt.hasNext()) {
                    if (appIt.next().getAppName().equals(name)) {
                        appIt.remove();
                    }
                }

                // remove toolbar item for shape factory.
                toolBar.getItems().remove(getToolBarButtonByName(app.getAppName()));

                // select a different shape factory.
                if (appList.size() > 0)
                    setCurrentApp(appList.iterator().next());
                else
                    setCurrentApp(null);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void toolbarButtonPressed(ActionEvent evt) {
        String sfName = (String) ((Button) evt.getSource()).getUserData();
        setCurrentApp(getAppByName(sfName));
    }

    private IApp getAppByName(String name) {

        for (IApp app : appList) {
            if (app.getAppName().equals(name)) {
                return app;
            }
        }

        return null;
    }

    private Button getToolBarButtonByName(String name) {
        for (Node n : toolBar.getItems())
            if (name.equals(n.getUserData())) {
                return (Button) n;
            }
        return null;
    }

    private void setCurrentApp(IApp app) {
        currentActiveApp = app;
        if (app == null) {
            return;
        }
        Button button = getToolBarButtonByName(app.getAppName());
        if (button != null) button.requestFocus();
    }

}