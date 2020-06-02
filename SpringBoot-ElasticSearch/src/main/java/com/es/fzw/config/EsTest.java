package com.es.fzw.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Fzw
 * @Date 18:25 2020/5/31
 */
@Configuration
public class EsTest {

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient http = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1", 9200, "http")));
        return  http;
    }

}
