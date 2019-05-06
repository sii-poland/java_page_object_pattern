package com.assaabloy.shared.cliq.selenium.framework.model.base.component;

import com.assaabloy.shared.cliq.selenium.framework.utils.Dictionary;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ByIdOrName;

public class Locator {

    private final By by;

    private Locator(By by) {
        this.by = by;
    }

    public static Locator id(String id) {
        return new Locator(By.id(Dictionary.translate(id)));
    }

    public static Locator css(String css) {
        return new Locator(By.cssSelector(Dictionary.translate(css)));
    }

    public static Locator xpath(String xpath) {
        return new Locator(By.xpath(Dictionary.translate(xpath)));
    }

    public static Locator tag(String tag) {
        return new Locator(By.tagName(Dictionary.translate(tag)));
    }

    public static Locator className(String className) {
        return new Locator(By.className(Dictionary.translate(className)));
    }

    public By by() {
        return this.by;
    }

    public String toString() {
        return "Locator: [" + by.toString() + "]";
    }

    public static Locator linkText(String linkText) {
        return new Locator(By.linkText(Dictionary.translate(linkText)));
    }

    public static Locator name(String name) {
        return new Locator(By.name(Dictionary.translate(name)));
    }

    public static Locator partialLinkText(String linkText) {
        return new Locator(By.partialLinkText(Dictionary.translate(linkText)));
    }

    public static Locator idOrName(String idOrName) {
        return new Locator(new ByIdOrName(Dictionary.translate(idOrName)));
    }
}