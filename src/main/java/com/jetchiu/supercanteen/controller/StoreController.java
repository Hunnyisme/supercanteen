package com.jetchiu.supercanteen.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jetchiu.supercanteen.DTO.Res;
import com.jetchiu.supercanteen.entity.Store;
import com.jetchiu.supercanteen.mapper.StoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {
@Autowired
private StoreMapper storeMapper;
    @Value("${upload.path}")
    String project_path;
@GetMapping
    public Res GetStores(){
        List<Store>storeList=null;
        QueryWrapper<Store>queryWrapper=new QueryWrapper<>();
        storeList=storeMapper.selectList(queryWrapper);

       return Res.OK(storeList);
    }
    @GetMapping("/pic")
    public void GetPic(@RequestParam String iconAddress , HttpServletResponse response) throws IOException{
             String ecodestr= URLDecoder.decode(iconAddress);
        System.out.println("翻译后的eco"+ecodestr);
        File file=new File(project_path+"storepic/"+ecodestr);
        if(file.exists())
        {
            OutputStream outputStream=response.getOutputStream();
            BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            while(bufferedInputStream.read(buffer)!=-1){
                outputStream.write(buffer);
            }
        }
    }
    @GetMapping("/one")
    public Res GetOneStore(@RequestParam int storeId){

         return Res.OK(storeMapper.selectById(storeId));
    }

}
