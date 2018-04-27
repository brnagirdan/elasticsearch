package com.berna.core.repository;


import com.berna.core.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends ElasticsearchRepository<User, Long> {
    List<User> findByName(String text);
    List<User> findBySalary(Long salary);
}