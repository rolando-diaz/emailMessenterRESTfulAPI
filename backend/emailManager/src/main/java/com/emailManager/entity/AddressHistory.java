package com.emailManager.entity;

import com.emailManager.enums.ENUM;
import com.emailManager.enums.FAILED;
import com.emailManager.enums.SUCCESS;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Entity class to be stored as a history record for each email address.
 */
@Entity
@Table(name = "addressHistory")
public class AddressHistory implements Serializable {
    @Transient
    private static final long serialversionUID = 12374567L;
    @Id
    private String email;
    private String name;
    private long todaysCount = 0;
    private long totalCount = 0;
    private long lastVisited;
    @Transient
    private final int SENDER_DAILY_MAX_MSG = 10;

    public AddressHistory() {}

    public AddressHistory(String email, String name) {
        this.email = email;
        this.name = name;
        this.lastVisited = Instant.now().getEpochSecond();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public final int getSENDER_DAILY_MAX_MSG(){
        return this.SENDER_DAILY_MAX_MSG;
    }

    public void setTodaysCount(long todaysCount) {
        this.todaysCount = todaysCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(long lastVisited) {
        this.lastVisited = lastVisited;
    }
    public void updateLastVisited(){
        setLastVisited(Instant.now().getEpochSecond());
    }

    public void incrementCount(){
        this.todaysCount++;
        this.totalCount++;
    }

    public ENUM dailyMaxMsg() {
        if(this.getTodaysCount() >= this.SENDER_DAILY_MAX_MSG)
            return FAILED.EMAIL_ADDRESS_DAILY_MAX_EXCEEDED;
        return SUCCESS.THANKS;
    }

    public long getTodaysCount() {
        String pattern = SUCCESS.DATE_FORMAT.toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        if(!simpleDateFormat.format(new Date(this.lastVisited * 1000)).equals(
                simpleDateFormat.format(new Date(Instant.now().getEpochSecond() * 1000))))
            {
                this.todaysCount = 0;
            }
        return todaysCount;
    }
}
