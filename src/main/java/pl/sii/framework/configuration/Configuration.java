package pl.sii.framework.configuration;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:configuration.properties")
public interface Configuration extends Config {

    String applicationAddress();

    String gridHubUrl();

    String browserName();

    String driverType();
}
