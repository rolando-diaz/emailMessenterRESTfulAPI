package com.emailManager.enums;

/**
 * enum class for success messages
 */
public enum SUCCESS implements ENUM{
    THANKS("Thanks for contacting me!"),
    MESSAGE_FROM("New email from "),
    MyEmail("sampleEmail@yahoo.com"),
    MICRO_SERVICE_ON("Ok, this microservice is running!"),
    STATUS("Status: "),
    DATE_FORMAT("yyyy-MM-dd"),
    MESSAGE_BODY("Thanks for your interest in my portofolio, I'm going to read your email as soon as possible!"),
    SECRET_KEY("secretKey"),
    TOKEN_KEY("secretTokenkey"),
    TOKEN_HEADER("secretTokenHeader"),
    OK("ok"),
    LINK("<a href=\"http://localhost:8081\">www.rdiaz.com</a>");

    public final java.lang.String message;

    SUCCESS(final java.lang.String msg) {
        this.message = msg;
    }

    public java.lang.String toString() { return message; }


}
