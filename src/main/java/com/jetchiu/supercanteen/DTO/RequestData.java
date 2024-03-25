package com.jetchiu.supercanteen.DTO;

import lombok.Data;

import java.util.List;

@Data
public class RequestData {
    List<DealDto> dealDtos;
    int userid;
}
