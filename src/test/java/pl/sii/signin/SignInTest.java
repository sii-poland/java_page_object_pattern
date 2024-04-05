/*
 * Copyright (c) 2019.  Sii Poland
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.sii.signin;

import io.qameta.allure.Feature;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import pl.sii.base.BaseTest;

import static org.assertj.core.api.Assertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class SignInTest extends BaseTest {

    @Test
    @DisplayName("Sign in with incorrect credentials.")
    @Tag("signin")
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