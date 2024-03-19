package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.DTO.UserDTO;
import com.jetchiu.supercanteen.entity.Admin;
import com.jetchiu.supercanteen.mapper.AdminMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
AdminMapper adminMapper;
    @Value("${keystring}")
    String key;
    @PostMapping
    public Res DoAdminLogin(@RequestBody Admin admin)
    {
        System.out.println("接收到的管理员账户"+admin.getAccount());
        System.out.println("接收到的管理员密码"+admin.getPassword());
        QueryWrapper<Admin>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",admin.getAccount());
         Admin admin1= adminMapper.selectOne(queryWrapper);
        System.out.println("查询到的管理员密码"+admin1.getPassword());
              if(admin1==null)
                  return Res.Error(null,"未找到该用户");
              if(!Objects.equals(admin1.getPassword(), admin.getPassword()))
                  return Res.Error(null,"密码错误");
        Claims claims= Jwts.claims();
        claims.put("identity","admin");
        claims.put("account",admin1.getAccount());
        String token=Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, key).setExpiration(new Date(System.currentTimeMillis() + 86400000)).compact();
        UserDTO userDTO=new UserDTO(admin1.getAccount(),token,admin1.getStoreName());

        return Res.OK(userDTO);
    }

    @GetMapping
    public Res GetAdminInfo(@RequestParam("account") String account){
        System.out.println("接收到的account"+account);
        QueryWrapper<Admin>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",account);
      Admin admin=adminMapper.selectOne(queryWrapper);
        System.out.println("查询到的"+admin.getStoreName());
      return Res.OK(admin);
    }
}
