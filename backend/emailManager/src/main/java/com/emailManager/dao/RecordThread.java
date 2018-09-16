package com.emailManager.dao;

import com.emailManager.entity.Message;
import com.emailManager.entity.Record;
import com.emailManager.repository.MysqlCrudRepo;

public class RecordThread implements Runnable {
    private MysqlCrudRepo mysqlCrudRepo;
    private Record newRecord;
    private String remoteAddr;
    private String remoteHost;

    public RecordThread(MysqlCrudRepo mysqlCrudRepo, Message message, String remoteAddr, String remoteHost) {
        this.mysqlCrudRepo = mysqlCrudRepo;
        this.newRecord = new Record();
        this.newRecord.setMessage(message);
        this.remoteAddr = remoteAddr;
        this.remoteHost = remoteHost;
    }

    /**
     * Saves an incoming message in MySql db to keep a record.
     */
    @Override
    public void run() {
        try{
            newRecord.setRemoteAddr(this.remoteAddr);
            newRecord.setRemoteHost(this.remoteHost);
            mysqlCrudRepo.save(newRecord);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
