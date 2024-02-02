package com.jetchiu.supercanteen;

import com.jetchiu.supercanteen.entity.Admin;
import com.jetchiu.supercanteen.mapper.AdminMapper;
import com.jetchiu.supercanteen.mapper.UserMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;

@SpringBootTest
class SupercanteenApplicationTests {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;
    @Value("${keystring}")
     String key;
    @Test
    void contextLoads() {
        Admin admin = new Admin();
        admin.setAccount("1112");
        admin.setName("qzj");
        admin.setPassword("qqqqq");
        admin.setStore_name("mc");
        admin.setSign_date(LocalDate.now());
        System.out.println(adminMapper.insert(admin));
    }

    @Test
    void test2() throws IOException {
//        UsernamePasswordToken token=new UsernamePasswordToken("qzj","1111");
//        token.setRememberMe(true);
//        Subject currentUser= SecurityUtils.getSubject();
//        currentUser.login(token);
//        Path path= Paths.get("/Users/jetchill/IdeaProjects/supercanteen/src/main/resources/static/my.qj");
//        Files.createFile(path);
        System.out.println(key);
        Claims claims=Jwts.claims();
        claims.put("principal","v1");
        String token= Jwts.builder().setClaims(claims).signWith(Keys.hmacShaKeyFor(Base64.getDecoder().decode(key))).compact();
        System.out.println(token);
//        CustomAuthenticationToken customAuthenticationToken=new CustomAuthenticationToken(token,key,userMapper);
//        System.out.println(customAuthenticationToken.getPrincipal());
//        System.out.println(customAuthenticationToken.getCredentials());

    }
    @Test
    void genKey(){

        System.out.println();
        System.out.println(Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded()));
    }
}
