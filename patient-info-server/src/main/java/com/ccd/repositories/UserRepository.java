package com.ccd.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccd.models.User;

/** This repository stores user info. A user may have access
 * to the information of multiple patients in the case of
 * parents accessing the medical information of minors */
public interface UserRepository extends JpaRepository<User, Long> {

}
