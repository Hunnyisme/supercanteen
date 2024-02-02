package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.DTO.UserDTO;
import com.jetchiu.supercanteen.commonservice.UserService;
import com.jetchiu.supercanteen.entity.UserEntity;
import com.jetchiu.supercanteen.mapper.UserMapper;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    UserMapper userMapper;
    @Value("${keystring}")
    String key;

    UserService userService;
         UserController(UserMapper userMapper,UserService userService){
            this.userMapper=userMapper;
            this.userService=userService;

         }
@PostMapping("/sign")
    public Res UserSignin(@RequestBody UserEntity userEntity){
    if(userService.CheckExist(userEntity)){
        return Res.Error(null,"账号已存在");
    }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userService.AddUser(userEntity)?Res.OK(null):Res.Error(null,"内部错误");
    }
    @GetMapping
    public Res GetUserInfo( @RequestHeader String Authorization){
        System.out.println("令牌是"+Authorization);
        return Res.Error("","");
    }

    @PostMapping("/log")

    public Res DoUserLogin(@RequestBody UserEntity userEntity){
             //session版逻辑
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userEntity.getAccount(),userEntity.getPassword());
//      Authentication authentication= authenticationManager.authenticate(usernamePasswordAuthenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//     Authentication authentication1=   SecurityContextHolder.getContext().getAuthentication();
//
        //JWT版逻辑

        UserEntity userEntity1=userService.SelectUserByAccount(userEntity.getAccount());
        if(userEntity1==null|| passwordEncoder.matches(userEntity1.getPassword(),userEntity.getPassword()))
         return Res.Error(null,"账号或密码错误");
        Claims claims=Jwts.claims();
        claims.put("account",userEntity1.getAccount());
      String token=Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, key).setExpiration(new Date(System.currentTimeMillis() + 86400000)).compact();
        UserDTO userDTO=new UserDTO(userEntity1,token);
        return Res.OK(userDTO);
    }

}
