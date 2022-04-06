package SYSC6.Project.user;


import SYSC6.Project.user.RoleType;
import SYSC6.Project.user.User;

public class Admin extends User {

    public Admin(){
        super();
    }

    public Admin(String username, String password, RoleType role){
        super(username,password,role);
    }

}
