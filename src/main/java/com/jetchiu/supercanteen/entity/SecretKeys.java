package com.jetchiu.supercanteen.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SecretKeys {
    String id;
    String key_string;
    Timestamp add_date;
}
