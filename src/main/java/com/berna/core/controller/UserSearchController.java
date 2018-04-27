package com.berna.core.controller;

import com.berna.core.model.User;
import com.berna.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/user/search")
public class UserSearchController {

    @Autowired
    UserRepository usersRepository;


    @GetMapping(value = "/name/{text}")
    public List<User> searchName(@PathVariable final String text) {
        return usersRepository.findByName(text);
    }


    @GetMapping(value = "/salary/{salary}")
    public List<User> searchSalary(@PathVariable final Long salary) {
        return usersRepository.findBySalary(salary);
    }


    @GetMapping(value = "/all")
    public List<User> searchAll() {
        List<User> userDummyList = new ArrayList<>();
        Iterable<User> userses = usersRepository.findAll();
        userses.forEach(userDummyList::add);
        return userDummyList;
    }
}
