package com.lexueoude.web;

import com.lexueoude.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(){

        System.out.println("handler code exec ing .... ");


//        假设 场景找不到的时候，创建一个新的exception出来
//        String  web = null;
//        if (web == null){
//            throw new NotFoundException("您所查看的页面不存在");
//        }


        return "details";
    }

}
