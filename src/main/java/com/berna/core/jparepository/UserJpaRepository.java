package com.berna.core.jparepository;

import com.berna.core.model.UserDummy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserDummy, Long> {
}