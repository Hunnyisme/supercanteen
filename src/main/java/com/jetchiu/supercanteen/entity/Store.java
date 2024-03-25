package com.jetchiu.supercanteen.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Store {
    @TableId
    int id;
    String name;
    String address;
    String profile;
    String iconAddress;
    double overallScore;
  int  foodScore;
  int envScore;
  int serScore;
    int hygScore;
}
