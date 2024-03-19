package com.jetchiu.supercanteen.controller;

import com.jetchiu.supercanteen.DTO.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

//主要负责dish的图片
@RestController
@RequestMapping("/api/file")
public class UploadController {
    @Autowired
    private ResourceLoader resourceLoader;
    @Value("${upload.path}")
    String project_path;
    @PostMapping
    public Res doUoloadFile(@RequestParam("uid")String uid, @RequestParam("picture")MultipartFile picture) throws IOException {
        System.out.println("传来文件：");
        System.out.println(uid);
        System.out.println(picture.getOriginalFilename());
        String[] sps=picture.getOriginalFilename().split("\\.");
        String postfix=sps[1];
        String newFileName=uid+"."+postfix;
        String path=project_path+"dishpic/"+newFileName;
        File file=new File(path);
        picture.transferTo(file);
       return Res.OK(newFileName);
    }
@GetMapping
    public void GetPic(@RequestParam String filename ,HttpServletResponse response) throws IOException {
    System.out.println("收到的文件名是"+filename);
        File file=new File(project_path+"dishpic/"+filename);
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
}
