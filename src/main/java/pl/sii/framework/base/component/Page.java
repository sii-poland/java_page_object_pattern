package pl.sii.framework.base.component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import pl.sii.framework.base.factory.DriverManagerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class Page {

    @Getter
    private WebDriver driver;

    public Page(WebDriver driver) {
        this.driver = driver;
    }

    public Page() {
        this(DriverManagerFactory.getManager().getDriver());
    }

    public List<Element> findElements(final Locator locator) {
        return driver.findElements(locator.by())
                .stream()
                .map(Element::new)
                .collect(Collectors.toList());
    }

    public Element findElement(final Locator locator) {
        return new Element(driver.findElement(locator.by()));
    }


    public void refresh() {
        driver.navigate().refresh();
    }

    public boolean isDisplayed(final Locator locator) {
        try {
            return driver.findElement(locator.by()).isDisplayed();
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
