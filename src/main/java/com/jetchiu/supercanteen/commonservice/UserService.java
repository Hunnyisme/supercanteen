package com.jetchiu.supercanteen.commonservice;

import com.jetchiu.supercanteen.entity.UserEntity;

public interface UserService {
   /**
   * 检查用户是否存在
   * */
    public boolean CheckExist(UserEntity userEntity);
    public boolean AddUser(UserEntity userEntity);
    public UserEntity SelectUserByAccount(String account);
}
