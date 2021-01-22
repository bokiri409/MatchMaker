
package com.web.blog.dao.user;

import java.util.Optional;

import com.web.blog.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.transaction.annotation.Transactional;

public interface UserDao extends JpaRepository<User, String> {
    User getUserByEmail(String email);

    User getUserByNickname(String nickname);

    User save(User user);

    @Transactional
    void deleteByEmail(String email);

    // void updateByEmail(String email, User user);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    User getUserByEmailAndCertified(String email, String certified);
}
