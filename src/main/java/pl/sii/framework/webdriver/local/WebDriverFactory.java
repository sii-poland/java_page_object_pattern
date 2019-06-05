package pl.sii.framework.webdriver.local;

import io.github.bonigarcia.wdm.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import pl.sii.framework.webdriver.IDriverFactory;
import pl.sii.framework.webdriver.StaticDriverOptionsFactory;

public class WebDriverFactory implements IDriverFactory {

    @Override
    public WebDriver getDriver(DriverManagerType type) {
        WebDriverManager.getInstance(type).setup();

        switch (type) {
            case CHROME:
                return new ChromeDriver(StaticDriverOptionsFactory.getChromeOptions(false));
            case FIREFOX:
                return new FirefoxDriver(StaticDriverOptionsFactory.getFirefoxOptions(false));
            case OPERA:
                return new OperaDriver(StaticDriverOptionsFactory.getOperaOptions(false));
            case EDGE:
                return new EdgeDriver(StaticDriverOptionsFactory.getEdgeOptions(false));
            case PHANTOMJS:
                throw new UnsupportedOperationException("PhantomJS Driver not supported yet.");
            case IEXPLORER:
                return new InternetExplorerDriver(StaticDriverOptionsFactory.getInternetExplorerOptions(false));
            case SELENIUM_SERVER_STANDALONE:
                throw new UnsupportedOperationException("Selenium Statndalon option not supported.");
            default:
                throw new UnsupportedOperationException("Option: " + type + " not supported.");
        }
    }
}
