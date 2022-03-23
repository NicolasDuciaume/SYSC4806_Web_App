package SYSC6.Project;

import javax.management.relation.Role;
import java.util.Optional;

public enum RoleType {
    NO_ROLE (1),
    FREE_USER (2),
    PAID_USER (0),
    ADMIN (0);

    public final int upgradeRoleIndex;

    private RoleType(int upgradeRoleIndex) {
        this.upgradeRoleIndex = upgradeRoleIndex;
    }

    public Optional<RoleType> getUpgradeRole() {
        if (upgradeRoleIndex != 0){
            return Optional.of(RoleType.values()[upgradeRoleIndex]);
        }
        return Optional.empty();
    }
}
