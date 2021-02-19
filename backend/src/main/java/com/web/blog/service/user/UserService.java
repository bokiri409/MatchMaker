package com.web.blog.service.user;

import com.web.blog.model.user.User;
import com.web.blog.dao.user.UserDao;
import com.web.blog.utils.EncryptionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

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
        char[] charSet = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                                    'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        char[] speSet = new char[] {'!', '@', '#', '$'};
        char[] numSet = new char[] {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};



        StringBuilder str = new StringBuilder();

        // UserDto Password Pattern 을 만족시키는 임시 비밀번호 생성
        int idx = 0;
        for(int i = 0; i < 5; i++) {
            idx = (int) (charSet.length * Math.random());
            str.append(charSet[idx]);
        }
        for(int i = 0; i < 2; i++) {
            idx = (int) (speSet.length * Math.random());
            str.append(speSet[idx]);
        }
        for(int i = 0; i < 5; i++) {
            idx = (int) (numSet.length * Math.random());
            str.append(numSet[idx]);
        }

        return str.toString();
    }

    // Form Validation 에러 메세지를 Map 에 담아 반환하는 함수
    public Map<String,String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }

        return validatorResult;
    }
}
