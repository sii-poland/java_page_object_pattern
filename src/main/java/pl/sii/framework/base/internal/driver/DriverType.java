package pl.sii.framework.base.internal.driver;

public enum DriverType {
    CHROME("chrome"),
    FIREFOX("firefox"),
    IE("ie"),
    SAFARI("safari");

    private String driverType;

    DriverType(String driverType) {
        this.driverType = driverType;
    }
}