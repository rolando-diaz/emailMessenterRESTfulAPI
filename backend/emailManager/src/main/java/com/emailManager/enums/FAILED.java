package com.emailManager.enums;

/**
 * enum class for Error responses
 */
public enum FAILED implements ENUM {
    INVALID_PARAMETERS("Invalid Parameters"),
    MEM_CACHED_ERROR("Memcached failed to connect"),
    EMAIL_ADDRESS_DAILY_MAX_EXCEEDED("Daily max emails from this address reached, please retry after midnight"),
    REMOTE_HOST_DAILY_MAX_EXCEEDED("Daily max emails from this remoteHost reached, please try tomorrow"),
    INVALID_EMAIL_ADDRESS("Invalid email address");

    private final String message;

    FAILED(final String msg) {
        this.message = msg;
    }

    public String toString() { return message; }

}
