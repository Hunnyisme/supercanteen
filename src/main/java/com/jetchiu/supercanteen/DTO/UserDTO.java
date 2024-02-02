package com.jetchiu.supercanteen.DTO;

import com.jetchiu.supercanteen.entity.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    public UserDTO(UserEntity userEntity, String tokens) {
        this.userEntity = userEntity;
        this.tokens = tokens;
    }

    UserEntity userEntity;
     String tokens;

}
