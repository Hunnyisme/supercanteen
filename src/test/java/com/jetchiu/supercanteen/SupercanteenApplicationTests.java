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
}

