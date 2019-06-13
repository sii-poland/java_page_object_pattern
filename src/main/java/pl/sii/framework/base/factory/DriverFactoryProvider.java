package pl.sii.framework.base.factory;

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
                driverFactory = new LocalDriverFactory();
                break;
            case "REMOTE":
                driverFactory = new RemoteDriverFactory();
                break;
            default:
                throw new IllegalStateException("Wrong driverType, supported types: [LOCAL, REMOTE]");
        }
        return driverFactory;
    }
}