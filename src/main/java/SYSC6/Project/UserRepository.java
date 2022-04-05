package SYSC6.Project;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String Username);
    ArrayList<User> findByPassword(String Password);

    @Transactional
    void deleteById(Long Id);
}