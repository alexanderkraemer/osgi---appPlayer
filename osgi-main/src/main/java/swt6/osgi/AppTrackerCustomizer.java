package swt6.osgi;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import swt6.osgi.app.IApp;

public class AppTrackerCustomizer implements ServiceTrackerCustomizer<IApp, IApp> {

    private AppWindow appWindow;
    private BundleContext context;

    public AppTrackerCustomizer(BundleContext context, AppWindow appWindow) {
        this.appWindow = appWindow;
        this.context = context;
    }

    @Override
    public IApp addingService(ServiceReference<IApp> serviceReference) {
        IApp sf = context.getService(serviceReference);
        appWindow.addApp(sf);
        return sf;
    }

    @Override
    public void modifiedService(ServiceReference<IApp> serviceReference, IApp appFactory) {
        appWindow.removeApp(appFactory);
        appWindow.addApp(appFactory);
    }

    @Override
    public void removedService(ServiceReference<IApp> serviceReference, IApp appFactory) {
        appWindow.removeApp(appFactory);
    }
}
