package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.entity.Category;
import com.jetchiu.supercanteen.entity.Dish;
import com.jetchiu.supercanteen.mapper.CategoryMapper;
import com.jetchiu.supercanteen.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dish")
public class DishController {

    @Autowired
    private DishMapper dishMapper;
@Autowired
private CategoryMapper categoryMapper;
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

    @GetMapping("/show")
    public Res GetDishsPlusCate(@RequestParam int storeid){
       QueryWrapper<Category>queryWrapper=new QueryWrapper<>();
       queryWrapper.eq("of_store",storeid);
     List<Category>categoryList=categoryMapper.selectList(queryWrapper);
       List res=new ArrayList();
         for(Category c :categoryList){
             List temlist=new ArrayList();
             temlist.add(c);
             QueryWrapper<Dish>queryWrapper1=new QueryWrapper<>();
             queryWrapper1.eq("related_cate",c.getId());
             temlist.add(dishMapper.selectList(queryWrapper1));
             res.add(temlist);
         }
        return Res.OK(res);
    }


}
