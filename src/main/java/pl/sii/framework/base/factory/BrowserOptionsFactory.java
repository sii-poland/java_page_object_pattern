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

import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import pl.sii.framework.configuration.Configuration;

import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class BrowserOptionsFactory {

    public static AbstractDriverOptions getOptions() {
        Configuration configuration = ConfigFactory.create(Configuration.class);
        DriverManagerType driverType;
        AbstractDriverOptions options;
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
                options = new ChromeOptions();
                ((ChromeOptions) options).addArguments("start-maximized");
                break;
            case FIREFOX:
                options = new FirefoxOptions();
                break;
            default:
                log.warn("Browser not provided, using default one");
                WebDriverManager.chromedriver().setup();
                options = new ChromeOptions();
                break;
        }
        return options;
    }
}