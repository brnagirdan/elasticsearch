package com.berna.core.controller;

import com.berna.core.jparepository.BookmarkJpaRepository;
import com.berna.core.jparepository.UserJpaRepository;
import com.berna.core.model.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/rest/bookmark")
public class BookmarkController {

    @Autowired
    BookmarkJpaRepository bookmarkJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    public BookmarkController(BookmarkJpaRepository bookmarkJpaRepository, UserJpaRepository userJpaRepository) {
        this.bookmarkJpaRepository = bookmarkJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmark> readBookmarks(@PathVariable String userId) {
        return this.bookmarkJpaRepository.findByUserName(userId);
    }



}
