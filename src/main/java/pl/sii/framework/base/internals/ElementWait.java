package pl.sii.framework.base.internals;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.sii.framework.base.components.Element;
import pl.sii.framework.base.components.Locator;
import pl.sii.framework.configurations.Constants;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.MILLIS;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public final class ElementWait {
    private final WebDriverWait wait;
    private boolean useDefaultException;

    private ElementWait() {
        wait = new WebDriverWait(DriverFactory.getDriver(), Constants.ACTION_TIMEOUT_IN_SECONDS);
    }

    public static ElementWait await() {
        return new ElementWait();
    }

    public Element untilClickable(WebElement element) {
        return new Element(untilWithIgnore(elementToBeClickable(element)));
    }

    public Element untilVisible(WebElement element) {
        return new Element(untilWithIgnore(visibilityOf(element)));
    }

    public Element untilVisibleInRoot(Locator locator) {
        return new Element(untilWithIgnore(visibilityOfElementLocated(locator.by())));
    }

    public Element untilFound(WebElement element) {
        until(isFound(element));
        return new Element(element);
    }

    public boolean isElementNotDisplayed(WebElement element) {
        try {
            return until(isNotDisplayed(element));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void forPageToBeReady() {
        await().untilNotVisibleInRoot(Locator
                .xpath("//body[contains(@class,'wait')] | //body[contains(@style, 'wait')]"));
    }

    public void untilNotVisibleInRoot(Locator locator) {
        untilWithIgnore(invisibilityOfElementLocated(locator.by()));
    }

    public void untilNotFound(WebElement element) {
        until(isNotDisplayed(element));
    }

    public <R> R until(Function<WebDriver, R> function) {
        return untilWithIgnore(function);
    }

    public ElementWait withNoDefaultsException() {
        useDefaultException = false;
        return this;
    }

    public ElementWait withTimeout(long time, TimeUnit timeUnit) {
        wait.withTimeout(Duration.of(timeUnit.toMillis(time), MILLIS));
        return this;
    }

    public ElementWait pollingEvery(long timeInMillis) {
        wait.pollingEvery(Duration.of(timeInMillis, MILLIS));
        return this;
    }

    public ElementWait withMessage(String message) {
        wait.withMessage(message);
        return this;
    }

    public ElementWait addExceptionsToIgnore(Class<? extends Throwable> exceptionType) {
        wait.ignoring(exceptionType);
        return this;
    }

    private <R> R untilWithIgnore(Function<WebDriver, R> function) {
        try {
            return wait.until(function);
        } catch (Throwable e) {
            if (useDefaultException && e instanceof NoSuchElementException) {
                throw new TimeoutException("Expected condition failed with timeout", e);
            }
            throw e;
        }
    }

    private Function<WebDriver, Boolean> isFound(WebElement element) {
        return driver -> {
            try {
                return element.getLocation() != null;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                return false;
            }
        };
    }


    private Function<WebDriver, Boolean> isNotDisplayed(WebElement element) {
        return driver -> {
            try {
                return !element.isDisplayed();
            } catch (NoSuchElementException e) {
                return true;
            }
        };
    }
}