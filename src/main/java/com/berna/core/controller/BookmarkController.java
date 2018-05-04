package com.berna.core.controller;

import com.berna.core.exception.UserNotFoundException;
import com.berna.core.jparepository.BookmarkJpaRepository;
import com.berna.core.jparepository.UserJpaRepository;
import com.berna.core.model.Bookmark;
import com.berna.core.updater.BookmarkUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/{name}/bookmarks")
public class BookmarkController {

    private final BookmarkJpaRepository bookmarkJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final BookmarkUpdater bookmarkUpdater;

    @Autowired
    public BookmarkController(BookmarkJpaRepository bookmarkJpaRepository, UserJpaRepository userJpaRepository, BookmarkUpdater bookmarkUpdater) {
        this.bookmarkJpaRepository = bookmarkJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.bookmarkUpdater = bookmarkUpdater;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Bookmark> readBookmarks(@PathVariable String name) {
        this.validateUser(name);
        return this.bookmarkJpaRepository.findByUserName(name);
    }

    @RequestMapping(method=RequestMethod.GET, value="/{bookmarkId}")
    Bookmark readBookmark(@PathVariable String name, @PathVariable Long bookmarkId){
        this.validateUser(name);
        return this.bookmarkJpaRepository.findOne(bookmarkId);
    }

   @RequestMapping(method = RequestMethod.PUT,value="/{bookmarkId}")
    ResponseEntity<?> updateBookmark(@PathVariable String name,@PathVariable Long bookmarkId,@RequestBody Bookmark input) {
        this.validateUser(name);
        Bookmark bookmark=bookmarkJpaRepository.findOne(bookmarkId);
        Bookmark pending=bookmarkUpdater.apply(bookmark,input);
        bookmarkJpaRepository.save(pending);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createBookmark(@PathVariable String name,@RequestBody Bookmark input){
        this.validateUser(name);
        return this.userJpaRepository
                .findByName(name)
                .map(user->{
                    Bookmark result=bookmarkJpaRepository.save(new Bookmark(user,
                            input.getUri(),input.getDescription()));

                    URI location= ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri();
                    return ResponseEntity.created(location).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/{bookmarkId}")
    ResponseEntity<?>deleteBookmark(@PathVariable String name,@PathVariable Long bookmarkId){
        this.validateUser(name);
        Bookmark bookmark= bookmarkJpaRepository
                .findById(bookmarkId);
        return ResponseEntity.ok(bookmark);

    }


    private void validateUser(String name) {
        this.userJpaRepository.findByName(name).orElseThrow(
                () -> new UserNotFoundException(name));
    }

}
