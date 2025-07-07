package org.labs.jkcloud.emrs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EmrsSaladahraNlgBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmrsSaladahraNlgBackApplication.class, args);
    }

}
