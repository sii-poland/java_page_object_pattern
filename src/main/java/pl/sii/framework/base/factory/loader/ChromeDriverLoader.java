package pl.sii.framework.base.factory.loader;

import io.github.bonigarcia.wdm.WebDriverManager;

public enum ChromeDriverLoader {
    CHROME_DRIVER_LOADER;

    ChromeDriverLoader() {
        WebDriverManager.chromedriver().setup();
    }

    public static void load() {
    }
}
