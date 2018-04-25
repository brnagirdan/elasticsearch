package com.berna.core.load;


import com.berna.core.jparepository.UserJpaRepository;
import com.berna.core.repository.UsersRepository;
import com.berna.core.model.UserDummy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Loaders {

    @Autowired
    ElasticsearchOperations operations;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserJpaRepository userJpaRepository;


    @PostConstruct
    @Transactional
    public void loadAll(){
        System.out.println("Loading Data");
        userJpaRepository.save(getData()); // save datas to H2 DB
        List<UserDummy> usersList = userJpaRepository.findAll(); //Get from H2 DB */
        usersRepository.save(getData()); //loads into Elastic
        System.out.printf("Loading Completed");
    }

    private List<UserDummy> getData() {
        List<UserDummy> users = new ArrayList<>();
        users.add(new UserDummy("Ajay",123L, "Accounting", 12000L));
        users.add(new UserDummy("Jaga",1234L, "Finance", 22000L));
        users.add(new UserDummy("Thiru",1235L, "Accounting", 12000L));
        return users;
    }
}