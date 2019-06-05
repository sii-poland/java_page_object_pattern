package pl.sii.framework.webdriver;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariOptions;

public class StaticDriverOptionsFactory {

    public static ChromeOptions getChromeOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable-infobars", "test-type", "--no-sandbox");
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

        if (headless) {
            options.addArguments("headless");
        }

        return options;
    }

    public static FirefoxOptions getFirefoxOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);

        if (headless) {
            options.addArguments("--headless");
        }

        return options;
    }

    public static EdgeOptions getEdgeOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();

        return options;
    }

    public static InternetExplorerOptions getInternetExplorerOptions(boolean headless){
        InternetExplorerOptions options = new InternetExplorerOptions();
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
        options.enablePersistentHovering();
        options.ignoreZoomSettings();
        options.destructivelyEnsureCleanSession();

        return options;
    }

    public static SafariOptions getSafariOptions(boolean headless){
        SafariOptions options = new SafariOptions();

        return options;
    }

    public static OperaOptions getOperaOptions(boolean headless){
        OperaOptions options = new OperaOptions();

        return options;
    }

}
