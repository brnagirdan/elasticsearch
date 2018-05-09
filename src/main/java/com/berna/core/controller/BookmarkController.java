package com.berna.core.controller;

import com.berna.core.exception.UserNotFoundException;
import com.berna.core.jparepository.BookmarkJpaRepository;
import com.berna.core.jparepository.UserJpaRepository;
import com.berna.core.model.Bookmark;
import com.berna.core.updater.BookmarkUpdater;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.security.Principal;
import java.util.Collection;


// TODO hateoas propertilerini ekle
@RestController
@RequestMapping("/bookmarks")
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
    Collection<Bookmark> readBookmarks(Principal principal) {
        this.validateUser(principal);
        return this.bookmarkJpaRepository.findByUserName(principal.getName());
    }

    @RequestMapping(method=RequestMethod.GET, value="/{bookmarkId}")
    Bookmark readBookmark(Principal principal, @PathVariable Long bookmarkId){
         this.validateUser(principal);
        return this.bookmarkJpaRepository.findOne(bookmarkId);
    }


   @RequestMapping(method = RequestMethod.PUT,value="/{bookmarkId}")
    ResponseEntity<?> updateBookmark(Principal principal,@PathVariable Long bookmarkId,@RequestBody Bookmark input) {
        this.validateUser(principal);
        Bookmark bookmark=bookmarkJpaRepository.findOne(bookmarkId);
        Bookmark pending=bookmarkUpdater.apply(bookmark,input);
        bookmarkJpaRepository.save(pending);
        return ResponseEntity.ok().build();
    }


    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createBookmark(Principal principal,@RequestBody Bookmark input){
        this.validateUser(principal);
        return this.userJpaRepository
                .findByName(principal.getName())
                .map(user->{
                    Bookmark result=bookmarkJpaRepository.save(new Bookmark(user,
                            input.getUri(),input.getDescription()));

                /*   URI location= ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri(); */
                 //   return ResponseEntity.created(location).build();

                    Link forOneBookmark=new BookmarkResource(result).getLink("self");
                    return ResponseEntity.created(URI.create(forOneBookmark.getHref())).build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/{bookmarkId}")
    ResponseEntity<?>deleteBookmark(Principal principal,@PathVariable Long bookmarkId){
        this.validateUser(principal);
        Bookmark bookmark= bookmarkJpaRepository
                .findById(bookmarkId);
        return ResponseEntity.ok(bookmark);

    }


    private void validateUser(Principal principal) {
        String name = principal.getName();
        this.userJpaRepository.findByName(name).orElseThrow(
                () -> new UserNotFoundException(name));
    }

}
