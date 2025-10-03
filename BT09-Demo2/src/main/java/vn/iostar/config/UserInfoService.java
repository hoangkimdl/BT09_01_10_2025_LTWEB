package vn.iostar.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vn.iostar.entity.UserInfo;
import vn.iostar.repository.UserInfoRepository;

import java.util.Optional;

public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository repository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.repository = userInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username);
        return userDetail.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}