package pl.sii.framework.base.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sii.framework.base.components.Page;
import pl.sii.framework.base.internals.DriverFactory;

public class PageFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(PageFactory.class);

    public static <T extends Page> T create(Class<T> classToProxy) {
        LOGGER.debug("Creating page object: {}", classToProxy.getSimpleName());
        return org.openqa.selenium.support.PageFactory.initElements(DriverFactory.getDriver(), classToProxy);
    }
}