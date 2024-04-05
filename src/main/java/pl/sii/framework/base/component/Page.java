/*
 * Copyright (c) 2019.  Sii Poland
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.sii.framework.base.component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.sii.framework.base.factory.PageFactory;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class Page {

    @Getter
    protected WebDriver driver;
    @Getter
    protected Wait<WebDriver> webDriverWait;
    protected PageFactory pageFactory;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.webDriverWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofMillis(300))
                .withMessage("WebElement was not found in specified timeout");
        pageFactory = new PageFactory(driver);
    }

    public List<Element> findElements(final Locator locator) {
        return driver.findElements(locator.by())
                .stream()
                .map(Element::new)
                .toList();
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
        } catch (NotFoundException | StaleElementReferenceException | ElementNotInteractableException e) {
            return false;
        }
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NotFoundException | StaleElementReferenceException | ElementNotInteractableException e) {
            return false;
        }
    }
}
