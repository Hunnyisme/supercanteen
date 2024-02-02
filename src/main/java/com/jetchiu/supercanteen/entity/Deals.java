package com.jetchiu.supercanteen.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Deals {
Long id;
double amount_count;
Long store_id;
long user_id;
Timestamp date_time;
}
