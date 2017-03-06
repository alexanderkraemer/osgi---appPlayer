package swt6.osgi.app.messages;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import swt6.osgi.app.IApp;

/**
 * Created by Peter on 28.02.2017.
 */
public class MessagesActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IApp.class, new Messages(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
