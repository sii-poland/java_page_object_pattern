package pl.sii.framework.base.factory;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class LocalDriverFactory implements IDriverFactory {

    @Override
    public WebDriver getDriver() {
        DriverManagerType driverType;
        WebDriver driver;
        try {
            driverType = DriverManagerType.valueOf(configuration.browserName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Wrong browserName, supported browsers:\n" +
                    Arrays.toString(
                            Stream.of(DriverManagerType.values())
                                    .map(DriverManagerType::name)
                                    .toArray(String[]::new)));
        }
        switch (driverType) {
            case CHROME:
                driver = new ChromeDriver(BrowserOptionsFactory.getOptions());
                break;
            case FIREFOX:
                driver = new FirefoxDriver(BrowserOptionsFactory.getOptions());
                break;
            default:
                log.warn("Browser not provided, using default one");
                driver = new ChromeDriver(BrowserOptionsFactory.getOptions());
                break;
        }
        return driver;
    }
}