package SYSC6.Project.user;

public class UserUtil {

    public static User setRoleToUpgradeRole(User user){
        user.setRole(user.getRole().getUpgradeRole().get());
        return user;
    }
}
