package com.jetchiu.supercanteen.entity;

import lombok.Data;

@Data
public class Deals_details {
    long id;
    long deals_id;
    String dish_name;
    int quantity;
    double single_price;
    double subtotal;
}
