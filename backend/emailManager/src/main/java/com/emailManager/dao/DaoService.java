package com.emailManager.dao;

import com.emailManager.entity.AddressHistory;
import com.emailManager.entity.Message;
import com.emailManager.logicService.IpParser;
import com.emailManager.repository.AddressHistoryRepo;
import com.emailManager.repository.MemcachedRepo;
import com.emailManager.repository.MysqlCrudRepo;
import com.emailManager.security.CookieJasonWebToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public class DaoService {

    @Autowired
    private AddressHistoryRepo addressHistoryRepo;
    @Autowired
    private MemcachedRepo memcachedRepo;
    @Autowired
    private MysqlCrudRepo mysqlCrudRepo;
    @Autowired
    IpParser ipParser;
    @Autowired
    CookieJasonWebToken cookieJasonWebToken;

    private String remoteAddr;
    private String remoteHost;

    /**
     * if the emailHistory Key exists in mySql db then the threads run Asynchronous/parallel,
     * else t2 must wait for t1 to create the corresponding key due to a REFERENCE constraint.
     * @param request
     * @param emailHistory
     * @param message
     */
    public void updateDB(HttpServletRequest request, AddressHistory emailHistory, Message message) {
        Thread t1 = saveEmailHistory(message, emailHistory);
        Thread t2 = saveMessage(request, message);
        t1.start();
        if(emailHistory == null){
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t2.start();
    }

    /**
     * @param message
     * @param emailHistory
     * @return Thread object to update MySql db
     */
    public Thread saveEmailHistory( Message message, AddressHistory emailHistory) {
        AddressHistory eH = (emailHistory!=null)?emailHistory:new AddressHistory(message.getEmail(), message.getName());
        return new Thread(new HistoryThread(memcachedRepo, addressHistoryRepo, eH, message));
    }

    /**
     * @param request
     * @param message
     * @return a Thread obj to update MySql db
     */
    public Thread saveMessage(HttpServletRequest request, Message message){
        this.remoteAddr = ipParser.getClientIpAddress(request);
        this.remoteHost = request.getRemoteHost();
        return new Thread(new RecordThread(mysqlCrudRepo, message, remoteAddr, remoteHost));
    }


    /**
     * Searching for the email history in our local MEMCACHED system,
     * otherwise it queries MySql db.
     * @param email
     */
    public AddressHistory getEmailHistory(String email) {
        try{
            AddressHistory emailHistory = memcachedRepo.getAddressHistory(email);
            if(emailHistory == null){
                emailHistory = addressHistoryRepo.findOne(email);
            }
            return emailHistory;
        }catch (Exception e){}
        return null;
    }


}
