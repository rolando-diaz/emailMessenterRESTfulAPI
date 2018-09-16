package com.emailManager.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This is a Record object to be stored in our sql message table,
 * which uses composite primary keys through the embeddable Message object.
 */
@Entity
@Table(name="message")
public class Record implements Serializable {
    @Transient
    private static final long serialversionUID = 1234567L;
    @EmbeddedId
    public Message message;
    private String remoteAddr;
    private String remoteHost;

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public boolean invalidParams(){
        return this.message.invalidParams();
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Record{" +
                "message=" + message.toString() +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", remoteHost='" + remoteHost + '\'' +
                '}';
    }
}
