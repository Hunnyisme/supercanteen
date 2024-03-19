package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.entity.Dish;
import com.jetchiu.supercanteen.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishMapper dishMapper;
    @GetMapping
    public Res GetDish(@RequestParam int id){
        QueryWrapper<Dish> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("related_cate",id);
     List<Dish>dishes= dishMapper.selectList(queryWrapper);
     for( Dish dish:dishes)
     {
         System.out.println(dish.getName());
     }
        return Res.OK(dishes);
    }
    @PostMapping
    public Res AddDish(@RequestBody Dish dish){

        System.out.println("接受到的dish"+dish.getName());
              dishMapper.insert(dish);
        return Res.OK(null);
    }
    @DeleteMapping
    public Res DeleteDish(@RequestParam int id){
        dishMapper.deleteById(id);
        return Res.OK(null);
    }
    @GetMapping("/all")
    public Res GetAllDishs(){
        
        return Res.OK(dishMapper.selectList(new QueryWrapper<Dish>()));
    }
}
