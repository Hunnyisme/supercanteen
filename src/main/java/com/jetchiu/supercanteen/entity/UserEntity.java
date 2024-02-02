package com.jetchiu.supercanteen.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserEntity {
    long id;
    String name;
    String account;
    String password;
    LocalDate sign_date;
    String icon_address;
    String phone;
    String email;
}
