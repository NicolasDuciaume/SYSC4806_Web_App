package SYSC6.Project.employee;

import SYSC6.Project.sorting.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public final class EmployeeComparators {

    @EqualsAndHashCode
    @AllArgsConstructor
    @Getter
    static class Key {
        String name;
        Direction dir;
    }

    static Map<Key, Comparator<Employee>> map = new HashMap<>();

    static {
        map.put(new Key("name", Direction.asc), Comparator.comparing(Employee::getName));
        map.put(new Key("name", Direction.desc), Comparator.comparing(Employee::getName)
                .reversed());

        map.put(new Key("role", Direction.asc), Comparator.comparing(Employee::getRole));
        map.put(new Key("role", Direction.desc), Comparator.comparing(Employee::getRole)
                .reversed());
    }

    public static Comparator<Employee> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private EmployeeComparators() {
    }
}
