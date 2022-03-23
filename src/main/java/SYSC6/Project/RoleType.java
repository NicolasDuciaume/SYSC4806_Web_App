package SYSC6.Project;

import javax.management.relation.Role;
import java.util.Locale;
import java.util.Optional;

public enum RoleType {
    NO_ROLE (1, "no_role"),
    FREE_USER (2, "free_user"),
    PAID_USER (0, "paid_user"),
    ADMIN (0, "admin");

    public final int upgradeRoleIndex;
    public final String name;

    private RoleType(int upgradeRoleIndex, String name) {
        this.upgradeRoleIndex = upgradeRoleIndex;
        this.name = name;
    }

    public Optional<RoleType> getUpgradeRole() {
        if (upgradeRoleIndex != 0){
            return Optional.of(RoleType.values()[upgradeRoleIndex]);
        }
        return Optional.empty();
    }

    public static RoleType getRoleByString(String s) {
        RoleType role = RoleType.NO_ROLE;
        s = s.toLowerCase();
        if (s.equals(RoleType.FREE_USER.name)) {
            role = RoleType.FREE_USER;
        } else if (s.equals(RoleType.PAID_USER.name)) {
            role = RoleType.PAID_USER;
        } else if (s.equals(RoleType.ADMIN.name)) {
            role = RoleType.ADMIN;
        } else {

        }
        return role;
    }
}
