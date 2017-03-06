package swt6.osgi.app.messages;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.osgi.app.IApp;


public class MessagesActivator implements BundleActivator {

    private final Logger logger =  LoggerFactory.getLogger(MessagesActivator.class.getName());
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        bundleContext.registerService(IApp.class, new Messages(), null);
        logger.info("Messages started");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        logger.info("Messages stopped");
    }
}
