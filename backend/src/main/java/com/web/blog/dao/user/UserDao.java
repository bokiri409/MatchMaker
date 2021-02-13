
package com.web.blog.dao.user;

import java.util.Optional;

import com.web.blog.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, Integer> {
    User getUserByEmail(String email);

    User getUserByNickname(String nickname);

    User save(User user);

    @Transactional
    void deleteByEmail(String email);

    // void updateByEmail(String email, User user);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.uid = :uid")
    void updateUserPassword(@Param("uid") int uid, @Param("password") String password);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);

    User getUserByEmailAndCertified(String email, String certified);
}
