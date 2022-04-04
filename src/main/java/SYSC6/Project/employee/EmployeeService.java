package SYSC6.Project.employee;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import SYSC6.Project.sorting.*;
import SYSC6.Project.user.User;
import SYSC6.Project.user.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

    private static final Comparator<Employee> EMPTY_COMPARATOR = (e1, e2) -> 0;
    private static final Comparator<UserPOJO> EMPTY_COMPARATOR_U = (e1, e2) -> 0;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Autowired
    UserRepository userRepository;

    public PageArray getEmployeesArray(PagingRequest pagingRequest) {
        pagingRequest.setColumns(Stream.of("name", "role")
                .map(Column::new)
                .collect(Collectors.toList()));
        Page<Employee> employeePage = getEmployees(pagingRequest);

        PageArray pageArray = new PageArray();
        pageArray.setRecordsFiltered(employeePage.getRecordsFiltered());
        pageArray.setRecordsTotal(employeePage.getRecordsTotal());
        pageArray.setDraw(employeePage.getDraw());
        pageArray.setData(employeePage.getData()
                .stream()
                .map(this::toStringList)
                .collect(Collectors.toList()));
        return pageArray;
    }

    private List<String> toStringList(Employee employee) {
        return Arrays.asList(employee.getId().toString(), employee.getName(), employee.getRole().toString(), "");
    }

    public Page<Employee> getEmployees(PagingRequest pagingRequest) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Employee> employees = objectMapper.readValue(getClass().getClassLoader()
                            .getResourceAsStream("employees.json"),
                    new TypeReference<List<Employee>>() {
                    });

            return getPage(employees, pagingRequest);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return new Page<>();
    }

    public Page<UserPOJO> getUsers(PagingRequest pagingRequest) {

        List<UserPOJO> users = new ArrayList<UserPOJO>();
        userRepository.findAll().forEach(u -> users.add(u.getPojo()));
        System.out.println("users: "+users);
        return getPageUser(users, pagingRequest);

    }

    private Page<Employee> getPage(List<Employee> employees, PagingRequest pagingRequest) {
        List<Employee> filtered = employees.stream()
                .sorted(sortEmployees(pagingRequest))
                .filter(filterEmployees(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        long count = employees.stream()
                .filter(filterEmployees(pagingRequest))
                .count();

        Page<Employee> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Page<UserPOJO> getPageUser(List<UserPOJO> users, PagingRequest pagingRequest) {
        List<UserPOJO> filtered = users.stream()
                .sorted(sortUsers(pagingRequest))
                .filter(filterUsers(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        long count = users.stream()
                .filter(filterUsers(pagingRequest))
                .count();

        Page<UserPOJO> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<Employee> filterEmployees(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return employee -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return employee -> employee.getName()
                .toLowerCase()
                .contains(value)
                || employee.getRole().toString()
                .toLowerCase()
                .contains(value);
    }

    private Predicate<UserPOJO> filterUsers(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch()
                .getValue())) {
            return employee -> true;
        }

        String value = pagingRequest.getSearch()
                .getValue();

        return user -> user.getUsername()
                .toLowerCase()
                .contains(value)
                || user.getRole().toString()
                .toLowerCase()
                .contains(value);
    }

    private Comparator<Employee> sortEmployees(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<Employee> comparator = EmployeeComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR;
    }

    private Comparator<UserPOJO> sortUsers(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR_U;
        }

        try {
            Order order = pagingRequest.getOrder()
                    .get(0);

            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns()
                    .get(columnIndex);

            Comparator<UserPOJO> comparator = UserComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR_U;
            }

            return comparator;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return EMPTY_COMPARATOR_U;
    }
}
