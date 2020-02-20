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

package pl.sii.framework.base.factory;

import io.github.bonigarcia.wdm.DriverManagerType;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pl.sii.framework.base.factory.loader.ChromeDriverLoader;
import pl.sii.framework.base.factory.loader.FirefoxDriverLoader;

import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class LocalDriverFactory implements IDriverFactory {

    @Override
    public WebDriver getDriver() {
        DriverManagerType driverType;
        WebDriver driver;
        try {
            driverType = DriverManagerType.valueOf(configuration.browserName().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Wrong browserName, supported browsers:\n" +
                    Arrays.toString(
                            Stream.of(DriverManagerType.values())
                                    .map(DriverManagerType::name)
                                    .toArray(String[]::new)));
        }
        switch (driverType) {
            case CHROME:
                ChromeDriverLoader.load();
                driver = new ChromeDriver((ChromeOptions) BrowserOptionsFactory.getOptions());
                break;
            case FIREFOX:
                FirefoxDriverLoader.load();
                driver = new FirefoxDriver((FirefoxOptions) BrowserOptionsFactory.getOptions());
                break;
            default:
                log.warn("Browser not provided, using default one");
                ChromeDriverLoader.load();
                driver = new ChromeDriver((ChromeOptions) BrowserOptionsFactory.getOptions());
                break;
        }
        return driver;
    }
}