package SYSC6.Project.user;

import SYSC6.Project.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>{
    //User findByUsername(String Username);

    List<User> findByRole(RoleType aRole);
    User findByUsername(String Username);
<<<<<<< master:src/main/java/SYSC6/Project/user/UserRepository.java
    List<User> findByPassword(String Password);

    @Transactional
    void deleteById(Long Id);
}
=======
    ArrayList<User> findByPassword(String Password);

    @Transactional
    void deleteById(Long Id);
}
>>>>>>> Completed user delete method functionality:src/main/java/SYSC6/Project/UserRepository.java
