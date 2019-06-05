package pl.sii.framework.base.factory;

import org.aeonbits.owner.ConfigFactory;
import pl.sii.framework.base.internal.driver.DriverType;
import pl.sii.framework.base.internal.driver.ChromeDriverManager;
import pl.sii.framework.base.internal.driver.DriverManager;
import pl.sii.framework.base.internal.driver.FirefoxDriverManager;
import pl.sii.framework.configuration.Configuration;

public class DriverManagerFactory {
    private static  Configuration configuration = ConfigFactory.create(Configuration.class);

    public static DriverManager getManager() {

        DriverManager driverManager;

        DriverType driverType = DriverType.valueOf(configuration.browserName());

        switch (driverType) {
            case CHROME:
                driverManager = ChromeDriverManager.getInstance();
                break;
            case FIREFOX:
                driverManager = new FirefoxDriverManager();
                break;
            default:
                driverManager = ChromeDriverManager.getInstance();
                break;
        }
        return driverManager;

    }
}
