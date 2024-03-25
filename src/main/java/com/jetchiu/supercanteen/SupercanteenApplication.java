package com.jetchiu.supercanteen;

import com.jetchiu.supercanteen.DTO.Res;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.jetchiu.supercanteen.mapper")
@EnableScheduling
public class SupercanteenApplication {

    public static void main(String[] args) {

        SpringApplication.run(SupercanteenApplication.class, args);

    }

}
