package pl.sii.framework.configurations;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sii.framework.base.internals.BrowserType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    private static Configuration configuration;

    private Properties properties;

    private static final String APPLICATION_ADDRESS_PROPERTY = "application.address";
    @Getter
    private final String applicationAddress;

    private static final String BROWSER_TYPE_PROPERTY = "browser.type";
    @Getter
    private Collection<BrowserType> browserTypes;

    private static final String FIREFOX_DOWNLOADS_DIR = "firefox.downloads.dir";
    @Getter
    private final String firefoxDownloadsDir;

    private Configuration() {

        LOGGER.info("Loading configuration...");

        try {
            properties = new Properties();
            properties.load(Configuration.class.getClassLoader().getResourceAsStream("configuration.properties"));
        } catch (IOException exception) {
            LOGGER.error("Error while loading configuration from configuration.properties file");
            throw new ExceptionInInitializerError(exception);
        }
        applicationAddress = extractProperty(APPLICATION_ADDRESS_PROPERTY);
        firefoxDownloadsDir = extractProperty(FIREFOX_DOWNLOADS_DIR);
        initializeBrowserTypes(extractProperty(BROWSER_TYPE_PROPERTY));
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    private String extractProperty(String propertyName) {
        String property = System.getProperty(propertyName);
        if (property == null) {
            property = properties.getProperty(propertyName);
        }
        LOGGER.info("Property {} extracted with value: {}", propertyName, property);
        return property;
    }

    private void initializeBrowserTypes(String browserTypeProperty) {
        final String[] browserTypesArray = browserTypeProperty.split(",");
        browserTypes = new ArrayList<>(browserTypesArray.length);
        for (String browserType : browserTypesArray) {
            browserTypes.add(BrowserType.valueOf(browserType.trim()));
        }
    }
}