package SYSC6.Project.user;

import SYSC6.Project.RoleType;

public class UserUtil {

    public static void setRoleToUpgradeRole(User user){
        user.setRole(user.getRole().getUpgradeRole().get());
    }

    public static boolean hasAdmin(User user){
        return user.getRole() == RoleType.ADMIN;
    }
}
