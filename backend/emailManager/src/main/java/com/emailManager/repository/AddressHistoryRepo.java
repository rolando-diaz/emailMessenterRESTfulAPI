package com.emailManager.repository;

import com.emailManager.entity.AddressHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface AddressHistoryRepo extends CrudRepository<AddressHistory, String> {
    /**
     * This area is for JPA custom methods
     */
}
