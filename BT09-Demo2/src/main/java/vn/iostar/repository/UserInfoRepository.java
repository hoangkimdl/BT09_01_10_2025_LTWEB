package vn.iostar.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.iostar.entity.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
	Optional<UserInfo> findByEmail(String email);
}
