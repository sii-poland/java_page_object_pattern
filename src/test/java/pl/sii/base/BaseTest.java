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

package pl.sii.base;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import pl.sii.framework.base.Application;
import pl.sii.framework.base.factory.DriverFactoryProvider;

import java.io.File;

@Slf4j
@ExtendWith(TestExtensions.class)
public class BaseTest {
    private WebDriver driver;
    protected Application application;

    @BeforeEach
    public void setUpBeforeEach() {
        this.driver = new DriverFactoryProvider().getDriverFactory().getDriver();
        application = new Application(driver);
    }

    @AfterEach
    public void cleanUpAfterEach() {
//        application.close();
    }

    public byte[] takeScreenshot(String method) {
        log.info("taking Screenshot");
        byte[] screens = new byte[0];
        if (driver instanceof TakesScreenshot) {
            TakesScreenshot screenshotTakingDriver = (TakesScreenshot) driver;
            try {
                File localScreenshots = new File(new File("target"), "screenshots");
                if (!localScreenshots.exists() || !localScreenshots.isDirectory()) {
                    localScreenshots.mkdirs();
                }
                File screenshot = new File(localScreenshots, method + ".png");
                File screenshotAs = screenshotTakingDriver.getScreenshotAs(OutputType.FILE);
                screens = screenshotTakingDriver.getScreenshotAs(OutputType.BYTES);
                FileUtils.moveFile(screenshotAs, screenshot);
                log.info("Screenshot for class={} method={} saved in: {}", method, screenshot.getAbsolutePath());
            } catch (Exception e1) {
                log.error("Unable to take screenshot", e1);
            }
        } else {
            log.info("Driver '{}' can't take screenshots so skipping it.", driver.getClass());
        }
        return screens;
    }

    public void addScreenShotToAllure(String testName) {
        log.info("add screenshot to allrure.");
        Allure.getLifecycle().addAttachment(testName, "image/png", "png", takeScreenshot(testName));
    }
}