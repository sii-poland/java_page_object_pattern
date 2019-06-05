package pl.sii.framework.base.internal.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

@Slf4j
public class ChromeDriverManager extends DriverManager {

    private ChromeDriverService chService;
    private static ChromeDriverManager chromeDriverManager;

    private ChromeDriverManager() {
    }

    @Override
    public void startService() {
        WebDriverManager.chromedriver()
                .setup();
        if (null == chService) {
            chService = new ChromeDriverService.Builder()
                    .usingAnyFreePort()
                    .build();
            try {
                chService.start();
            } catch (IOException e) {
                log.error("Error occurred when starting service", e);
            }
        }
    }

    @Override
    public void stopService() {
        if (null != chService && chService.isRunning())
            chService.stop();
    }

    @Override
    public void createDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("test-type");
        super.setDriver(new ChromeDriver(chService, options));
    }

    public static ChromeDriverManager getInstance() {
        if (chromeDriverManager == null) {
            chromeDriverManager = new ChromeDriverManager();
        }
        return chromeDriverManager;
    }
}