package vn.iostar.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.iostar.entity.UserInfo;
import vn.iostar.repository.UserInfoRepository;

@Service
public class UserService {

    private final UserInfoRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserInfoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "Thêm user thành công!";
    }
}
