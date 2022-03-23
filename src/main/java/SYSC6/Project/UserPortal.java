package SYSC6.Project;

public class UserPortal {

    private User user;
    private boolean limitExists;
    private static int LIMIT = 1000;


    /**
     * Checks the user's role
     *
     * @return true
     */
    public boolean checkRole(){
        if (user.getRole() == RoleType.FREE_USER){
            limitExists = true;
        }
        else if (user.getRole() == RoleType.PAID_USER){
            limitExists = false;
        }
        else{   // if user role is ADMIN or NO_ROLE
            // Admins should be redirected to admin page
            // (although admins should never see this page to begin with)
            // By default we will apply a limit
            limitExists = true;
        }
        return limitExists;
    }

}
