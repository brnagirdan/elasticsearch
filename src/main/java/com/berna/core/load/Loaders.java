package com.berna.core.load;


import com.berna.core.jparepository.BookmarkJpaRepository;
import com.berna.core.jparepository.UserJpaRepository;
import com.berna.core.model.Bookmark;
import com.berna.core.repository.UserRepository;
import com.berna.core.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class Loaders {

    @Autowired
    ElasticsearchOperations operations;

    @Autowired
    UserRepository usersRepository;

    @Autowired
    UserJpaRepository userJpaRepository;


    @PostConstruct
    @Transactional
    public void loadAll(){
        System.out.println("Loading Data");
        List<User> usersList = userJpaRepository.findAll(); //Get from H2 DB */
        usersRepository.save(usersList); //loads into Elastic
        System.out.printf("Loading Completed");
    }


}