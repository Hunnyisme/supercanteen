package com.jetchiu.supercanteen.DTO;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class DealDto2 {
    String storeName;
    String dealId;
    double amountCount;
    int storeId;
    int userId;
    String userName;
    Timestamp dateTime;
}
