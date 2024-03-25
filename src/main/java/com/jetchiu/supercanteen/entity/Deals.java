package com.jetchiu.supercanteen.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Deals {
String id;
double amountCount;
int storeId;
int userId;
Timestamp dateTime;
}
