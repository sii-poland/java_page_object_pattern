package pl.sii.signin;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Test;
import pl.sii.base.BaseTest;
import pl.sii.framework.base.Application;

public class SignInTest extends BaseTest {


    @Test
    @Feature("Sign in to application")
    public void userShouldBeRedirectedToMainPageWhenCorrectCredentialsProvided() {
        Application.open()
                .signIn();
    }
}