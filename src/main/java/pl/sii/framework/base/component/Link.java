package pl.sii.framework.base.component;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Link extends Element {

    private static final Logger LOGGER = LoggerFactory.getLogger(Link.class);

    public Link(WebElement element) {
        super(element);
    }
}