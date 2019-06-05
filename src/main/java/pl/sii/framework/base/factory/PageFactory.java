package pl.sii.framework.base.factory;

import lombok.extern.slf4j.Slf4j;
import pl.sii.framework.base.component.Page;

@Slf4j
public class PageFactory {

    public static <T extends Page> T create(Class<T> classToProxy) {
        log.debug("Creating page object: {}", classToProxy.getSimpleName());
        return org.openqa.selenium.support.PageFactory.initElements(
                DriverManagerFactory.getManager()
                        .getDriver(),
                classToProxy);
    }
}