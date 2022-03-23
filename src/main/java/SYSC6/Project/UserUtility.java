package SYSC6.Project;

import java.util.Optional;

public class UserUtility {

    public static RoleType upgradeUserRole(User user){
        RoleType current = user.getRole();
        Optional<RoleType> upgrade = current.getUpgradeRole();
        if (upgrade.isPresent()) {
            user.setRole(upgrade.get());
            return upgrade.get();
        }
        return current;
    }
}
