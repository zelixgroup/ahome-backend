package com.zelix.ahome.repository;

import com.zelix.ahome.domain.AppUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AppUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    @Query("select appUser from AppUser appUser where appUser.user.login = ?#{principal.username}")
    List<AppUser> findByUserIsCurrentUser();
}
