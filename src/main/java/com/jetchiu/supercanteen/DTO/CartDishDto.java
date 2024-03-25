package com.jetchiu.supercanteen.DTO;

import lombok.Data;

@Data
public class CartDishDto {
    int dishId;
    int count;
    String dishName;
    double dishPrice;
    String picture;
    int cartId;
}
