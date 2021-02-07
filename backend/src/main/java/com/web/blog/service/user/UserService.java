package com.web.blog.service.user;

import com.web.blog.model.user.User;
import com.web.blog.dao.user.UserDao;
import com.web.blog.utils.EncryptionUtils;
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

    public boolean checkEmailAndNickname(String email, String nickname) {
        User user = userDao.getUserByEmail(email);
        return user != null && user.getNickname().equals(nickname);
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

    public void updatePassword(String str, String email) {
        String password = EncryptionUtils.encryptSHA256(str);
        int uid = userDao.findUserByEmail(email).getUid();
        userDao.updateUserPassword(uid, password);
    }

    public String getTempPassword() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                '!', '@', '#' };

        StringBuilder str = new StringBuilder();

        int idx = 0;
        for(int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str.append(charSet[idx]);
        }

        return str.toString();
    }

}
