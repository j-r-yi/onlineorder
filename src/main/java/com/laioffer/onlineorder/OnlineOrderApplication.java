package com.laioffer.onlineorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

// Annotation, for the compiler
// starting from this class (OnlineOrderApplication), it all belongs to SpringBootApplication
@SpringBootApplication
@EnableCaching
public class OnlineOrderApplication {

    public static void main(String[] args) {
        // Use springboot to run this class with args
        SpringApplication.run(OnlineOrderApplication.class, args);
    }

}
