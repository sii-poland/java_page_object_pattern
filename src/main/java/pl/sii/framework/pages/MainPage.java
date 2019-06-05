package pl.sii.framework.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sii.framework.base.component.Page;
import pl.sii.framework.base.factory.PageFactory;

import static pl.sii.framework.base.internal.ElementWait.await;

@Slf4j
public class MainPage extends Page {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInPage.class);

    @FindBy(css = "#_desktop_user_info > div > a")
    WebElement signInLink;

    public SignInPage signIn() {
        log.info("Go to 'Sign in' page");
        await().untilClickable(signInLink)
                .click();
        return PageFactory.create(SignInPage.class);
    }
}