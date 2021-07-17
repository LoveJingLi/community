package com.miao.community_m;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com/miao/community_m/mapper")
//@EnableScheduling
public class CommunityMApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommunityMApplication.class, args);
    }

}
