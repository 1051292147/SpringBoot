package com.es.fzw.service;

import com.alibaba.fastjson.JSON;
import com.es.fzw.entity.Book;
import com.es.fzw.utils.HtmlJsoup;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.bucket.geogrid.ParsedGeoTileGrid;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author Fzw
 * @Date 20:31 2020/6/1
 */
@Service
public class ShopService {

    @Autowired
    private HtmlJsoup htmlJsoup;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //实现给ElasticSearch中添加数据
    public boolean insert() throws IOException {
        List<Book> information = htmlJsoup.getInformation("vue");
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2ms");
        for (int i = 0; i <information.size() ; i++) {
           bulkRequest.add(new IndexRequest("shop").source(JSON.toJSONString(information.get(i)),XContentType.JSON));

        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        boolean b = !bulk.hasFailures();
        return b;
    }

    //实现给ElasticSearch的分页查询功能
    public List<Map<String,Object>> findBook(String keyword,int pageNo,int pageSize) throws IOException {
        if (pageNo == 0) {
            pageNo = 1;
        }
        //条件搜索
        SearchRequest shop = new SearchRequest("shop");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //设置分页
        searchSourceBuilder.from(1);
        searchSourceBuilder.size(10);

        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);


        //执行搜索
        shop.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(shop, RequestOptions.DEFAULT);
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit result : search.getHits().getHits()) {
            list.add(result.getSourceAsMap());
        }
        return list;
    }
    //实现给ElasticSearch的分页查询功能
    public List<Map<String,Object>> findBookHight(String keyword,int pageNo,int pageSize) throws IOException {
        if (pageNo == 0) {
            pageNo = 1;
        }
        //条件搜索
        SearchRequest shop = new SearchRequest("shop");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //设置分页
        searchSourceBuilder.from(1);
        searchSourceBuilder.size(10);

        //精准匹配
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);


        //执行搜索
        shop.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(shop, RequestOptions.DEFAULT);
        List<Map<String, Object>> list = new ArrayList<>();
        for (SearchHit result : search.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = result.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = result.getSourceAsMap();
            if(title!=null){
                Text[] fragments = title.fragments();
                String x="";
                for (Text t:fragments){
                    x+=t;
                }
                sourceAsMap.put("title",x);
            }

            list.add(result.getSourceAsMap());
        }
        return list;
    }
}
