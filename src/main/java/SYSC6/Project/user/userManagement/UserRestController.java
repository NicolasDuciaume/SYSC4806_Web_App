package SYSC6.Project.user.userManagement;

import SYSC6.Project.sorting.Page;
import SYSC6.Project.sorting.PagingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/usertable")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Page<UserPOJO> list(@RequestBody PagingRequest pagingRequest) {
        return userService.getUsers(pagingRequest);
    }
}
