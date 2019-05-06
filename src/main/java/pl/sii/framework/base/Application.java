package pl.sii.framework.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sii.framework.base.components.Page;
import pl.sii.framework.base.factories.PageFactory;
import pl.sii.framework.pages.MainPage;

import static pl.sii.framework.configurations.Configuration.getConfiguration;

public class Application extends Page {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static MainPage open() {
        String applicationAddress = getConfiguration().getApplicationAddress();
        LOGGER.info("Opening application at: {}", applicationAddress);
        browser().get(applicationAddress);
        return PageFactory.create(MainPage.class);
    }

    public static void close() {
        LOGGER.info("Closing application");
        closeBrowser();
    }
}