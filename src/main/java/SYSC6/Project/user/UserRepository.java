package SYSC6.Project.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    //User findByUsername(String Username);

    List<User> findByRole(RoleType aRole);
    User findByUsername(String Username);
    List<User> findByPassword(String Password);

    @Transactional
    void deleteById(Long Id);
}