package pl.sii.framework.base.component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.sii.framework.base.factory.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class Page {

    @Getter
    protected WebDriver driver;
    @Getter
    protected WebDriverWait webDriverWait;
    protected PageFactory pageFactory;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new WebDriverWait(driver, 15);
        pageFactory = new PageFactory(driver);
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
