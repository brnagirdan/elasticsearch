package com.berna.core.builder;


import com.berna.core.model.User;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchQueryBuilder {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<User> getAll(String text) {
        QueryBuilder query= QueryBuilders.boolQuery()
                   .should(QueryBuilders.queryStringQuery(text)
                           .lenient(true)
                           .field("name")
                           .field("teamName")
                          ).should(QueryBuilders.queryStringQuery("*"+ text +"*")
                           .lenient(true)
                           .field("name")
                           .field("teamName"));


        NativeSearchQuery build =new NativeSearchQueryBuilder()
                .withQuery(query)
                .build();

        List<User>users= elasticsearchTemplate.queryForList(build,User.class);

        return users;

    }

}
