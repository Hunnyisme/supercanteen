package com.jetchiu.supercanteen.entity;

import lombok.Data;

@Data
public class Store {
    int id;
    String name;
    String address;
    String profile;
    String iconAddress;
    int overallScore;
  int  foodScore;
  int envScore;
  int serScore;
    int hygScore;
}
