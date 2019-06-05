package pl.sii.framework.base.internal.driver;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

public abstract class DriverManager {

    @Getter
    @Setter
    protected WebDriver driver;

    protected abstract void startService();

    protected abstract void stopService();

    protected abstract void createDriver();

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public WebDriver getDriver() {
        if (driver == null) {
            startService();
            createDriver();
        }
        return driver;
    }
}
