package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.DTO.CartDishDto;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.entity.Cart;
import com.jetchiu.supercanteen.entity.Category;
import com.jetchiu.supercanteen.entity.Dish;
import com.jetchiu.supercanteen.entity.Store;
import com.jetchiu.supercanteen.mapper.CartMapper;
import com.jetchiu.supercanteen.mapper.CategoryMapper;
import com.jetchiu.supercanteen.mapper.DishMapper;
import com.jetchiu.supercanteen.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
private CartMapper cartMapper;
    @Autowired
    private DishMapper dishMapper;
@Autowired
    private CategoryMapper categoryMapper;
@Autowired
private StoreMapper storeMapper;
    @GetMapping
    public Res AddCart(@RequestParam int dishid, @RequestParam int userid){
       Cart cart=new Cart();
       cart.setDishId(dishid);
       cart.setOfUser(userid);
       cart.setCount(1);

cartMapper.insert(cart);
return Res.OK(null);
    }

    @GetMapping("/get")
    public Res GetCart(@RequestParam int userid){
        QueryWrapper<Cart>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("of_user",userid);
         return Res.OK(cartMapper.selectCount(queryWrapper));
    }
    @GetMapping("/list")
    public Res GetCartList(@RequestParam int userid){
//             QueryWrapper<Cart>queryWrapper=new QueryWrapper<>();
//             queryWrapper.eq("of_user",userid);
//              List<Cart> cartList=cartMapper.selectList(queryWrapper);
//                   List cartdishlist=new ArrayList();
//                   for(Cart c:cartList){
//                       QueryWrapper<Dish>queryWrapper1=new QueryWrapper<>();
//                       queryWrapper1.eq("id",c.getDishId());
//                      Dish dish=dishMapper.selectOne(queryWrapper1);
//                       CartDishDto dto=new CartDishDto();
//                       dto.setDishName(dish.getName());
//                       dto.setDishId((int) dish.getId());
//                       dto.setPicture(dish.getPicture());
//                       dto.setCount(c.getCount());
//                       dto.setDishPrice(dish.getPrice());
//                       dto.setCateName((categoryMapper.selectById(dish.getRelatedCate()).getName()));
//                       cartdishlist.add(dto);
//                   }
//                   return Res.OK(cartdishlist);
        //先收集所有购物车数据
            QueryWrapper<Cart>queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("of_user",userid);
            List<Cart> cartList=cartMapper.selectList(queryWrapper);
            List<CartDishDto> dishDtoList=new ArrayList<>();
            List<Store> storeList=new ArrayList<>();
            List ansList=new ArrayList();
              for(Cart c : cartList){
                  Category category=GetCateByDish(c.getDishId());
                  Store store=GetStoreByCate((int) category.getId());
                  Dish dish=dishMapper.selectById(c.getDishId());
                  CartDishDto dto=new CartDishDto();
                  dto.setCartId(c.getId());
                  dto.setPicture(dish.getPicture());
                  dto.setDishName(dish.getName());
                  dto.setDishPrice(dish.getPrice());
                  dto.setCount(c.getCount());
                  dto.setDishId((int) dish.getId());

            //      dishList.add(dishMapper.selectById(c.getDishId()));
                  dishDtoList.add(dto);
                  if(!storeList.contains(store)){
                      storeList.add(store);
                  }
              }
              for(Store store :storeList){
                  List temList=new ArrayList();
                  temList.add(store);
                  for(CartDishDto dto:dishDtoList){
                      Category category=GetCateByDish((int) dto.getDishId());
                      Store s=GetStoreByCate((int) category.getId());
                      if(s.getId()==store.getId()){
                          temList.add(dto);
                      }
                  }
                  ansList.add(temList);

              }
           return Res.OK(ansList);
    }
    @DeleteMapping
    public Res DeleteCart(){

        return Res.OK(null);
    }

    private Store GetStoreByCate(int CateId){
       Category category= categoryMapper.selectById(CateId);
       return storeMapper.selectById(category.getOfStore());
    }
    private Category GetCateByDish(int dishId){
        Dish dish=dishMapper.selectById(dishId);
        return categoryMapper.selectById(dish.getRelatedCate());
    }
}
