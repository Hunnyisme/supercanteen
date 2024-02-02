package com.jetchiu.supercanteen.entity;

import lombok.Data;

@Data
public class Dish {
long id;
String name;
double price;
long related_cate;
}
