package pl.sii.framework.pages;

import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sii.framework.base.components.Link;
import pl.sii.framework.base.components.Page;
import pl.sii.framework.base.factories.PageFactory;

import static pl.sii.framework.base.internals.ElementWait.await;

public class MainPage extends Page {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInPage.class);

    @FindBy(xpath = "//*[@id='_desktop_user_info']/div/a")
    Link signInLink;

    public SignInPage signIn() {
        LOGGER.info("Go to 'Sign in' page");
        await().untilClickable(signInLink)
                .click();
        return PageFactory.create(SignInPage.class);
    }
}
