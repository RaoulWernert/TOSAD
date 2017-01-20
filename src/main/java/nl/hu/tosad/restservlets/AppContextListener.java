package nl.hu.tosad.restservlets;

import com.mysql.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppContextListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {}

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        for(Driver driver = drivers.nextElement(); drivers.hasMoreElements(); driver = drivers.nextElement()) {
            try {
                DriverManager.deregisterDriver(driver);
                LOG.info(String.format("Deregistered: %s", driver.getClass().getName()));
            } catch (SQLException e) {
                LOG.log(Level.SEVERE, String.format("Failed to deregister driver: %s", driver.getClass().getName()), e);
            }

            try {
                AbandonedConnectionCleanupThread.shutdown();
                LOG.info("Shutting down Abandoned Connection Cleanup Thread");
            } catch (InterruptedException e) {
                LOG.log(Level.SEVERE, "Failed to shut down Abandoned Connection Cleanup Thread", e);
            }
        }
    }
}
