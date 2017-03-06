package swt6.osgi.app.phone;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import swt6.osgi.app.IApp;

/**
 * Created by Peter on 28.02.2017.
 */
public class PhoneActivator implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IApp.class, new Phone(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {

    }
}
