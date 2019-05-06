package com.assaabloy.shared.cliq.selenium.framework.model.base.internals;

import com.assaabloy.shared.cliq.selenium.framework.configuration.Constants;
import com.assaabloy.shared.cliq.selenium.framework.model.base.component.Element;
import com.assaabloy.shared.cliq.selenium.framework.model.base.component.Locator;
import com.assaabloy.shared.cliq.selenium.framework.utils.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.MILLIS;

public final class ElementWait {
    private final WebDriverWait wait;
    private boolean useDefaultException;
    private boolean defaultExceptionsRegistered;

    private ElementWait() {
        wait = new WebDriverWait(DriverFactory.getDriver(), Constants.ACTION_TIMEOUT_IN_SECONDS);
        useDefaultException = true;
        updateWaitWithDefaultExceptions();
    }

    public static ElementWait await() {
        return new ElementWait();
    }

    /**
     * Returns a WebElement from input if element is clickable before reaching timeout.
     *
     * @param element WebElement expected to be clickable
     * @return the input WebElement when it is clickable
     */
    public Element untilClickable(WebElement element) {
        return new Element(untilWithIgnore(ExpectedConditions.elementToBeClickable(element)));
    }

    /**
     * Returns a WebElement which can be found with use of relative locator for which the root DOM element is
     * rootSearchableElement. If we want to find element outside the rootSearchableElement then we have to use
     * as a locator e.g. xpath which starts with '//'
     * <p>
     * The WebElement is returned only if it is clickable.
     *
     * @param rootSearchableElement is a root DOM element which implements Searchable
     * @param locator               locator which have to be achievable from the rootSearchableElement
     * @return WebElement indicated by the locator if it is clickable
     */
    public Element untilClickable(Searchable rootSearchableElement, Locator locator) {
        return until(isClickable(rootSearchableElement, locator));
    }

    /**
     * Returns a WebElement from the input argument if it is found in the DOM tree and is visible
     *
     * @param element WebElement expected to be visible
     * @return the input WebElement if it is visible
     */
    public Element untilVisible(WebElement element) {
        return new Element(untilWithIgnore(ExpectedConditions.visibilityOf(element)));
    }

    /**
     * Returns a WebElement which can be found with use of relative locator for which the root DOM element is
     * rootSearchableElement. If we want to find element outside the rootSearchableElement then we have to use
     * as a locator e.g. xpath which starts with '//'
     * <p>
     * The WebElement is returned only if locator indicates element found in the DOM tree and visible.
     *
     * @param rootSearchableElement root locator on which search is performed
     * @param locator               which to be found inside rootSearchableElement
     * @return found Element
     */
    public Element untilVisible(Searchable rootSearchableElement, Locator locator) {
        until(isDisplayed(rootSearchableElement, locator));
        return rootSearchableElement.findElement(locator);
    }

    /**
     * Method waits until element indicated by the locator is displayed.
     * Search is performed on the whole DOM tree so it should be taken into account during constructing of the locator.
     *
     * @param locator locator to Element expected to be visible
     */
    public Element untilVisibleInRoot(Locator locator) {
        return new Element(untilWithIgnore(ExpectedConditions.visibilityOfElementLocated(locator.by())));
    }

    /**
     * Returns a WebElement from the input argument if it is found in the DOM tree but it don't need to be visible.
     *
     * @param element WebElement expected to be found
     * @return the input WebElement if it is found
     */
    public Element untilFound(WebElement element) {
        until(isFound(element));
        return new Element(element);
    }

    /**
     * This method returns true if element is not displayed and false if element is displayed. It is suggested to use
     * this method when expect that element to be not displayed because it fails after timeout.
     *
     * @param element we check if is displayed
     * @return status of visibility of the element
     */
    public boolean isElementNotDisplayed(WebElement element) {
        try {
            return until(isNotDisplayed(element));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Methods waits until text is displayed in the element indicated as an argument.
     *
     * @param element       which should contains the text
     * @param textToBeFound is a text which expect to be found
     */
    public void untilTextToBePresentInElement(Element element, String textToBeFound) {
        untilWithIgnore(ExpectedConditions.textToBePresentInElement(element, textToBeFound));
    }

    /**
     * Method waits for some actions in application to be performed. It is based on wait class for the body tag.
     */
    public void forPageToBeReady() {
        await().untilNotVisibleInRoot(Locator
                .xpath("//body[contains(@class,'wait')] | //body[contains(@style, 'wait')]"));
    }

    /**
     * Method waits until element indicated by the locator is not displayed but it still can be found in the DOM tree.
     * Search is performed on the whole DOM tree so it should be taken into account during constructing of the locator.
     *
     * @param locator locator to Element expected to be not visible
     */
    public void untilNotVisibleInRoot(Locator locator) {
        untilWithIgnore(ExpectedConditions.invisibilityOfElementLocated(locator.by()));
    }

    /**
     * Method waits until element is not found in the DOM tree.
     *
     * @param element WebElement expected to be not found
     */
    public void untilNotFound(WebElement element) {
        until(isNotDisplayed(element));
    }

    /**
     * Methods executes the function method in the loop until timeout is reached. If the function method returns
     * Boolean then after returning true value method finishes with the success. If the function method returns other
     * object then value different then null finishes this wait with success. If the predicate method returns false
     * value (for Boolean) or null for other object then it is executed once again after the pooling time.
     *
     * @param function method which describes when wait criteria are passed and return desired object
     */
    public <R> R until(Function<WebDriver, R> function) {
        return untilWithIgnore(function);
    }

    /**
     * Switches off throwing NoSuchElementException and StaleElementReferenceException during executing waits
     *
     * @return this element
     */
    public ElementWait withNoDefaultsException() {
        useDefaultException = false;
        return this;
    }

    /**
     * Method updates default timeout used by the class
     *
     * @param time updated timeout in milliseconds
     * @return this element
     */
    public ElementWait withTimeout(long time, TimeUnit timeUnit) {
        wait.withTimeout(Duration.of(timeUnit.toMillis(time), MILLIS));
        return this;
    }

    /**
     * Method updates default polling time used by the class
     *
     * @param timeInMillis updated polling time in milliseconds
     * @return this element
     */
    public ElementWait pollingEvery(long timeInMillis) {
        wait.pollingEvery(Duration.of(timeInMillis, MILLIS));
        return this;
    }

    /**
     * Method adds a message which will be displayed when wait is failed
     *
     * @param message to be displayed when failed
     * @return this element
     */
    public ElementWait withMessage(String message) {
        wait.withMessage(message);
        return this;
    }

    public ElementWait addExceptionsToIgnore(Class<? extends Throwable> exceptionType) {
        wait.ignoring(exceptionType);
        return this;
    }

    /**
     * Method wrap until method of FluentWait class because when NoSuchElementException occurs for the element
     * it is waiting for then this exception is thrown after timeout even if it was added to ignored. This exception
     * is caught by FluentWait's until method but String.format rethrow it outside before throwing TimeoutException.
     * This issue can have impact on all places where we catching TimeoutException in block with any until method
     * from ElementWait class.
     *
     * @param function method which describes when wait criteria are passed and return desired object
     * @return The functions' return value if the function returned something different
     * from null or false before the timeout expired.
     * @throws TimeoutException If the timeout expires.
     */
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

    private Function<WebDriver, Boolean> isDisplayed(Searchable rootSearchableElement, Locator locator) {
        return driver -> {
            try {
                return rootSearchableElement.findElement(locator).isDisplayed();
            } catch (NoSuchElementException e) {
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

    private Function<WebDriver, Element> isClickable(Searchable rootSearchableElement, Locator locator) {
        return driver -> {
            Element elementToBeClickable = rootSearchableElement.findElement(locator);
            if (elementToBeClickable.isDisplayed() && elementToBeClickable.isEnabled()) {
                return elementToBeClickable;
            } else {
                return null;
            }
        };
    }

    private void updateWaitWithDefaultExceptions() {
        if (useDefaultException & !defaultExceptionsRegistered) {
            defaultExceptionsRegistered = true;
            wait.ignoring(NoSuchElementException.class);
            wait.ignoring(StaleElementReferenceException.class);
        }
    }
}
