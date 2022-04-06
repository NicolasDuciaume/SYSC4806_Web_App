package SYSC6.Project.user;

import java.util.Optional;

public enum RoleType {
    NO_ROLE (1, "no_role"),
    FREE_USER (2, "free_user"),
    PAID_USER (1, "paid_user"),
    ADMIN (3, "admin");

    public final int upgradeRoleIndex;
    public final String name;

    private RoleType(int upgradeRoleIndex, String name) {
        this.upgradeRoleIndex = upgradeRoleIndex;
        this.name = name;
    }

    public boolean isUpgradeable(){
        return upgradeRoleIndex != this.ordinal();
    }

    public Optional<RoleType> getUpgradeRole() {
        return Optional.of(RoleType.values()[upgradeRoleIndex]);
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
