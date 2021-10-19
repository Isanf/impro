package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.Firstlogin;

import com.supernettechnologie.impro.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Firstlogin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FirstloginRepository extends JpaRepository<Firstlogin, Long> {
    Optional<Firstlogin> findByUser_Login(String login);
    List<Firstlogin> findOneByUser_Login(String login);
    Optional<Firstlogin> findByUser_Login(Optional<String> currentUserLogin);
}
