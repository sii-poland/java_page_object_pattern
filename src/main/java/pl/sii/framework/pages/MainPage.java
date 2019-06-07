package pl.sii.framework.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pl.sii.framework.base.component.Page;

@Slf4j
public class MainPage extends Page {

    @FindBy(css = "#_desktop_user_info > div > a")
    WebElement signInLink;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public SignInPage signIn() {
        log.info("Go to 'Sign in' page");
        signInLink.click();
        return pageFactory.create(SignInPage.class);
    }
}