package com.emailManager.dao;
import com.emailManager.entity.AddressHistory;
import com.emailManager.entity.Message;
import com.emailManager.repository.AddressHistoryRepo;
import com.emailManager.repository.MemcachedRepo;

public class HistoryThread implements Runnable{

    private MemcachedRepo memcachedRepo;
    private AddressHistoryRepo addrHistRepo;
    private AddressHistory addrHistory;
    public String email;
    private String name;

    /**
     * @param memcached
     * @param addrRepo
     * @param addrHist
     * @param msg
     */
    public HistoryThread(MemcachedRepo memcached, AddressHistoryRepo addrRepo, AddressHistory addrHist, Message msg) {
        this.memcachedRepo = memcached;
        this.addrHistRepo = addrRepo;
        this.addrHistory = addrHist;
        this.email = msg.getEmail();
        this.name = msg.getName();
    }

    /**
     * addressHistory obj: it is saved locally in Memcached and in the * sql table.
     */
    @Override
    public void run(){
        try{
            addrHistory.updateLastVisited();
            addrHistory.incrementCount();

            memcachedRepo.saveAddressHistory(addrHistory);
            addrHistRepo.save(addrHistory);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}

