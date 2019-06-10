package pl.sii.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import pl.sii.framework.base.Application;
import pl.sii.framework.base.factory.DriverFactoryProvider;

public class BaseTest {
    private WebDriver driver;
    protected Application application;

    @BeforeEach
    public void setUpBeforeEach() {
        this.driver = new DriverFactoryProvider().getDriverFactory().getDriver();
        application = new Application(driver);
    }

    @AfterEach
    public void cleanUpAfterEach() {
        application.close();
    }
}
