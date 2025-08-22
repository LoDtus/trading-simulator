//package com.trading_simulator.backend.config.elasticsearch;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestClient;
//
//@Configuration
//public class ElasticSearchConfig {
//    @Bean
//    public RestHighLevelClient elasticsearchClient() {
//        return new RestHighLevelClient(
//                RestClient.builder(new HttpHost("localhost", 9200, "http"))
//        );
//    }
//}
