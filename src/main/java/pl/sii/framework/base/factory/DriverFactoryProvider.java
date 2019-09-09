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

package pl.sii.framework.base.factory;

import lombok.extern.slf4j.Slf4j;
import org.aeonbits.owner.ConfigFactory;
import pl.sii.framework.configuration.Configuration;

@Slf4j
public class DriverFactoryProvider {
    private static Configuration configuration = ConfigFactory.create(Configuration.class);

    public IDriverFactory getDriverFactory() {
        String driverType = configuration.driverType();
        IDriverFactory driverFactory;

        switch (driverType) {
            case "LOCAL":
                driverFactory = new LocalDriverFactory();
                break;
            case "REMOTE":
                driverFactory = new RemoteDriverFactory();
                break;
            default:
                throw new IllegalStateException("Wrong driverType, supported types: [LOCAL, REMOTE]");
        }
        return driverFactory;
    }
}