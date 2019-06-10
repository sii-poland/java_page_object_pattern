package pl.sii.framework.base.factory;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class RemoteDriverFactory implements IDriverFactory {

    @Override
    public WebDriver getDriver() {
        DriverManagerType driverType;
        WebDriver driver;
        URL gridHubUrl = null;
        DesiredCapabilities desiredCapabilities;
        try {
            driverType = DriverManagerType.valueOf(configuration.browserName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Wrong browserName, supported browsers:\n" +
                    Arrays.toString(
                            Stream.of(DriverManagerType.values())
                                    .map(DriverManagerType::name)
                                    .toArray(String[]::new)));
        }

        try {
            gridHubUrl = new URL(configuration.gridHubUrl());
        } catch (MalformedURLException e) {
            log.error("Invalid gridHubUrl", e);
        }
        switch (driverType) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, BrowserOptionsFactory.getOptions());
                driver = new RemoteWebDriver(gridHubUrl, desiredCapabilities);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, BrowserOptionsFactory.getOptions());
                driver = new RemoteWebDriver(gridHubUrl, desiredCapabilities);
                break;
            default:
                log.warn("Browser not provided, using default one");
                WebDriverManager.chromedriver().setup();
                desiredCapabilities = new DesiredCapabilities();
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, BrowserOptionsFactory.getOptions());
                driver = new RemoteWebDriver(gridHubUrl, desiredCapabilities);
                break;
        }
        return driver;
    }
}