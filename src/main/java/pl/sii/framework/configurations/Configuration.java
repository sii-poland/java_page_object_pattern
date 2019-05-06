package com.assaabloy.shared.cliq.selenium.framework.configuration;

import com.assaabloy.shared.cliq.selenium.framework.utils.Locale;
import com.assaabloy.shared.cliq.selenium.test.base.BrowserType;
import com.assaabloy.shared.cliq.selenium.test.base.BrowserVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

public class Configuration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    private static Configuration configuration;

    private Properties properties;

    private static final String APPLICATION_ADDRESS_PROPERTY = "application.address";
    private final String applicationAddress;

    private static final String POP3_SERVER_PROPERTY = "application.pop3.server";
    private final String applicationPop3Server;

    private static final String GRID_HUB_ADDRESS_PROPERTY = "grid.hub.address";
    private final String gridHubAddress;

    private static final String RELAY_BOARD_API_ADDRESS_PROPERTY = "relayboard.api.address";
    private final String relayBoardApiAddress;

    private static final String CLIQ_REMOTE_URL_PROPERTY = "cliq.remote.url";
    private final String cliqRemoteUrl;

    private static final String DB_HOST_PROPERTY = "db.host";
    private final String dbHost;

    private static final String DB_USER_PROPERTY = "db.user";
    private final String dbUser;

    private static final String DB_PASSWORD_PROPERTY = "db.password";
    private final String dbPassword;

    private static final String CWM_PROCESS_NAME_PROPERTY = "cwm.process.name";
    private final String cwmProcessName;

    private static final String BROWSER_TYPE_PROPERTY = "browser.type";
    private Collection<BrowserType> browserTypes;

    private static final String IE_BROWSER_VERSION_PROPERTY = "ie.browser.version";
    private Collection<BrowserVersion> ieBrowserVersions;

    private static final String LOCAL_IE_WEBDRIVER_PATH_PROPERTY = "local.ie.webdriver.path";
    private final String localIEWebDriverPath;

    private static final String LOCAL_FF_WEBDRIVER_PATH_PROPERTY = "local.ff.webdriver.path";
    private final String localFFWebDriverPath;

    private static final String LOCALE_PROPERTY = "locale";
    private final String locale;

    private static final String TRANSLATIONS_DIRECTORY_PATH_PROPERTY = "translations.directory.path";
    private final String translationsDirectoryPath;

    private static final String SELENIUM_TEST_SHARED_FOLDER = "selenium.test.fileShare";
    private final String seleniumTestSharedFolder;

    private static final String FIREFOX_DOWNLOADS_DIR = "firefox.downloads.dir";
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
        applicationPop3Server = extractProperty(POP3_SERVER_PROPERTY);
        gridHubAddress = extractProperty(GRID_HUB_ADDRESS_PROPERTY);
        relayBoardApiAddress = extractProperty(RELAY_BOARD_API_ADDRESS_PROPERTY);
        cliqRemoteUrl = extractProperty(CLIQ_REMOTE_URL_PROPERTY);
        dbHost = extractProperty(DB_HOST_PROPERTY);
        dbUser = extractProperty(DB_USER_PROPERTY);
        dbPassword = extractProperty(DB_PASSWORD_PROPERTY);
        cwmProcessName = extractProperty(CWM_PROCESS_NAME_PROPERTY);

        initializeBrowserTypes(extractProperty(BROWSER_TYPE_PROPERTY));
        initializeIEBrowserVersions(extractProperty(IE_BROWSER_VERSION_PROPERTY));

        localIEWebDriverPath = extractProperty(LOCAL_IE_WEBDRIVER_PATH_PROPERTY);
        localFFWebDriverPath = extractProperty(LOCAL_FF_WEBDRIVER_PATH_PROPERTY);
        locale = extractProperty(LOCALE_PROPERTY);
        translationsDirectoryPath = extractProperty(TRANSLATIONS_DIRECTORY_PATH_PROPERTY);
        seleniumTestSharedFolder = extractProperty(SELENIUM_TEST_SHARED_FOLDER);
        firefoxDownloadsDir = extractProperty(FIREFOX_DOWNLOADS_DIR);

        validateConfiguration();

        LOGGER.info("Configuration loaded");
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        return configuration;
    }

    private String extractProperty(String propertyName) {
        LOGGER.info("Extracting {} property...", propertyName);
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

    private void initializeIEBrowserVersions(String ieBrowserVersionProperty) {
        if (ieBrowserVersionProperty != null) {
            final String[] ieBrowserVersionsArray = ieBrowserVersionProperty.split(",");
            ieBrowserVersions = new ArrayList<>(ieBrowserVersionsArray.length);
            for (String ieBrowserVersion : ieBrowserVersionsArray) {
                ieBrowserVersions.add(BrowserVersion.fromString(ieBrowserVersion.trim()));
            }
        }
    }

    private void validateConfiguration() {
        LOGGER.info("Validating configuration...");

        notNull(applicationAddress, "application.address property must be defined");
        notEmpty(browserTypes, "browser.type property must be defined");
        if (browserTypes.contains(BrowserType.INTERNET_EXPLORER) || browserTypes.contains(BrowserType.FIREFOX)) {
            notNull(gridHubAddress, "grid.hub.address property must be defined while using grid IE or Firefox browser");
        }
        if (browserTypes.contains(BrowserType.INTERNET_EXPLORER)) {
            notEmpty(ieBrowserVersions, "ie.browser.version property must be defined while using grid IE browser)");
        }
        if (browserTypes.contains(BrowserType.INTERNET_EXPLORER_LOCAL)) {
            notNull(localIEWebDriverPath, "local.ie.webdriver.path property must be defined while using local IE browser");
        }
        notNull(locale, "locale property must be defined");
        try {
            Locale.valueOf(locale);
        } catch (IllegalArgumentException exception) {
            LOGGER.error("locale {} is not available", locale);
            throw new ExceptionInInitializerError(exception);
        }
        notNull(translationsDirectoryPath, "translations.directory.path property must be defined");

        LOGGER.info("Configuration is valid");
    }

    public String getApplicationAddress() {
        return applicationAddress;
    }

    public String getApplicationPop3Server() {
        return applicationPop3Server;
    }

    public String getGridHubAddress() {
        return gridHubAddress;
    }

    public String getRelayBoardApiAddress() {
        return relayBoardApiAddress;
    }

    public String getCliqRemoteUrl() { return cliqRemoteUrl; }

    public String getDbHost() { return dbHost; }

    public String getDbUser() { return dbUser; }

    public String getDbPassword() { return dbPassword; }

    public String getCwmProcessName() { return cwmProcessName; }

    public Iterable<BrowserType> getBrowserTypes() {
        return browserTypes;
    }

    public Iterable<BrowserVersion> getIEBrowserVersions() {
        return ieBrowserVersions;
    }

    public String getLocalIEWebDriverPath() {
        return localIEWebDriverPath;
    }

    public String getLocalFFWebDriverPath() {
        return localFFWebDriverPath;
    }

    public String getLocale() {
        return locale;
    }

    public String getTranslationsDirectoryPath() {
        return translationsDirectoryPath;
    }

    public String getSeleniumTestSharedFolder() {
        return seleniumTestSharedFolder;
    }

    public String getFirefoxDownloadsDir() {
        return firefoxDownloadsDir;
    }
}