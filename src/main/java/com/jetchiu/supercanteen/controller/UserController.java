package com.jetchiu.supercanteen.controller;

import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.commonservice.UserService;
import com.jetchiu.supercanteen.entity.UserEntity;
import com.jetchiu.supercanteen.mapper.UserMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userEntity.getAccount(),userEntity.getPassword());
      Authentication authentication= authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
     Authentication authentication1=   SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication1.getPrincipal());
        System.out.println(authentication1.getName());
        System.out.println(authentication1.getCredentials());
   return Res.OK("登陆成功");
    }

}
