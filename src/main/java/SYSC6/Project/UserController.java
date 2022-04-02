package SYSC6.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/rest/api")
public class UserController {
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
    public ResponseEntity<User> createUser(@RequestBody User BuddyRequest) {
        User userTemp = userRepository.save(new User(BuddyRequest.getUsername(), BuddyRequest.getPassword()));
        return new ResponseEntity<>(userTemp, HttpStatus.CREATED);
    }
}
