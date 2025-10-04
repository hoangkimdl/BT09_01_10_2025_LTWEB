package vn.iostar.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import vn.iostar.entity.Users;
import vn.iostar.repository.UserRepository;
import vn.iostar.service.MyUserDetails;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.getUserByUsername(username);
        if (user == null) throw new UsernameNotFoundException("Could not find user");
        return new MyUserDetails(user);
    }
}
