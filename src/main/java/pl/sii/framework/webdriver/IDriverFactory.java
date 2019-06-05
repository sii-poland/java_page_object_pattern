package pl.sii.framework.webdriver;

import io.github.bonigarcia.wdm.DriverManagerType;
import org.openqa.selenium.WebDriver;

public interface IDriverFactory {
    WebDriver getDriver(DriverManagerType type);
}
