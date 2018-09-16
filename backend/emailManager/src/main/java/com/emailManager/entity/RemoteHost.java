package com.emailManager.entity;

import com.emailManager.enums.SUCCESS;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Each object is stored in a Cookie/Jwt token and sent
 * back to the client side to be stored and retrieved for each visit.
 */
@Entity
public class RemoteHost implements Serializable{
    private static final long serialversionUID = 17234567L;
    @Id
    private String remoteIpAddr;
    private String newIpAddr;
    private String remoteHost;
    private long numVisits ;
    private int todaysVisits;
    private long firstVisit;
    private long lastVisited;
    private int admin;

    public RemoteHost() {
        this.numVisits = 0;
        this.todaysVisits = 0;
        this.firstVisit = Instant.now().getEpochSecond();
        this.lastVisited = this.firstVisit;
        this.admin = 0;
    }

    public int getTodaysVisits() {
        if(isNewDay()) {
            this.todaysVisits = 1;
        }
        return todaysVisits;
    }

    public void setTodaysVisits(int todaysVisits) {
        this.todaysVisits = todaysVisits;
    }

    public long getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(long lastVisited) {
        this.lastVisited = lastVisited;
    }

    public int isAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public String getRemoteIpAddr() {
        return remoteIpAddr;
    }

    public void setRemoteIpAddr(String remoteIpAddr) {
        this.remoteIpAddr = remoteIpAddr;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public long getNumVisits() {
        return numVisits;
    }

    public void setNumVisits(long numVisits) {
        this.numVisits = numVisits;
    }

    public long getFirstVisit() {
        return firstVisit;
    }

    public void setFirstVisit(long firstVisit) {
        this.firstVisit = firstVisit;
    }

    public String getNewIpAddr() {
        return newIpAddr;
    }

    public void setNewIpAddr(String newIpAddr) {
        this.newIpAddr = newIpAddr;
    }


    public void resetLastVisited(){
        this.lastVisited = Instant.now().getEpochSecond();
    }

    public void incrementVisits(){
        this.todaysVisits = (!isNewDay())? ++this.todaysVisits:1;
        this.numVisits++;
    }


    private boolean isNewDay(){
        String pattern = SUCCESS.DATE_FORMAT.toString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if(!simpleDateFormat.format(new Date(this.lastVisited * 1000)).equals(
                simpleDateFormat.format(new Date(Instant.now().getEpochSecond() * 1000)))) {

            return true;
        }
        return false;
    }


    public int getAdmin() {
        return admin;
    }

    @Override
    public String toString() {
        return "RemoteHost{" +
                "remoteIpAddr='" + remoteIpAddr + '\'' +
                ", newIpAddr='" + newIpAddr + '\'' +
                ", remoteHost='" + remoteHost + '\'' +
                ", numVisits=" + numVisits +
                ", todaysVisits=" + todaysVisits +
                ", firstVisit=" + firstVisit +
                ", lastVisited=" + lastVisited +
                ", admin=" + admin +
                '}';
    }

    public static long getSerialversionUID() {
        return serialversionUID;
    }

}
