package pl.sii.framework.base;

import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.base.factory.DriverManagerFactory;
import pl.sii.framework.base.factory.PageFactory;
import pl.sii.framework.configuration.Configuration;
import pl.sii.framework.pages.MainPage;

@Slf4j
public class Application extends Page {
    private static Configuration configuration = ConfigFactory.create(Configuration.class);

    public static MainPage open() {
        String applicationAddress = configuration.applicationAddress();
        log.info("Opening application at: {}", applicationAddress);

        DriverManagerFactory.getManager()
                .getDriver()
                .get(applicationAddress);
        return PageFactory.create(MainPage.class);
    }

    public static void close() {
        log.info("Closing application");
        DriverManagerFactory.getManager()
                .getDriver()
                .close();
    }
}