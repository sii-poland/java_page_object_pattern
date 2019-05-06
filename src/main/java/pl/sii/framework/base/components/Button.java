package pl.sii.framework.base.components;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Button extends Element {

    private static final Logger LOGGER = LoggerFactory.getLogger(Button.class);

    public Button(WebElement element) {
        super(element);
    }
}