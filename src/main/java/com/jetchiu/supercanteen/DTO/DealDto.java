package com.jetchiu.supercanteen.DTO;

import lombok.Data;

@Data
public class DealDto {
    int cartId;
    int count;
    int dishId;
    String name;
    double price;
}
