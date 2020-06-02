package com.es.fzw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Author Fzw
 * @Date 20:41 2020/5/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Book {
    private String url;
    private String price;
    private String title;
}
