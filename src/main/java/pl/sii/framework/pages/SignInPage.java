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

package pl.sii.framework.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.sii.framework.base.component.Page;

@Slf4j
public class SignInPage extends Page {

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#email")
    private WebElement emailInput;

    @FindBy(css = "#passwd")
    WebElement passwordInput;

    @FindBy(css = "#SubmitLogin")
    WebElement submitButton;

    @FindBy(css = ".alert-danger")
    WebElement alertMessage;

    @Step("User sets email address to email input field")
    public SignInPage withEmail(String email) {
        log.info("Set email {}", email);
        emailInput.sendKeys(email);
        return this;
    }

    @Step("User sets password to password input field")
    public SignInPage withPassword(String password) {
        log.info("Set password {}", password);
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("User clicks on Submit button")
    public MainPage submit() {
        log.info("Submit login form");
        submitButton.click();
        return pageFactory.create(MainPage.class);
    }

    @Step("User clicks on Submit button")
    public SignInPage submitWithoutSuccess() {
        log.info("Submit login form");
        submitButton.click();
        return this;
    }

    @Step("User checks if alert message is displayed")
    public boolean isAlertMessageDisplayed() {
        log.info("Check if alert message displayed");
        return webDriverWait.until(webDriver -> alertMessage.isDisplayed());
    }
}