package com.SpringExaminationSystem;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.SpringExaminationSystem")
public class CommonTestApplication {
    static {
        System.setProperty("dev.secret", 
          new String(java.util.Base64.getDecoder().decode("ZGV2LXNlY3JldC1jaGFuZ2UtbWUtcGxlYXNlLWV4dGVuZC0xMjM0NTY=")));
    }
}
