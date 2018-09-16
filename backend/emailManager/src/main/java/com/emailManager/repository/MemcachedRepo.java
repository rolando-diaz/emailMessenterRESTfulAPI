package com.emailManager.repository;

import com.emailManager.entity.AddressHistory;
import com.emailManager.enums.FAILED;
import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * This class establishes connection to the local Memcached,
 * in order to store and quick retrieve objects
 */
@Service
public class MemcachedRepo {
    private MemcachedClient memcachedClient;

    public MemcachedRepo(){
        connect();
    }

    public void connect(){
        try {
            this.memcachedClient  = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
        } catch (IOException e) {
            System.out.println(FAILED.MEM_CACHED_ERROR.toString());
        }
    }

    public void saveAddressHistory(AddressHistory addressHistory){
        if(addressHistory != null){
            memcachedClient.set(addressHistory.getEmail(),10000, addressHistory);
        }
    }

    public AddressHistory getAddressHistory(String emailAddress){
        return (AddressHistory)memcachedClient.get(emailAddress);
    }

    public void deleteAddressHistory(String emailAddress) {
        memcachedClient.delete(emailAddress);

    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}
