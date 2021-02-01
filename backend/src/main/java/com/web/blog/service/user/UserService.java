package com.web.blog.service.user;

import com.web.blog.model.user.User;
import com.web.blog.dao.user.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User save(User user) {
        userDao.save(user);
        return user;
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userDao.findUserByEmailAndPassword(email, password);
    }

    @Transactional
    public void updateByEmail(String email, User user) {
        User u = userDao.getUserByEmail(email);

        u.setNickname(user.getNickname());
        u.setPassword(user.getPassword());
        userDao.save(u);
    }

    public User emailCertifiedCheck(User user) {
        return userDao.getUserByEmailAndCertified(user.getEmail(), user.getCertified());
    }

    public void emailCertifiedUpdate(String email) {
        User u = userDao.getUserByEmail(email);
        u.setCertified("Y");
        userDao.save(u);
    }

}
