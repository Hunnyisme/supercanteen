package com.jetchiu.supercanteen.entity;

import lombok.Data;

@Data
public class Evaluate {
    int id;
    int storeId;
    String dealId;
    int envScore;
    int serScore;
    int fooScore;
    int hygScore;
    String textComment;
     int userId;
}
