package com.es.fzw.utils;


import com.es.fzw.entity.Book;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @Author Fzw
 * @Date 20:45 2020/5/31
 */
@Component
public class HtmlJsoup {
//    public static void main(String[] args) throws IOException {
//     new HtmlJsoup().getInformation("java").forEach(System.out::println);
//    }
    public List<Book> getInformation(String keyword) throws IOException {
        List<Book> books=new ArrayList<>();
        String url="https://search.jd.com/Search?keyword="+keyword;
        Document parse = Jsoup.parse(new URL(url), 30000);
        Element elementById = parse.getElementById("J_goodsList");
        //System.out.println(elementById.html());
        Elements li = elementById.getElementsByTag("li");
        for (Element element:li) {
            String img=element.getElementsByTag("img").eq(0).attr("src");
            String price=element.getElementsByClass("p-price").eq(0).text();
            String title=element.getElementsByClass("p-name").eq(0).text();
            System.out.println(img);
            Book book = new Book(img, price, title);
            books.add(book);
        }
        return books;
    }
}
