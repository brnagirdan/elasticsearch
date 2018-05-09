package com.berna.core.controller;


import com.berna.core.model.Bookmark;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class BookmarkResource extends ResourceSupport {
    private final Bookmark bookmark;

    public BookmarkResource(Bookmark bookmark) {
        String name=bookmark.getUser().getName();
        this.bookmark = bookmark;
        this.add(new Link(bookmark.getUri(), "bookmark-uri"));
        this.add(linkTo(BookmarkController.class, name).withRel("bookmarks"));
        this.add(linkTo(methodOn(BookmarkController.class,name).readBookmark(null,bookmark.getId())).withSelfRel());
    }

    public Bookmark getBookmark() {
        return bookmark;
    }
}
