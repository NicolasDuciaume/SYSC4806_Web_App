package SYSC6.Project.employee;

import SYSC6.Project.sorting.Direction;
import SYSC6.Project.user.User;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class UserComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<UserPOJO>> map = new HashMap<>();

    static {
        map.put(new Key("name", Direction.asc), Comparator.comparing(UserPOJO::getUsername));
        map.put(new Key("name", Direction.desc), Comparator.comparing(UserPOJO::getUsername)
                .reversed());

        map.put(new Key("role", Direction.asc), Comparator.comparing(UserPOJO::getRole));
        map.put(new Key("role", Direction.desc), Comparator.comparing(UserPOJO::getRole)
                .reversed());
    }

    public static Comparator<UserPOJO> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private UserComparators() {
    }
}
