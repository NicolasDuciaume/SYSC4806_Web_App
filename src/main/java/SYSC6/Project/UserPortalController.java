package SYSC6.Project;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "https://projectsysc4806.herokuapp.com")
@RestController
@RequestMapping("/rest/api/user_portal")
public class UserPortalController {
    private UserPortal up = new UserPortal();

    /**
     * Redirects user to a different page to delete their account
     * @return currently main page, will return delete user page once that is implemented
     */
    @PostMapping("/delete_user")
    public String delete(){
        // send to delete page, for now sends back to main page
        return "redirect:/"; // TODO change this to delete page once that has been added, for now redirect to main page
    }

    @PostMapping("/upgrade")
    public String upgrade(){
        // send to upgrade page, for now sends back to main page
        return "redirect:/"; // TODO change this to upgrade page once that has been added, for now redirect to main page
    }

}
