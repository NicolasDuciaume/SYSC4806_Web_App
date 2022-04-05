package SYSC6.Project;


import SYSC6.Project.user.User;

public class Admin extends User {

    public Admin(){

    }

    public Admin(String username, String password){
        super(username,password);
    }

    public void setUserRole(User user,RoleType role) {
        user.setRole(role);
    }

}
