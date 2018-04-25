package com.berna.core.repository;


import com.berna.core.model.UserDummy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends ElasticsearchRepository<UserDummy, Long> {
    List<UserDummy> findByName(String text);
    List<UserDummy> findBySalary(Long salary);
}