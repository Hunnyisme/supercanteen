package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.DTO.CateDto;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.entity.Category;
import com.jetchiu.supercanteen.entity.Store;
import com.jetchiu.supercanteen.mapper.CategoryMapper;
import com.jetchiu.supercanteen.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private StoreMapper storeMapper;
    @PostMapping
    public Res AddCate(@RequestBody CateDto cateDto){
        QueryWrapper<Category>queryWrapper=new QueryWrapper<>();
        System.out.println("dto中的sotrename是"+cateDto.storeName);
        long ofstore=storeMapper.selectOne(new QueryWrapper<Store>().eq("name",cateDto.storeName)).getId();


       queryWrapper.eq("name",cateDto.getName()).eq("of_store",ofstore);
           if(categoryMapper.selectOne(queryWrapper)!=null){
               return Res.Error(null,"该店铺已存在此分类");
           }
           Category category=new Category();
           category.setName(cateDto.name);
           category.setOfStore(ofstore);

           try {
                categoryMapper.insert(category);

           }catch (Exception e){
               System.out.println(e);
               return Res.Error(null,"服务器内部错误");
           }
       return Res.OK(null);


    }
    @GetMapping
    public Res GetCate(@RequestParam String storeName){
        System.out.println("get方法"+storeName);
        long ofstore=storeMapper.selectOne(new QueryWrapper<Store>().eq("name",storeName)).getId();
        QueryWrapper<Category>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("of_store",ofstore);
        List<Category>categoryList;
        try {
          categoryList=  categoryMapper.selectList(queryWrapper);
        }catch (Exception e){
            System.out.println(e);
            return Res.Error(null,"服务器错误");
        }

        return Res.OK(categoryList);
    }
    @GetMapping("/name")
    public Res GetCateName(@RequestParam int id){

        return Res.OK(categoryMapper.selectById(id).getName());
    }

    @DeleteMapping
    public Res DeleteCate(@RequestParam int id){
        System.out.println("接收到的删除id"+id);
      int status = categoryMapper.deleteById(id);
        System.out.println("是否删除"+status);
            return Res.OK(null);
    }
}
