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
public class MainPage extends Page {

    @FindBy(css = ".header_user_info")
    WebElement signInLink;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("User navigate to 'Sign in' page")
    public SignInPage signIn() {
        log.info("Go to 'Sign in' page");
        signInLink.click();
        return pageFactory.create(SignInPage.class);
    }
}