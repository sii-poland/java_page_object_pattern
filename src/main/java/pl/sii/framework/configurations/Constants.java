package com.assaabloy.shared.cliq.selenium.framework.configuration;

public interface Constants {
    int ACTION_TIMEOUT_IN_SECONDS = 20; //only for test purposes it should be set to e.g. 5 seconds
    int ACTION_TIMEOUT_IN_SECONDS_FOR_PDF_OPENING = 20;
    int ACTION_TIMEOUT_IN_SECONDS_FOR_MAIL_RECEIVING = 10;
    int NUMBER_OF_REPETITIONS_IN_STABILITY_CHECK = 50;
    int DOWNLOAD_TIME_IN_MILLIS = 1000;
    int ACTION_TIMEOUT_FIVE_SECONDS = 5000;
    int READ_AUDIT_TRAIL_WAIT_TIME_SECONDS = 10;
    int READ_AUDIT_TRAIL_CWM_REFRESH_TIME_SECONDS = 240;
    int REPROGRAM_CYLINDER_WAIT_TIME_SECONDS = 10;
    int PENDING_UPDATES_REMOTE_PD_WAIT_TIME_SECONDS = 10;
    int REMOTE_PD_HEARTBEAT_MIN = 70;
    int REMOTE_PD_DEFAULT_DELAY = 5;
}