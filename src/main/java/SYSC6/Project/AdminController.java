package SYSC6.Project;

import SYSC6.Project.user.User;
import SYSC6.Project.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    private Long id = 0L;

    @Autowired
    UserRepository userRepository;
//
//    @GetMapping("/admin_portal")
//    public String greeting_admin(@RequestParam(name="name", required=false, defaultValue="World") String name_place, Model model) {
//        User user = userRepository.findByUsername("admin");
//        name_place = user.getUsername();
//        model.addAttribute("name", "Admin");
//        user.setRole(RoleType.ADMIN);
//        model.addAttribute("role", user.getRole().toString());
//        return "admin_portal";
//    }

//    @GetMapping("/view_users")
//    public String getUsers(){
//        if(id==0){
//            return "login_form";
//        }
//        User user = getUser(id);
//        if(UserUtil.hasAdmin(user)){
//            return "view_users";
//        }
//        return logout();
//    }


//    /**
//     * Logs the user out and returns the user to the login screen
//     * @return
//     */
//    @PostMapping("/LogOut")
//    public String logout(){
//        id = 0L;
//        return "redirect:/";
//    }

}
