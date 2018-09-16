package com.emailManager.repository;
import com.emailManager.entity.Message;
import com.emailManager.entity.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;


@Service
public interface MysqlCrudRepo extends CrudRepository<Record, Message> {
    /**
     * JPA custom methods
     */

}
