package pl.sii.framework.base.internal.driver;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;

public class FirefoxDriverManager extends DriverManager {

    private FirefoxDriverService fService;

    @Override
    public void startService() {
        // TODO
    }

    @Override
    public void stopService() {
        if (null != fService && fService.isRunning())
            fService.stop();
    }

    @Override
    public void createDriver() {
        driver = new FirefoxDriver(fService);
    }

}