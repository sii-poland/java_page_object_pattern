package pl.sii.framework.base.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import pl.sii.framework.configuration.Configuration;

@Slf4j
public class DriverFactoryProvider {
    private static Configuration configuration = ConfigFactory.create(Configuration.class);

    public IDriverFactory getDriverFactory() {
        String driverType = configuration.driverType();
        IDriverFactory driverFactory;

        switch (driverType) {
            case "LOCAL":
                WebDriverManager.chromedriver().setup();
                driverFactory = new LocalDriverFactory();
                break;
            case "REMOTE":
                WebDriverManager.firefoxdriver().setup();
                driverFactory = new RemoteDriverFactory();
                break;
            default:
                throw new IllegalStateException("Wrong browserType, supported types: [LOCAL, REMOTE]");
        }
        return driverFactory;
    }
}