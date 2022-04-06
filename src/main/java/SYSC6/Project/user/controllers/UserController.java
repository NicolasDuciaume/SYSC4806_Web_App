package SYSC6.Project.user.controllers;

import SYSC6.Project.user.RoleType;
import SYSC6.Project.user.User;
import SYSC6.Project.user.UserRepository;
import SYSC6.Project.user.UserUtil;
import SYSC6.Project.user.userManagement.sorting.Page;
import SYSC6.Project.user.userManagement.sorting.PagingRequest;
import SYSC6.Project.user.userManagement.UserPOJO;
import SYSC6.Project.user.userManagement.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@CrossOrigin(origins = "demo4806.herokuapp.com")
@RestController
@RequestMapping("/rest/api")
public class UserController {

    private static byte[] key;
    private static SecretKeySpec secretKey;

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Takes a user id and fetches the user associated with that id
     * @param id the id of the user you which to return
     * @return the user profil in json format
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Takes the body of user details and fetches to see if the login information matches with a user in the
     * Database then returns that user information if it finds a match or a bad request if it does not
     * @param BuddyRequest The user detail
     * @return either a bad request or the user detail
     */
    @PostMapping("/user/get/login")
    public ResponseEntity<User> Login(@RequestBody User BuddyRequest) {
        User user = userRepository.findByUsername(BuddyRequest.getUsername());
        List<User> passUsers = userRepository.findByPassword(BuddyRequest.getPassword());
        if (passUsers == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for(User s : passUsers){
            if(s.getUsername().equals(user.getUsername())){
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Finds a user with the help of the Username
     * Seeing the usernames must be unique this method is used to check if the new username is already taken
     * @param Username username to be checked
     * @return ok status if the username is available and bad request if not
     */
    @GetMapping("/user/get/{Username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("Username") String Username) {
        User user = userRepository.findByUsername(Username);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method fecthes and returns all users within the database
     * @return All the users in the database if not empty and bed request if it is empty
     */
    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers(){

        List<User> user = new ArrayList<>(userRepository.findAll());

        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    /**
     * Method is used to take in user details and find if that user is an admin of basic user then creates
     * a new account within the database accordingly
     * @param userRequest The User details
     * @return a new user account
     */
    @PostMapping("/user/add")
    public ResponseEntity<User> createUser(@RequestBody User userRequest) {
        User userTemp;
        if(userRequest.getUsername().equals("admin") && userRequest.getPassword().equals("admin")){
            userTemp = userRepository.save(new User(userRequest.getUsername(), userRequest.getPassword(), RoleType.ADMIN));
        }
        else{
            userTemp = userRepository.save(new User(userRequest.getUsername(), userRequest.getPassword(), RoleType.FREE_USER));
        }
        return new ResponseEntity<>(userTemp, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/Del/{id}")
    public ResponseEntity<HttpStatus> deleteBuddy(@PathVariable("id") long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Change a users role:
     * "role":"[RoleType.toString]"
     *
     * @param id, id
     * @param userRequest, userRequest
     * @return ResponseEntity
     */
    @PostMapping("/user/changeRole/{id}")
    public ResponseEntity<User> changeRole(@PathVariable("id") long id, @RequestBody User userRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User floatingUser = userOptional.get();
        floatingUser.setRole(userRequest.getRole());
        floatingUser = userRepository.save(floatingUser);
        return new ResponseEntity<>(floatingUser, HttpStatus.CREATED);
    }

    /**
     * Trigger a users upgrade routine
     * @param id, id
     * @return ResponseEntity
     */
    @PutMapping("/user/upgrade/{id}")
    public ResponseEntity<User> upgradeUserRole(@PathVariable("id") long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User floatingUser = userOptional.get();
        if (floatingUser.getRole().isUpgradeable()){
            UserUtil.setRoleToUpgradeRole(floatingUser);
        }
        User userUpdated = userRepository.save(floatingUser);
        return new ResponseEntity<>(userUpdated, HttpStatus.CREATED);
    }

    /**
     * Returns a Page object with all users POJO objects contained within, used by dynamic table elements in admin portal
     * @param pagingRequest, pagingRequest
     * @return Page<UserPOJO>
     */
    @PostMapping("/user/pagelist")
    public Page<UserPOJO> list(@RequestBody PagingRequest pagingRequest) {
        return userService.getUsers(pagingRequest);
    }

}
