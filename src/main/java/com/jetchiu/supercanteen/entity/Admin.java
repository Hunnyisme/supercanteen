package com.jetchiu.supercanteen.entity;

import lombok.Data;

import java.time.LocalDate;


@Data
public class Admin {
  long id;
  String name;
  String account;
  String password;
  String storeName;
  LocalDate signDate;
}
