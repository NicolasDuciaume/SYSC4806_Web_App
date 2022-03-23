package SYSC6.Project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class UserPortal {

    private boolean limitExists;
    private static int LIMIT = 1000;

    public UserPortal() {
//        checkRole();
    }

//    @PostMapping("/btn_clicked")
//    public String incrementClicks(@RequestParam(value="clicks",required=true) String clicks){
//        // increment the number of times the button has been clicked
//        int numClicks = Integer.parseInt(clicks);
//        numClicks++;
//
//
//        return "redirect:/user_portal";
//    }
//
//    /**
//     * Checks the user's role
//     *
//     * @return true
//     */
//
//    @GetMapping("/user_portal")
//    public boolean checkRole(@RequestParam(value="role",required=true) String role){
//        if (role == RoleType.FREE_USER.toString()){
//            limitExists = true;
//        }
//        else if (role == RoleType.PAID_USER.toString()){
//
//            limitExists = false;
//        }
//        else{   // if user role is ADMIN or NO_ROLE
//            // Admins should be redirected to admin page
//            // (although admins should never see this page to begin with)
//            // By default we will apply a limit
//            limitExists = true;
//        }
//        System.out.println(limitExists);
//        return limitExists;
//    }
}
