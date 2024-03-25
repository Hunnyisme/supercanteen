package com.jetchiu.supercanteen.entity;

import lombok.Data;

@Data
public class Deals_details {
    int id;
    String dealsId;
    String dishName;
    int quantity;
    double singlePrice;
    double subtotal;
}
