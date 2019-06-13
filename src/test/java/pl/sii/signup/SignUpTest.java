package pl.sii.signup;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pl.sii.base.BaseTest;

@Execution(ExecutionMode.CONCURRENT)
public class SignUpTest extends BaseTest {

    @Test
    public void userShouldBeAbleToSignUpWhenFormFilledCorrectly() {
        application.open(); //TODO
    }
}
