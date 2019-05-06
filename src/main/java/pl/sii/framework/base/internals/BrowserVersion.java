package com.assaabloy.shared.cliq.selenium.test.base;

public enum BrowserVersion {

    NOT_SPECIFIED("unspecified"), IE_9("9"), IE_10("10"), IE_11("11");

    final String text;

    BrowserVersion(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static BrowserVersion fromString(String browserVersion) {
        if (browserVersion != null) {
            for (BrowserVersion version : BrowserVersion.values()) {
                if (version.getText().equals(browserVersion)) {
                    return version;
                }
            }
        }
        return null;
    }

}