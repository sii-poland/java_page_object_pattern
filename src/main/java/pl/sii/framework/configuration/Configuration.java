package pl.sii.framework.configuration;

import org.aeonbits.owner.Config;

public interface Configuration extends Config {

    String applicationAddress();

    String gridHubUrl();

    String browserName();
}
