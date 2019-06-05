package pl.sii.base;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import pl.sii.framework.base.Application;

public class BaseTest {

    @BeforeAll
    public static void setUpBeforeAll() {
    }

    @AfterAll
    public static void cleanUpAfterAll() {
        Application.close();
    }
}
