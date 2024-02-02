package com.jetchiu.supercanteen.config;

import com.jetchiu.supercanteen.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserDetails extends User {
    private UserEntity userEntity;
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


    public UserDetails(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        super(userEntity.getAccount(), userEntity.getPassword(), authorities);
        this.userEntity=userEntity;
    }
}
