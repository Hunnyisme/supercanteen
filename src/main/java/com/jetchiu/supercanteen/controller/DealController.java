package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jetchiu.supercanteen.DTO.DealDto;
import com.jetchiu.supercanteen.DTO.DealDto2;
import com.jetchiu.supercanteen.DTO.RequestData;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.entity.*;
import com.jetchiu.supercanteen.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deal")
public class DealController {
@Autowired
    private CategoryMapper categoryMapper;
@Autowired
private DishMapper dishMapper;
@Autowired
private StoreMapper storeMapper;
@Autowired
private DealsMapper dealsMapper;
@Autowired
private Deals_detailsMapper dealsDetailsMapper;
@Autowired
private UserMapper userMapper;
    @PostMapping
    public Res AddDeal(@RequestBody RequestData data){
//               dealDtos.forEach(System.out::println);
//               for(DealDto dto:dealDtos){
//                   Deals deals=new Deals();
//                   deals.setDate_time(Timestamp.valueOf(LocalDateTime.now()));
//                   deals.
//               }
        data.getDealDtos().forEach(System.out::println);
        System.out.println(data.getUserid());
        List<DealDto> dealDtoList=data.getDealDtos();
        //先获取总共涉及到几个商店
        List<Store>storeList=new ArrayList<>();

        for(DealDto dto:dealDtoList){
            Category category=GetCateByDish(dto.getDishId());
            Store store=GetStoreByCate((int) category.getId());
            if(!storeList.contains(store)){
                storeList.add(store);
            }
        }
        for(Store s:storeList){
            double amount=0;
            String uuid= UUID.randomUUID().toString();
           for(DealDto dto:dealDtoList){
               Category category=GetCateByDish(dto.getDishId());
               Store store=GetStoreByCate((int) category.getId());
               if(s.getId() == store.getId()){
                   amount+=dto.getPrice();
                   Deals_details dealsDetails=new Deals_details();
                   dealsDetails.setDealsId(uuid);
                   dealsDetails.setQuantity(dto.getCount());
                   dealsDetails.setDishName(dto.getName());
                   dealsDetails.setSinglePrice(dto.getPrice());
                   dealsDetails.setSubtotal(dto.getPrice()*dto.getCount());
                   dealsDetailsMapper.insert(dealsDetails);
               }
           }
            Deals deals=new Deals();
           deals.setAmountCount(amount);
           deals.setUserId(data.getUserid());
           deals.setDateTime(Timestamp.valueOf(LocalDateTime.now()));
           deals.setStoreId(s.getId());
           deals.setId(uuid);
           dealsMapper.insert(deals);

        }

        return Res.OK(null);
    }
    @GetMapping
    public Res GetAllDeals(@RequestParam int userid){
        System.out.println("获取的userid"+userid);
        QueryWrapper<Deals>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id",userid);
        List<Deals>dealsList = dealsMapper.selectList(queryWrapper);
        List<DealDto2>dealDtoList=new ArrayList<>();
        for(Deals d:dealsList){
            DealDto2 dealDto2=new DealDto2();
            dealDto2.setAmountCount(d.getAmountCount());
            dealDto2.setStoreName(storeMapper.selectById(d.getStoreId()).getName());
            dealDto2.setDealId(d.getId());
            dealDto2.setUserId(d.getUserId());
            dealDto2.setUserName(userMapper.selectById(d.getUserId()).getName());
            dealDto2.setDateTime(d.getDateTime());
            dealDto2.setStoreId(d.getStoreId());
            dealDtoList.add(dealDto2);
        }
        return Res.OK(dealDtoList);
    }
    @GetMapping("/detail")
    public Res GetAllDealsDetails(@RequestParam String dealsId){
          QueryWrapper<Deals_details>queryWrapper=new QueryWrapper<>();
          queryWrapper.eq("deals_id",dealsId);
         List<Deals_details>dealsDetails=dealsDetailsMapper.selectList(queryWrapper);
         return Res.OK(dealsDetails);

    }
    @GetMapping("/one")
    public Res GetOneDeal(@RequestParam String dealId){
      Deals deals=  dealsMapper.selectById(dealId);
       DealDto2 dealDto2=new DealDto2();
       dealDto2.setDealId(deals.getId());
       dealDto2.setStoreName(storeMapper.selectById(deals.getStoreId()).getName());
       dealDto2.setUserId(deals.getUserId());
       dealDto2.setUserName(userMapper.selectById(deals.getUserId()).getName());
       dealDto2.setStoreId(deals.getStoreId());
        return Res.OK(dealDto2);
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
