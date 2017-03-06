package swt6.osgi.app.alarm;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.osgi.app.IApp;


public class AlarmActivator implements BundleActivator {

    private final Logger logger =  LoggerFactory.getLogger(AlarmActivator.class.getName());
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IApp.class, new Alarm(), null);
        logger.info("Alarm started");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        logger.info("Alarm stopped");
    }
}
