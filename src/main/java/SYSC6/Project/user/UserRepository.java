package SYSC6.Project.user;

import SYSC6.Project.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    //User findByUsername(String Username);

    List<User> findByRole(RoleType aRole);
    User findByUsername(String Username);
    ArrayList<User> findByPassword(String Password);
}
