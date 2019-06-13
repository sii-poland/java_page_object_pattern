package pl.sii.framework.base.factory;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import pl.sii.framework.base.component.Page;

@Slf4j
public class PageFactory {
    private WebDriver driver;

    public PageFactory(WebDriver driver) {
        this.driver = driver;
    }

    public <T extends Page> T create(Class<T> classToProxy) {
        log.debug("Creating page object: {}", classToProxy.getSimpleName());
        return org.openqa.selenium.support.PageFactory.initElements(driver, classToProxy);
    }
}