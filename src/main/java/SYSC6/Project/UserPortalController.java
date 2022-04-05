package SYSC6.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8080/")
//@CrossOrigin(origins = "https://projectsysc4806.herokuapp.com")
@Controller
@RequestMapping("/rest/api/user_portal")
public class UserPortalController {
    private static int LIMIT = 1000;
    private boolean limitExists = false;
    private Long id = 0L;

    @Autowired
    UserRepository userRepository;

    /**
     * @param UserId
     * @param role
     * @param model
     * @return
     */
    @PostMapping("/user_portal")
    public String enterPortal(@RequestParam(value="id",required=true) String UserId, @RequestParam(value="role",required=true) String role, Model model){
        id = Integer.parseInt(UserId) * 1L;
        checkRole(role);
        model.addAttribute("limitExists", limitExists);

        return "redirect:/user_portal";
    }

    /**
     * Checks the user's role
     * If they are a paid user, disable the app limit
     * Otherwise, enable limit
     */
    private void checkRole(String role){
        // User's role is PAID_USER
        if (role == RoleType.PAID_USER.toString()){
            // don't enable limit
            limitExists = false;
        }
        else{
            // By default, we will apply a limit i.e. assume FREE_USER
            limitExists = true;
        }
    }

    /**
     * @return redirects page to the app proxy
     */
    @PostMapping("/Click")
    public String incrementClicks(Model model){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
        int numClicks = user.getClicks();

        // Check if a limit has been reached only if it is applied (free user only)
        if (limitExists) {
            if (numClicks == LIMIT) {
                // free user has reached their 1000 click limit - disable "enter app" button
                System.out.println("Limit reached");

                // stay on current page
                return "redirect:/user_portal";
            }
        }
        numClicks++;

        user.setClicks(numClicks);
        userRepository.save(user); // update clicks in database

        model.addAttribute("limitExists", limitExists);

        // enter app
        return "redirect:/app_proxy";
    }

    /**
     * Actions when user enters the app via user portal
     * Here we will check if an UNPAID user has reached their limit and display it
     * A PAID user will not have a limit applied to them
     * @param model
     * @return
     */
    @PostMapping("/app_proxy")
    public String enterApp(Model model){
        // send to upgrade page, for now sends back to main page
        String userName = model.getAttribute("name").toString();
        return "redirect:/user_portal";
    }

//     TODO i think this should be moved to delete and upgrade controller classes since the functionality
//        isn't within the scope of the user portal
//    /**
//     * Redirects user to a different page to delete their account
//     * @return currently redirects to current page, will return delete user page once that is implemented
//     */
//    @PostMapping("/Delete")
//    public String delete(){
//        // send to delete page, for now sends back to main page
//        return "redirect:/user_portal"; // TODO change this to delete page once that has been added, for now redirect to main page
//    }
//
//    /**
//     * @return currently redirects to current page, will return upgrade user page once that is implemented
//     */
//    @PostMapping("/Upgrade")
//    public String upgrade(){
//        // send to upgrade page, for now sends back to main page
//        return "redirect:/user_portal"; // TODO change this to upgrade page once that has been added, for now redirect to main page
//    }
}
