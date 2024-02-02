package com.jetchiu.supercanteen.commonservice;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.entity.UserEntity;
import com.jetchiu.supercanteen.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;

@Service
public class UserServiceImp implements UserService, UserDetailsService {
    @Autowired
    UserMapper userMapper;
    public UserServiceImp(UserMapper userMapper){
     this.userMapper=userMapper;
    }
    @Override
    public boolean CheckExist(UserEntity userEntity) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("account", userEntity.getAccount());
        if(userMapper.selectOne(queryWrapper)!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean AddUser(UserEntity userEntity) {
        userEntity.setSign_date(LocalDate.now());
        int status= userMapper.insert(userEntity);
        return status != 0;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UserEntity>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",username);
        UserEntity user=userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("没找到该用户");
        }
        return new com.jetchiu.supercanteen.config.UserDetails(user, Collections.emptyList());
    }

    @Override
    public UserEntity SelectUserByAccount(String account) {
        QueryWrapper<UserEntity>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("account",account);
        UserEntity userEntity1=userMapper.selectOne(queryWrapper);
        return userEntity1;
    }
}
