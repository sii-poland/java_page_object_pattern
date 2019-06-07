package pl.sii.signin;

import io.qameta.allure.Feature;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pl.sii.base.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class SignInTest extends BaseTest {

    @Test
    @Feature("Sign in to application with incorrect credentials")
    public void errorMessageShouldBeVisibleWhenIncorrectCredentialsProvided() {
        assertThat(
                application.open()
                        .signIn()
                        .withEmail(String.join("@", RandomString.make(), "sii.pl"))
                        .withPassword(RandomString.make())
                        .submitWithoutSuccess()
                        .isAlertMessageDisplayed())
                .isTrue()
                .withFailMessage("Alert message is not displayed");
    }
}