package SYSC6.Project;

import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "https://projectsysc4806.herokuapp.com")
@RestController
@RequestMapping("/rest/api/user_portal")
public class UserPortalController {


    /**
     * Redirects user to a different page to delete their account
     * @return currently main page, will return delete user page once that is implemented
     */
    @PostMapping("/delete_user")
    public String delete(){
        // ask for verification/"are you sure?" message
        // if yes, redirect to delete page, else, stay on user portal
        return "redirect:/"; // TODO change this to delete page once that has been added, for now redirect to main page
    }

    public boolean confirm(){
        return true;
    }
}
