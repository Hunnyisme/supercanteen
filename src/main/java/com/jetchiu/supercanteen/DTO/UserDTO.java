package com.jetchiu.supercanteen.DTO;

import com.jetchiu.supercanteen.entity.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    String account;
    String token;
    public UserDTO(String account, String tokens) {
        this.account = account;
        this.token = tokens;
    }



}
