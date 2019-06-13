package pl.sii.framework.base.factory;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import pl.sii.framework.configuration.Configuration;

import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class BrowserOptionsFactory {

    public static AbstractDriverOptions getOptions() {
        Configuration configuration = ConfigFactory.create(Configuration.class);
        DriverManagerType driverType;
        AbstractDriverOptions options;
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
                WebDriverManager.chromedriver().setup();
                options = new ChromeOptions();
                ((ChromeOptions) options).addArguments("start-maximized");
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                options = new FirefoxOptions();
                ((FirefoxOptions) options).setHeadless(false);
                break;
            default:
                log.warn("Browser not provided, using default one");
                WebDriverManager.chromedriver().setup();
                options = new ChromeOptions();
                break;
        }
        return options;
    }
}