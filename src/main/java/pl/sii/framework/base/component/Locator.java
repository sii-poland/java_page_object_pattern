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

import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;

public class Locator {

    private final By by;

    private Locator(By by) {
        this.by = by;
    }

    public static Locator id(String id) {
        return new Locator(By.id(id));
    }

    public static Locator css(String css) {
        return new Locator(By.cssSelector(css));
    }

    public static Locator xpath(String xpath) {
        return new Locator(By.xpath(xpath));
    }

    public static Locator tag(String tag) {
        return new Locator(By.tagName(tag));
    }

    public static Locator className(String className) {
        return new Locator(By.className(className));
    }

    public By by() {
        return this.by;
    }

    public String toString() {
        return "Locator: {" + by.toString() + "}";
    }

    public static Locator linkText(String linkText) {
        return new Locator(By.linkText(linkText));
    }

    public static Locator name(String name) {
        return new Locator(By.name(name));
    }

    public static Locator partialLinkText(String linkText) {
        return new Locator(By.partialLinkText(linkText));
    }

    public static Locator idOrName(String idOrName) {
        return new Locator(new ByIdOrName(idOrName));
    }
}