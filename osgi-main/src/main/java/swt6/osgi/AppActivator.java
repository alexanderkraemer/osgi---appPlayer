package swt6.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.util.tracker.ServiceTracker;
import swt6.osgi.app.IApp;
import swt6.osgi.util.JavaFxUtils;

public class AppActivator implements BundleActivator{

    private AppWindow appWindow;
    private ServiceTracker<IApp, IApp> appTracker;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        JavaFxUtils.initJavaFx();
        startUI(bundleContext);

        appTracker = new ServiceTracker<>(bundleContext, IApp.class, new AppTrackerCustomizer(bundleContext, appWindow));
        appTracker.open();
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        appTracker.close();
        stopUI(bundleContext);
    }

    private void startUI(BundleContext bundleContext) {
        appWindow = new AppWindow();
        appWindow.addOnCloseEventHandler(evt -> {
            try {
                bundleContext.getBundle().stop();
            } catch (BundleException e) {
                e.printStackTrace();
            }
        });
        appWindow.show();
    }

    private void stopUI(BundleContext bundleContext) {
        if (appWindow != null) appWindow.close();
    }
}
