package com.berna.core.updater;


import com.berna.core.jparepository.BookmarkJpaRepository;
import com.berna.core.model.Bookmark;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;


//which is an immutable class and cannot be extended
@Component
public final class BookmarkUpdater implements BiFunction<Bookmark,Bookmark,Bookmark> {

    @Override
    public Bookmark apply(Bookmark old, Bookmark pending) {
        if(!pending.getDescription().isEmpty()){
            old.setDescription(pending.getDescription());
        }
        if(!pending.getUri().isEmpty()){
            old.setUri(pending.getUri());
        }
        old.setUser(pending.getUser());
        return old;
    }
}
