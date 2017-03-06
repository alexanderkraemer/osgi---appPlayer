package swt6.osgi.app.phone;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.osgi.app.IApp;


public class PhoneActivator implements BundleActivator {
    private final Logger logger =  LoggerFactory.getLogger(PhoneActivator.class.getName());

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IApp.class, new Phone(), null);
        logger.info("Phone started");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        logger.info("Phone stopped");
    }


}
