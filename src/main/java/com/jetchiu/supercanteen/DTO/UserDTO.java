package com.jetchiu.supercanteen.DTO;

import com.jetchiu.supercanteen.entity.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    String account;
    String token;
    String storeName;
    int id;
    public UserDTO(String account, String tokens,String storeName,int id) {
        this.account = account;
        this.token = tokens;
        this.storeName=storeName;
        this.id=id;
    }



}
