package SYSC6.Project;

import java.util.Optional;

public class UserUtility {

    public static Boolean upgradeUserRole(User user){
        RoleType current = user.getRole();
        Optional<RoleType> upgrade = current.getUpgradeRole();
        if (upgrade.isPresent()) {
            user.setRole(upgrade.get());
            return true;
        }
        return false;
    }
}
