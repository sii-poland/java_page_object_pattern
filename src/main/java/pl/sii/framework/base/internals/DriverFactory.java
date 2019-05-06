package com.assaabloy.shared.cliq.selenium.framework.utils;


import com.assaabloy.shared.cliq.selenium.test.base.BrowserType;
import com.assaabloy.shared.cliq.selenium.test.base.BrowserVersion;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.function.Function;

import static com.assaabloy.shared.cliq.selenium.framework.configuration.Configuration.getConfiguration;
import static com.assaabloy.shared.cliq.selenium.framework.model.base.internals.ElementWait.await;
import static org.apache.commons.lang3.Validate.notNull;

public class DriverFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);
    private static WebDriver driver;
    private static CKey currentCKeyInUse;

    private static BrowserType browserType;
    private static BrowserVersion ieBrowserVersion;

    public static synchronized WebDriver getDriver(CKey cKey) {
        notNull(browserType, "Browser type should be defined");
        notNull(cKey, "C-Key to use should be defined");
        if (wrongCKeyInUseInBrowser(cKey)) {
            LOGGER.info("Rerunning browser with new C-Key {} ", cKey);
            disposeDriver();
        }
        if (initialBrowserRun()) {
            try {
                LOGGER.info("Creating new WebDriver [{}] instance for {}", browserType, cKey);
                switch (browserType) {
                    case FIREFOX:
                        driver = getRemoteFirefoxDriver(cKey);
                        break;
                    case FIREFOX_LOCAL:
                        driver = getLocalFirefoxDriver(cKey);
                        break;
                    case INTERNET_EXPLORER:
                        driver = getRemoteIEDriver();
                        break;
                    case INTERNET_EXPLORER_LOCAL:
                        driver = getLocalIEDriver();
                        break;
                }
                currentCKeyInUse = cKey;
                LOGGER.info("Selenium WebDriver instance created [{}]", driver);
            } catch (Exception exception) {
                LOGGER.error("Can't create instance of Selenium WebDriver", exception);
            }
            driver.manage().window().setSize(new Dimension(1920, 1080));
        }
        return driver;
    }

    public static synchronized WebDriver getDriver() {
        if(driver == null){
            LOGGER.warn("Browser is not started or already closed!");
        }
        return driver;
    }

    public static synchronized void initialize(BrowserType browserType, BrowserVersion ieBrowserVersion) {
        DriverFactory.browserType = browserType;
        DriverFactory.ieBrowserVersion = ieBrowserVersion;
    }

    public static synchronized void disposeDriver() {
        LOGGER.info("Disposing Selenium WebDriver [{}]", driver);
        if (driver != null) {
            LOGGER.info("Quitting Selenium WebDriver...");
            driver.quit();
            await().until(browserClosed());
            LOGGER.info("Quitting of Selenium WebDriver is finished.");
            driver = null;
        }
        LOGGER.info("Disposing of Selenium WebDriver is finished.");
    }

    private static WebDriver getRemoteFirefoxDriver(CKey cKey) throws MalformedURLException, UnknownHostException {
        return new RemoteWebDriver(new URL(getConfiguration().getGridHubAddress()), createFirefoxDriverOptions(cKey));
    }

    private static WebDriver getLocalFirefoxDriver(CKey cKey) throws UnknownHostException {
        return new FirefoxDriver(createFirefoxDriverOptions(cKey));
    }

    private static FirefoxOptions createFirefoxDriverOptions(CKey cKey) throws UnknownHostException {
        LOGGER.info("IP address: {}", InetAddress.getLocalHost());
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxProfile profile = new FirefoxProfile(new File(DriverFactory.class.getResource(cKey.getProfilePath()).getPath()));
        profile.setPreference("security.default_personal_cert", "Select Automatically");
        profile.setPreference("intl.accept_languages", "en-gb");
        // Make sure that initial page of browser is blank
        profile.setPreference("browser.startup.homepage", "about:blank");
        profile.setPreference("startup.homepage_welcome_url", "about:blank");
        profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");
        // Default folder to download text/csv files
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        String downloadFolderFullPath = new File(getConfiguration().getFirefoxDownloadsDir()).getAbsolutePath();
        LOGGER.info("Firefox download directory: {}", downloadFolderFullPath);
        profile.setPreference("browser.download.dir", downloadFolderFullPath);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");
        profile.setPreference("plugin.state.java", 0);
        profile.setPreference("plugin.state.npdeployjava", 0);
        // Disable window to save password
        profile.setPreference("signon.rememberSignons", false);
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("moz:webdriverClick", false); // Firefox 58+
        options.setProfile(profile);
        return options;
    }

    private static WebDriver getRemoteIEDriver() throws MalformedURLException {
        notNull(ieBrowserVersion, "IE browser version should be defined");
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setVersion(ieBrowserVersion.getText());
        return new RemoteWebDriver(new URL(getConfiguration().getGridHubAddress()), capabilities);
    }

    private static WebDriver getLocalIEDriver() {
        return new InternetExplorerDriver();
    }

    private static Function<WebDriver, Boolean> browserClosed() {
        return webDriver -> {
            if (driver instanceof FirefoxDriver) {
                return (((FirefoxDriver) driver).getSessionId() == null);
            } else if (driver instanceof RemoteWebDriver) {
                return (((RemoteWebDriver) driver).getSessionId() == null);
            } else {
                try {
                    driver.getWindowHandle();
                    return false;
                } catch (NoSuchSessionException e) {
                    return true;
                }
            }
        };
    }

    private static boolean initialBrowserRun() {
        return driver == null || currentCKeyInUse == null;
    }

    private static boolean wrongCKeyInUseInBrowser(CKey cKey) {
        return driver != null && currentCKeyInUse != cKey;
    }
}
