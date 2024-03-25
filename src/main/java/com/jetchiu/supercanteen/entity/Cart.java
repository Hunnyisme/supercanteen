package com.jetchiu.supercanteen.entity;

import lombok.Data;

@Data
public class Cart {
    int id;
    int dishId;
    int count;
    int ofUser;

}
