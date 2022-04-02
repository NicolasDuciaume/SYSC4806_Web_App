package SYSC6.Project;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "https://projectsysc4806.herokuapp.com")
@RestController
@RequestMapping("/rest/api/user_portal")
public class UserPortalController {
    private static int LIMIT = 1000;
    private boolean limitExists = false;

    @PostMapping("/user_portal")
    public String enterPortal(Model model){
        // send to upgrade page, for now sends back to main page
        String userName = model.getAttribute("name").toString();
        return "redirect:/"; // TODO change this to upgrade page once that has been added, for now redirect to main page
    }

    private int incrementClicks(@RequestParam(value="clicks",required=true) String clicks){
        // increment the number of times the button has been clicked
        int numClicks = Integer.parseInt(clicks);
        numClicks++;


        return numClicks;
    }

    /**
     * Checks the user's role
     *
     * @return true
     */

    private boolean checkRole(@RequestParam(value="role",required=true) String role){
        if (role == RoleType.FREE_USER.toString()){
            limitExists = true;
        }
        else if (role == RoleType.PAID_USER.toString()){

            limitExists = false;
        }
        else{   // if user role is ADMIN or NO_ROLE
            // Admins should be redirected to admin page
            // (although admins should never see this page to begin with)
            // By default we will apply a limit
            limitExists = true;
        }
        System.out.println(limitExists);
        return limitExists;
    }

    /**
     * Redirects user to a different page to delete their account
     * @return currently redirects to current page, will return delete user page once that is implemented
     */
    @PostMapping("/delete_user")
    public String delete(){
        // send to delete page, for now sends back to main page
        return "user_portal"; // TODO change this to delete page once that has been added, for now redirect to main page
    }

    /**
     * @return currently redirects to current page, will return upgrade user page once that is implemented
     */
    @PostMapping("/upgrade")
    public String upgrade(){
        // send to upgrade page, for now sends back to main page
        return "user_portal"; // TODO change this to upgrade page once that has been added, for now redirect to main page
    }


}
