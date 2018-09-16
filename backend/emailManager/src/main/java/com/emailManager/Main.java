package com.emailManager;

import net.spy.memcached.ConnectionFactory;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.btmatthews.springboot.memcached.EnableMemcached;
import org.springframework.context.annotation.Bean;

/**
 * Springboot application main class.
 */
@SpringBootApplication
@EnableMemcached
public class Main{
    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }


    @Bean
    public ConnectionFactory memcachedConnection() {
        return new ConnectionFactoryBuilder()
                .setDaemon(true)
                .setFailureMode(FailureMode.Cancel)
                .build();
    }
}
