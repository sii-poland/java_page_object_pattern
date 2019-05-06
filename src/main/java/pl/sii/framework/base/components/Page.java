package com.assaabloy.shared.cliq.selenium.framework.model.base.component;

import com.assaabloy.shared.cliq.selenium.framework.model.base.Verifiable;
import com.assaabloy.shared.cliq.selenium.framework.model.base.internals.Searchable;
import com.assaabloy.shared.cliq.selenium.framework.utils.CKey;
import com.assaabloy.shared.cliq.selenium.framework.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.assaabloy.shared.cliq.selenium.framework.model.base.internals.ElementWait.await;

public abstract class Page implements Searchable, Verifiable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Page.class);
    protected static final Locator MESSAGE_INFO_SUMMARY_LOCATOR = Locator.css("span[class='ui-messages-info-summary']");
    protected static final Locator MESSAGE_WARN_SUMMARY_LOCATOR = Locator.css("span[class='ui-messages-warn-summary']");
    protected static final Locator MESSAGE_ERROR_SUMMARY_LOCATOR = Locator.css("span[class='ui-messages-error-summary']");

    @SuppressWarnings("unused")
    @FindBy(id = "headlineAndLoginInfo")
    private Element headline;

    protected static WebDriver browser() {
        return DriverFactory.getDriver();
    }

    protected static WebDriver browserWithCKey(CKey cKey) {
        return DriverFactory.getDriver(cKey);
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

    public void clickOn(Tab tab) {
        await().untilClickable(tab);
        tab.click();
        await().forPageToBeReady();
        await().until(waitUntilElementIsClicked(tab));
    }

    public String getInfoMessage() {
        return await().untilVisibleInRoot(MESSAGE_INFO_SUMMARY_LOCATOR)
                .getText();
    }

    public String getWarningMessage() {
        return await().untilVisibleInRoot(MESSAGE_WARN_SUMMARY_LOCATOR)
                .getText();
    }

    public String getErrorMessage() {
        return await().untilVisibleInRoot(MESSAGE_ERROR_SUMMARY_LOCATOR)
                .getText();
    }

    public List<String> getInfoMessages() {
        await().untilVisibleInRoot(MESSAGE_INFO_SUMMARY_LOCATOR);
        return findElements(MESSAGE_INFO_SUMMARY_LOCATOR).stream()
                .map(Element::getText)
                .collect(Collectors.toList());
    }

    public boolean isErrorMessageDisplayed() {
        await().forPageToBeReady();
        return isDisplayed(MESSAGE_ERROR_SUMMARY_LOCATOR);
    }

    public String pageTitle() {
        return await().untilVisible(headline).getText();
    }

    public void refresh() {
        browser().navigate().refresh();
        try {
            browser().switchTo().alert().accept();
            LOGGER.info("Modal dialog to confirm page to be refreshed has been displayed");
        } catch (NoAlertPresentException e) {
            LOGGER.info("No modal dialog is currently open after page refresh");
        }

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

    protected Function<WebDriver, Boolean> waitUntilElementIsClicked(Element element) {
        return driver -> {
            if (element.isSelected()) {
                return true;
            } else {
                LOGGER.warn("Next click has to be performed to perform the action");
                element.click();
                return false;
            }
        };
    }
}
