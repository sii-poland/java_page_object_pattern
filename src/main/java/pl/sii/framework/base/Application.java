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

package pl.sii.framework.base;

import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.WebDriver;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.configuration.Configuration;
import pl.sii.framework.pages.MainPage;

@Slf4j
public class Application extends Page {
    private static Configuration configuration = ConfigFactory.create(Configuration.class);

    public Application(WebDriver webDriver) {
        super(webDriver);
    }

    public MainPage open() {
        String applicationAddress = configuration.applicationAddress();
        log.info("Opening application at: {}", applicationAddress);

        driver.get(applicationAddress);
        return pageFactory.create(MainPage.class);
    }

    public void close() {
        log.info("Closing application");
        driver.quit();
    }
}