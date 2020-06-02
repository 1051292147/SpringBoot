package com.es.fzw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author Fzw
 * @Date 20:25 2020/6/1
 */

@Controller
public class ShopController {

    @GetMapping({"/","/index"})
    public String toIndex(){
        return "index";
    }
}
