package pl.sii.framework.base.internals;


import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.Function;

import static pl.sii.framework.base.internals.ElementWait.await;

public class DriverFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverFactory.class);
    private static WebDriver driver;

    private static BrowserType browserType;
    private static BrowserVersion ieBrowserVersion;

    public static synchronized WebDriver getDriver() {
        if (initialBrowserRun()) {
            try {
                LOGGER.info("Creating new WebDriver [{}] instance", browserType);
                switch (browserType) {
                    case FIREFOX_LOCAL:
                        driver = getLocalFirefoxDriver();
                        break;
                }
                LOGGER.info("Selenium WebDriver instance created [{}]", driver);
            } catch (Exception exception) {
                LOGGER.error("Can't create instance of Selenium WebDriver", exception);
            }
            driver.manage().window().setSize(new Dimension(1920, 1080));
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


    private static WebDriver getLocalFirefoxDriver() throws UnknownHostException {
        return new FirefoxDriver(createFirefoxDriverOptions());
    }

    private static FirefoxOptions createFirefoxDriverOptions() throws UnknownHostException {
        LOGGER.info("IP address: {}", InetAddress.getLocalHost());
        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
        FirefoxOptions options = new FirefoxOptions();
        options.setCapability("moz:webdriverClick", false);
        return options;
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
        return driver == null;
    }
}
