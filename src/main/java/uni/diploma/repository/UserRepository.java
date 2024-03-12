package uni.diploma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uni.diploma.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>  {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    Optional<User> findByUsername(String username);

    User findUserByUsername(String username);

}
