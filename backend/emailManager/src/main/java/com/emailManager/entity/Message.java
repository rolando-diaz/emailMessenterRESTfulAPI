package com.emailManager.entity;

import com.emailManager.enums.SUCCESS;
import com.fasterxml.uuid.Generators;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
/**
 * This in an embeddable object, so we can use all its variables as composite
 * primary keys in our Sql message table.
 */
@Embeddable
public class Message implements Serializable {
    @Transient
    private static final long serialversionUID = 12134567L;
    public String email;
    private String name;
    private String body;
    private String frontEndBackDoorKey;
    private String timeUUID;

    public Message(){
        timeUUID = Generators.timeBasedGenerator().generate().toString();
        }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeUUID() {
        return timeUUID;
    }

    public void setTimeUUID(String timeUUID) {
        this.timeUUID = timeUUID;
    }

    public String getFrontEndBackDoorKey() {
        return frontEndBackDoorKey;
    }

    public void setFrontEndBackDoorKey(String frontEndBackDoorKey) {
        this.frontEndBackDoorKey = frontEndBackDoorKey;
    }

    public boolean invalidParams(){
        return this.name == null || this.email == null || this.body == null;
    }

    public boolean fromInvalidSource() {
        if(frontEndBackDoorKey == null || !frontEndBackDoorKey.equals(SUCCESS.SECRET_KEY.toString()))
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Message{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", body='" + body + '\'' +
                ", frontEndBackDoorKey='" + frontEndBackDoorKey + '\'' +
                ", timeStamp=" + timeUUID +
                '}';
    }
}
