package pl.sii.framework.configuration;

import org.aeonbits.owner.Config;

public interface Configuration extends Config {

    @DefaultValue("http://5.196.7.235")
    String applicationAddress();

    @DefaultValue("http://localhost:5566/wd/hub")
    String gridHubUrl();

    @DefaultValue("FIREFOX")
    String browserName();

    @DefaultValue("REMOTE")
    String driverType();
}
