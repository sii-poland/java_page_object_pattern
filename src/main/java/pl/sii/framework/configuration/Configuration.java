package pl.sii.framework.configuration;

import org.aeonbits.owner.Config;

public interface Configuration extends Config {

    @DefaultValue("http://5.196.7.235")
    String applicationAddress();

    String gridHubUrl();

    @DefaultValue("chrome")
    String browserName();
}
