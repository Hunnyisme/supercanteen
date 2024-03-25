package com.jetchiu.supercanteen.DTO;

import lombok.Data;

@Data
public class EvaDto {
    int id;
    int storeId;
    String dealId;
    int envScore;
    int serScore;
    int fooScore;
    int hygScore;
    String textComment;
    int userId;
    String userName;
}
