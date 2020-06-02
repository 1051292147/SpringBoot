package com.es.fzw.controller;

import com.es.fzw.entity.Book;
import com.es.fzw.service.ShopService;
import com.es.fzw.utils.HtmlJsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author Fzw
 * @Date 20:28 2020/6/1
 */

@RestController
public class RestShopController {

    @Autowired
    private HtmlJsoup htmlJsoup;
    @Autowired
    private ShopService shopService;

    @GetMapping("/test/{kw}")
    public List<Book> getInformation(@PathVariable("kw") String keyword) throws IOException {
        List<Book> information = htmlJsoup.getInformation(keyword);
        return information;
    }

    @GetMapping("/add")
    public Boolean add() throws IOException {
        boolean insert = shopService.insert();
        return insert;
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable("keyword") String keyword,@PathVariable("pageNo") int pageNo,@PathVariable("pageSize") int pageSize) throws IOException {
        //List<Map<String, Object>> java = shopService.findBook(keyword,pageNo,pageSize);
        //return java;
        //高亮
        List<Map<String, Object>> bookHight = shopService.findBookHight(keyword, pageNo, pageSize);
        return bookHight;

    }



}
