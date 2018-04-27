package com.berna.core.controller;

import com.berna.core.builder.SearchQueryBuilder;
import com.berna.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/manual/search")
public class ManualSearchController {

    @Autowired
    private SearchQueryBuilder searchQueryBuilder;

    @GetMapping(value="/{text}")
    public List<User>getAll(@PathVariable final String text){
        return searchQueryBuilder.getAll(text);
    }
}