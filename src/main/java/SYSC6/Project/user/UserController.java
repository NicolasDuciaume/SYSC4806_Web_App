package SYSC6.Project.user;

import SYSC6.Project.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/rest/api")
public class UserController {

    private static byte[] key;
    private static SecretKeySpec secretKey;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("addressbook not found"));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/get/{Username}/{Password}")
    public ResponseEntity<User> getUserByUsernameandPassword(@PathVariable("Username") String Username, @PathVariable("Password") String Password) {
        User user = userRepository.findByUsername(Username);
        ArrayList<User> passUsers = userRepository.findByPassword(Password);
        if (passUsers == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for(User s : passUsers){
            if(s.getUsername().equals(user.getUsername())){
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
        }
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user/get/login")
    public ResponseEntity<User> Login(@RequestBody User BuddyRequest) {
        User user = userRepository.findByUsername(BuddyRequest.getUsername());
        ArrayList<User> passUsers = userRepository.findByPassword(BuddyRequest.getPassword());
        if (passUsers == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        for(User s : passUsers){
            if(s.getUsername().equals(user.getUsername())){
                return new ResponseEntity<>(user,HttpStatus.OK);
            }
        }
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

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

    @GetMapping("/user")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> user = new ArrayList<User>();

        userRepository.findAll().forEach(user::add);

        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PostMapping("/user/add")
    public ResponseEntity<User> createUser(@RequestBody User userRequest) {
        User userTemp = new User();
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

    @PostMapping("/user/changeRole/{id}")
    public ResponseEntity<User> upgradeUser(@PathVariable("id") long id, @RequestBody User userRequest) {
        User targetToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found with id: "+id)); //TODO Not an Ideal way to handle this, return appropriate 400 code
        targetToUpdate.setRole(userRequest.getRole());
        System.out.println("Targets new Role: "+targetToUpdate.getRole());
        User userUpdated = userRepository.save(targetToUpdate);
        return new ResponseEntity<>(userUpdated, HttpStatus.CREATED);
    }

    @PutMapping("/user/upgrade/{id}")
    public ResponseEntity<User> upgradeUserRole(@PathVariable("id") long id) {
        Optional<User> targetToUpdate = userRepository.findById(id);
        if(!targetToUpdate.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User updated = targetToUpdate.get();
        if (targetToUpdate.get().getRole().isUpgradeable()){
            updated = UserUtil.setRoleToUpgradeRole(targetToUpdate.get());
        }
        User userUpdated = userRepository.save(updated);
        return new ResponseEntity<>(userUpdated, HttpStatus.CREATED);
    }
}