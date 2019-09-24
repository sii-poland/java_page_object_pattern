package pl.sii.base;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

@Slf4j

public class TestExtensions implements TestWatcher, AfterAllCallback {

    @Override
    public void testDisabled(ExtensionContext extensionContext, Optional<String> optional) {
    }

    @Override
    public void testSuccessful(ExtensionContext extensionContext) {
    }

    @Override
    public void testAborted(ExtensionContext extensionContext, Throwable throwable) {
    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        log.info("testFailed method called.");
        ((BaseTest) extensionContext.getTestInstance().get())
                .addScreenShotToAllure(extensionContext.getDisplayName());
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        log.info("After all callbacks");
    }
}