package pl.sii.framework.base.factory.loader;

import io.github.bonigarcia.wdm.WebDriverManager;

public enum FirefoxDriverLoader {
    FIREFOX_DRIVER_LOADER;

    FirefoxDriverLoader() {
        WebDriverManager.firefoxdriver().setup();
    }

    public static void load() {
    }
}
