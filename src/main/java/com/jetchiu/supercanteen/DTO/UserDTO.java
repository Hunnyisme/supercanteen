package com.jetchiu.supercanteen.DTO;

import com.jetchiu.supercanteen.entity.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    String account;
    String token;
    String storeName;
    public UserDTO(String account, String tokens,String storeName) {
        this.account = account;
        this.token = tokens;
        this.storeName=storeName;
    }



}
