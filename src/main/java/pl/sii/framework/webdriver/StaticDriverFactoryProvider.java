package pl.sii.framework.webdriver;

import pl.sii.framework.webdriver.local.WebDriverFactory;

public class StaticDriverFactoryProvider {
    private static boolean remote = true;

    public static IDriverFactory getFactory() {
        return new WebDriverFactory();
    }
}
