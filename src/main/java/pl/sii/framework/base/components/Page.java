package pl.sii.framework.base.components;

import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sii.framework.base.internals.DriverFactory;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Page {
    private static final Logger LOGGER = LoggerFactory.getLogger(Page.class);

    protected static WebDriver browser() {
        return DriverFactory.getDriver();
    }

    protected static void closeBrowser() {
        DriverFactory.disposeDriver();
    }

    public List<Element> findElements(final Locator locator) {
        return browser().findElements(locator.by())
                .stream()
                .map(Element::new)
                .collect(Collectors.toList());
    }

    public Element findElement(final Locator locator) {
        return new Element(browser().findElement(locator.by()));
    }


    public void refresh() {
        browser().navigate().refresh();
    }

    public static boolean isDisplayed(final Locator locator) {
        try {
            return browser().findElement(locator.by()).isDisplayed();
        } catch (NotFoundException | StaleElementReferenceException | ElementNotVisibleException e) {
            return false;
        }
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NotFoundException | StaleElementReferenceException | ElementNotVisibleException e) {
            return false;
        }
    }
}
