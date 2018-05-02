package com.berna.core.jparepository;

import com.berna.core.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BookmarkJpaRepository extends JpaRepository<Bookmark, Long> {
    Collection<Bookmark> findByUserName(String name);
    Bookmark findById(long id);
}
