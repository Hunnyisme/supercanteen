package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.DTO.EvaDto;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.entity.Evaluate;
import com.jetchiu.supercanteen.mapper.EvaMapper;
import com.jetchiu.supercanteen.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/eva")
public class Evacontroller {
@Autowired
    private EvaMapper evaMapper;
@Autowired
private UserMapper userMapper;
@PostMapping
    public Res PostEvaluation(@RequestBody Evaluate evaluate){
          evaMapper.insert(evaluate);

return Res.OK(null);
}
@GetMapping
public Res GetEva(@RequestParam int storeId){
    QueryWrapper<Evaluate>queryWrapper=new QueryWrapper<>();
         queryWrapper.eq("store_id",storeId);
           List<Evaluate> evaluateList=evaMapper.selectList(queryWrapper);
                    List<EvaDto>evaDtoList=new ArrayList<>();
                    for(Evaluate e:evaluateList){
                        EvaDto dto=new EvaDto();
                        BeanUtils.copyProperties(e,dto);
                        dto.setUserName(userMapper.selectById(e.getUserId()).getName());
                        evaDtoList.add(dto);
                    }

    return Res.OK(evaDtoList);
}

}
