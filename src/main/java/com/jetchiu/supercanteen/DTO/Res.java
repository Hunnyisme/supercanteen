package com.jetchiu.supercanteen.DTO;

import lombok.Data;

@Data
public class Res<T> {
 public T data;
 String mess;
 int status_code;

 public static<T> Res OK(T data){
    Res res=new Res();
     res.data=data;
     res.status_code=200;
     res.mess="操作成功";
     return  res;
 }
 public static <T> Res Error(T data,String mes){
     Res res=new Res();
  res.data=data;
  res.status_code=400;
  res.mess=mes;
  return res;
 }

}
